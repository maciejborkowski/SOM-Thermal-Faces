package som;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

public class ImageLoader {

	public static List<double[]> load(File file) throws IOException {
		if (file.isDirectory()) {
			return loadDirectory(file);
		} else if (file.isFile()) {
			List<double[]> pixels = new ArrayList<>();
			pixels.add(loadFile(file));
			return pixels;
		}
		throw new IOException("File/Directory not accepted");
	}

	public static double[] loadFile(File file) throws IOException {
		FImage image = ImageUtilities.readF(file);
		return image.getDoublePixelVector();
		// BufferedImage image = ImageIO.read(file);
		// int[] pixels = getImagePixels(image);
		// return pixels;
	}

	public static List<double[]> loadDirectory(File directory) throws IOException {
		List<double[]> pixels = new ArrayList<>();
		for (File file : directory.listFiles()) {
			pixels.add(loadFile(file));
		}
		return pixels;
	}

	public static void validateImageIntegrity(List<double[]> images) throws Exception {
		double[] any = images.iterator().next();
		int length = any.length;
		for (double[] image : images) {
			if (image.length != length)
				throw new Exception("Images have different sizes");
		}
	}

}
