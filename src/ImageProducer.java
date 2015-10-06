import common.Constants;
import common.Extractor;
import common.Loader;
import common.Movie;
import decoration.Rating;
import decoration.Text;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

/**
 * Class responsible for orchestrating the decoration and writing the
 * resulting image to disk.
 */
public class ImageProducer
{
	public void run(String filename, File directory) throws Exception
	{
        File file = new File(directory, filename + ".jpg");

        if (file.exists())
        {
            System.out.println("["+filename+"] EXISTS  (" + file.getAbsolutePath() + ")");
            return;
        }

        String url = "http://www.imdb.com/title/tt" + filename;

        Movie movie = new Movie(new Extractor(new Loader(url)));

		BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (movie.getCover()!=null)
            g2.drawImage(movie.getCover(), Constants.MARGIN, Constants.MARGIN, null);

		Text.paintTitle(image, movie);
		Text.paintGenres(image, movie);
		Text.paintStory(image, movie);

		write(image, file);
	}

	private void write(BufferedImage image, File target) throws Exception
	{
		Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = (ImageWriter)iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(1);

        System.err.println("[" + target.getName().replace(".jpg","") + "] WRITING (" + target.getAbsolutePath() + ")");
        FileImageOutputStream output = new FileImageOutputStream(target);
        writer.setOutput(output);
        IIOImage iioimage = new IIOImage(image, null, null);
        writer.write(null, iioimage, iwp);
		writer.dispose();
	}
}
