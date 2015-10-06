package decoration;

import common.Constants;
import common.Movie;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Class to paint text on the image. Methods
 */
public class Text
{
	public static void paintTitle(BufferedImage image, Movie movie)
	{
		Graphics2D g2 = Constants.prepareGraphics(image);
		g2.setFont(Constants.fontTitle);
		g2.setColor(Color.WHITE);
		int x = Constants.MARGIN + (movie.getCover()!=null ? Constants.MARGIN + movie.getCover().getWidth() : 0);
		int y = Constants.MARGIN+g2.getFontMetrics().getAscent() - 15;
		g2.drawString(movie.getTitle(), x, y);
	}

	public static void paintGenres(BufferedImage image, Movie movie)
	{
		Graphics2D g2 = Constants.prepareGraphics(image);

        g2.setFont(Constants.fontGenre);
        FontMetrics fontMetrics = g2.getFontMetrics();

        g2.setColor(Color.WHITE);

        int x = Constants.MARGIN + (movie.getCover()!=null ? Constants.MARGIN + movie.getCover().getWidth() : 0);
		int y = Constants.MARGIN*2 + g2.getFontMetrics(Constants.fontTitle).getAscent() - 15;
		int dy = fontMetrics.getAscent() + Constants.INNER_MARGIN * 2 - 3;

		for (String genre : movie.getGenres())
		{
			int textWidth = fontMetrics.stringWidth(genre);
			g2.setPaint(Constants.COLOR_GENRE_BOX);
			g2.fillRoundRect(x, y, textWidth+Constants.INNER_MARGIN*4, dy, 15, 15);
			g2.setPaint(Constants.COLOR_GENRE_TEXT);
			g2.drawString(genre, x + Constants.INNER_MARGIN * 2, y + dy - Constants.INNER_MARGIN - 2);
			x += textWidth + Constants.INNER_MARGIN * 6;
		}

        int textWidth = fontMetrics.stringWidth(movie.getRating());
        g2.setPaint(Constants.COLOR_RATING_BOX);
        g2.fillRoundRect(x, y, textWidth+Constants.INNER_MARGIN*4, dy, 15, 15);
        g2.setPaint(Color.BLACK);
        g2.drawString(movie.getRating(), x + Constants.INNER_MARGIN * 2, y + dy - Constants.INNER_MARGIN - 2);

        g2.dispose();
	}

	public static void paintStory(BufferedImage image, Movie movie)
	{
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setFont(Constants.fontStory);
		FontMetrics fm = g2.getFontMetrics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		String[] words = movie.getStory().split(" ");

		int lineNumber = 1;

        int xBase;

        if (movie.getCover()==null) {
            xBase = Constants.MARGIN;
        } else {
            xBase = Constants.MARGIN*2 + movie.getCover().getWidth();
        }

		int y =
            Constants.MARGIN*3 +
            g2.getFontMetrics(Constants.fontTitle).getHeight() +
            Constants.INNER_MARGIN*2 +
            g2.getFontMetrics(Constants.fontGenre).getHeight() - 15;

        int x = xBase;

        for (String word : words)
		{
			String work = word.trim();
			if (work.length()>0)
			{
				String txt = word.replaceAll("\n","") + " ";
				int width = fm.stringWidth(txt);

				if ((x + width + Constants.MARGIN) > Constants.WIDTH)
				{
                    lineNumber++;
                    x = lineNumber<5 ? xBase : Constants.MARGIN;
					y += fm.getAscent() + Constants.INNER_MARGIN;
				}

				g2.drawString(txt, x, y);
				x+=width;
			}
		}
	}
}
