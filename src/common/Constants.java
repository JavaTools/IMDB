package common;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Constants
{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int MARGIN = 30;
	public static final int INNER_MARGIN = 10;

    public static final Color COLOR_GENRE_BOX = new Color(0x404040);
    public static final Color COLOR_GENRE_TEXT = new Color(0xC0C0C0);

	public static Font fontTitle, fontStory, fontGenre;

	public static final String URL = "http://www.imdb.com";
    public static final Color COLOR_RATING_BOX = new Color(0xffc734);

    public static Graphics2D prepareGraphics(BufferedImage image)
    {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return g2;
    }

    static
    {
        try
        {
//            fontStory = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/Gotham-Medium.otf")).deriveFont(Font.PLAIN, 32);
//            fontTitle = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/Gotham-Bold.otf")).deriveFont(Font.PLAIN, 50);
//            fontGenre = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/Gotham-Medium.otf")).deriveFont(Font.PLAIN, 28);
            fontStory = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/Montserrat-Regular.otf")).deriveFont(Font.PLAIN, 32);
            fontTitle = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/Montserrat-Bold.otf")).deriveFont(Font.PLAIN, 50);
            fontGenre = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/Montserrat-SemiBold.otf")).deriveFont(Font.PLAIN, 28);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fontStory = new Font("Helvetica", Font.PLAIN, 50);
            fontTitle = new Font("Calibri", Font.BOLD, 50);
            fontGenre = new Font("Calibri", Font.BOLD, 28);
        }
    }
}
