package common;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
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
			Pattern p = Pattern.compile("id=\"img_primary\".*?<a.*?src=\\\"(.*?)\\\"", Pattern.DOTALL |Pattern.MULTILINE);
			Matcher m = p.matcher(content);
			String url = m.find() ? m.group(1) : "<ERR>";
			cover = ImageIO.read(new URL(url));
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
            result = StringEscapeUtils.unescapeHtml4(result);
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
			return StringEscapeUtils.unescapeHtml4(m.group(1).replace(" - IMDb",""));
		}
		else
		{
			return "<ERR>";
		}
	}

	public Set<String> getGenres()
	{
		TreeSet<String> set = new TreeSet<String>();
		Pattern p = Pattern.compile("/genre/([^?]+?)[\\?|\"]", Pattern.DOTALL | Pattern.MULTILINE);
		Matcher m = p.matcher(content);
		while (m.find())
		{
			set.add(m.group(1));
		}
		return set;
	}
}
