package decoration;

import common.Constants;
import common.Movie;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Class to paint rating. The paint method takes the entire image and
 * the method is responsible for painting the rating in the right spot.
 */
public class Rating
{
	public void paint(BufferedImage image, Movie movie)
	{
		try
		{
//            BufferedImage star = ImageIO.read(getClass().getResource("/star-gold48.png"));
//
//            Graphics2D g2 = Constants.prepareGraphics(image);
//            g2.setFont(Constants.fontTitle);
//
//            int w = g2.getFontMetrics().stringWidth(movie.getRating()) + Constants.INNER_MARGIN*2;
//            int h =
//                Constants.MARGIN +
//                g2.getFontMetrics(Constants.fontTitle).getHeight() +
//                g2.getFontMetrics(Constants.fontGenre).getHeight() +
//                Constants.INNER_MARGIN*2;
//            int x = image.getWidth() - w -Constants.MARGIN;
//            int y = Constants.MARGIN;
//
//            g2.setPaint(Color.ORANGE);

//            FontRenderContext fontRenderContext = g2.getFontRenderContext();
//            TextLayout textLayout = new TextLayout(movie.getRating(), Constants.fontTitle, fontRenderContext);
//            g2.translate(x + ((star.getWidth()-g2.getFontMetrics().stringWidth(movie.getRating()))/2),y);
//            g2.setStroke(new BasicStroke(5));
//            g2.setColor(Color.YELLOW);
//            g2.draw(textLayout.getOutline(null));
//            g2.setColor(Color.ORANGE);
//            g2.drawString(movie.getRating(), 0, 0);
        }
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
