package som;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

public class ImageLoader {

	public static List<float[]> load(File file) throws IOException {
		if (file.isDirectory()) {
			return loadDirectory(file);
		} else if (file.isFile()) {
			List<float[]> pixels = new ArrayList<>();
			pixels.add(loadFile(file));
			return pixels;
		}
		throw new IOException("File/Directory not accepted");
	}

	public static float[] loadFile(File file) {
		FImage image;
		try {
			image = ImageUtilities.readF(file);
			return image.getFloatPixelVector();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<float[]> loadDirectory(File directory) throws IOException {
		List<float[]> pixels = new ArrayList<>();
		for (File file : directory.listFiles()) {
			pixels.add(loadFile(file));
		}
		return pixels;
	}

	public static void validateImageIntegrity(List<float[]> images) throws Exception {
		float[] any = images.iterator().next();
		int length = any.length;
		for (float[] image : images) {
			if (image.length != length) {
				throw new Exception("Images have different sizes");
			}
		}
	}

}
