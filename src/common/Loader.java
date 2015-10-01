package common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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
			URL realUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(realUrl.openStream(), "utf-8"));
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
