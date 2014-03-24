package common;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpParams;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for extracting information from the resulting
 * html-content from IMDB. The content is passed as a string and
 * the methods extract various information from it.
 */
public class Extractor
{
    private String content;

    public Extractor(Loader loader)
    {
        this.content = loader.load();
    }

	public BufferedImage getCover()
	{
		BufferedImage cover = null;

		try
		{
//			Pattern p = Pattern.compile("img_primary.*?<img.*?src=\\\"(.*?)\\\"", Pattern.DOTALL |Pattern.MULTILINE);
			Pattern p = Pattern.compile("id=\"img_primary\".*?<a.*?src=\\\"(.*?)\\\"", Pattern.DOTALL |Pattern.MULTILINE);
			Matcher m = p.matcher(content);
			String url = m.find() ? m.group(1) : "<ERR>";
            System.out.println("URL="+url);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			CookieSpecFactory csf = new CookieSpecFactory() {
				public CookieSpec newInstance(HttpParams params) {
					return new BrowserCompatSpec() {
						@Override
						public void validate(Cookie cookie, CookieOrigin origin)
						throws MalformedCookieException
						{
							// Oh, I am easy
						}
					};
				}
			};

			httpclient.getCookieSpecs().register("easy", csf);
			httpclient.getParams().setParameter(
			ClientPNames.COOKIE_POLICY, "easy");
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			cover = ImageIO.read(entity.getContent());
            //System.out.println("url: " + url);
            //System.out.println("img: " + cover);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return cover.getWidth()>1 ? cover : null;
	}

	public String getStory()
	{
		Pattern p = Pattern.compile("<h2>Storyline</h2>.*?<p>(.*?)<em.*?>Written.*?</em>(.*?)</p>", Pattern.DOTALL |Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		if (m.find()) {
            String result = m.group(1) + m.group(2);
            result = result.replaceAll("<.*?>"," ");
            result = StringEscapeUtils.unescapeHtml(result);
            return result;
        } else {
			return "<ERR>";
        }
	}

	public String getRating()
	{
        Pattern p = Pattern.compile("<span itemprop=\"ratingValue\">(\\d*.\\d*)</span>", Pattern.DOTALL |Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		if (m.find())
		{
			return m.group(1);
		}
		else
		{
			return "<ERR>";
		}
	}

	public String getTitle()
	{
		Pattern p = Pattern.compile("<title>(.*)</title>", Pattern.DOTALL |Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		if (m.find())
		{
			return StringEscapeUtils.unescapeHtml(m.group(1).replace(" - IMDb",""));
		}
		else
		{
			return "<ERR>";
		}
	}

	public Set<String> getGenres()
	{
		TreeSet<String> set = new TreeSet<String>();
		Pattern p = Pattern.compile("/genre/(.+?)[\\?|\"]", Pattern.DOTALL | Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		while (m.find())
		{
			set.add(m.group(1));
		}
		return set;
	}
}
