package common;

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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Loader
{
    private String url;

    public Loader(String url)
    {
        this.url = url;
    }

    public BufferedImage loadImage(String name) throws Exception
    {
        return ImageIO.read(getClass().getResource(name));
    }

	public String load()
	{
		return fetchRemote(url);
	}

	private String fetchRemote(String url)
	{
		StringBuilder content = new StringBuilder();
		try
		{
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

			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
			String line = br.readLine();
			while (line!=null)
			{
				content.append(line);
				line = br.readLine();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return content.toString();
	}
}
