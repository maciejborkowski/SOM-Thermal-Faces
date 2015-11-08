package som;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

public class Engine {
	private final NeuronNet neuronNet;
	private final List<float[]> images;
	private final EngineOptions options;

	public Engine(EngineOptions options) throws Exception {
		this.options = options;
		images = ImageLoader.load(new File(options.getPictures()));
		ImageLoader.validateImageIntegrity(images);
		normalize(images);
		neuronNet = new NeuronNet(options.getLayerX(), options.getLayerY(), images.get(0).length);
	}

	public void learn() {
		float learningRate = options.getLearningRate();
		float mexicanHatParam = options.getMexicanHatParam();

		while (learningRate > 0.0) {
			Logger.getRootLogger().debug("Starting for learning rate = " + learningRate);
			Collections.shuffle(images);
			Map<Point, float[]> winnerImageMap = new HashMap<>();
			for (float[] image : images) {
				Point winner = neuronNet.findWinner(image);
				winnerImageMap.put(winner, image);
			}
			for (Entry<Point, float[]> entry : winnerImageMap.entrySet()) {
				neuronNet.applyGrossbergRule(learningRate, mexicanHatParam, entry.getKey(), entry.getValue());
			}

			Logger.getRootLogger().debug("Done for learning rate = " + learningRate);
			learningRate -= 0.25;
			mexicanHatParam -= 0.25;
		}
	}

	public BufferedImage retrieve(File imageFile) {
		float[] image = ImageLoader.loadFile(imageFile);
		normalize(image);
		float[][] output = neuronNet.retrieve(image);
		// makePrintable(output);
		FImage retrieved = new FImage(output);
		return ImageUtilities.createBufferedImage(retrieved);
	}

	private void makePrintable(float[][] output) {
		float max = Float.MIN_VALUE;
		for (float[] row : output) {
			for (float value : row) {
				if (max < value) {
					max = value;
				}
			}
		}
		for (float[] row : output) {
			for (int i = 0; i < row.length; i++) {
				row[i] /= max;
			}
		}
	}

	private void normalize(List<float[]> images) {
		for (float[] image : images) {
			normalize(image);
		}
	}

	private void normalize(float[] image) {
		float divider = 0;
		for (float value : image) {
			divider += (value * value);
		}
		divider = (float) Math.sqrt(new Float(divider).doubleValue());
		for (int i = 0; i < image.length; i++) {
			image[i] /= divider;
		}
	}
}
