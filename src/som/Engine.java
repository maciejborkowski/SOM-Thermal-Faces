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
		float learningRate;
		float mexicanHatParam = options.getMexicanHatParamTo();
		int steps = options.getSteps();

		for (int i = 0; i < steps; i++) {
			learningRate = options.getLearningRateFrom()
					+ (options.getLearningRateTo() - options.getLearningRateFrom()) * i / steps;
			mexicanHatParam = options.getMexicanHatParamFrom()
					+ (options.getMexicanHatParamTo() - options.getMexicanHatParamFrom()) * i / steps;
			Logger.getRootLogger().debug("Starting step " + (i + 1) + "/" + steps + ", learning rate = " + learningRate
					+ ", mexican = " + mexicanHatParam);
			Collections.shuffle(images);
			Map<Point, float[]> winnerImageMap = new HashMap<>();
			for (float[] image : images) {
				Point winner = neuronNet.findWinner(image, winnerImageMap.keySet());
				winnerImageMap.put(winner, image);
			}
			for (Entry<Point, float[]> entry : winnerImageMap.entrySet()) {
				neuronNet.applyGrossbergRule(learningRate, mexicanHatParam, entry.getKey(), entry.getValue());
			}
			// Logger.getRootLogger().debug("Done step " + (i + 1) + "/" +
			// steps);
		}
	}

	public BufferedImage retrieve(float[] image) {
		normalize(image);
		float[][] output = neuronNet.retrieve(image);
		makePrintable(output);
		FImage retrieved = new FImage(output);
		return ImageUtilities.createBufferedImage(retrieved);
	}

	private void makePrintable(float[][] output) {
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for (float[] row : output) {
			for (int i = 0; i < row.length; i++) {
				if (min > row[i]) {
					min = row[i];
				}
				if (max < row[i]) {
					max = row[i];
				}
			}
		}
		for (float[] row : output) {
			for (int i = 0; i < row.length; i++) {
				row[i] -= min;
				if (row[i] < 0.0f) {
					row[i] = 0.0f;
				}
			}
		}
		max -= min;
		for (float[] row : output) {
			for (int i = 0; i < row.length; i++) {
				row[i] = row[i] / max;
				if (row[i] < 0.99f) {
					row[i] -= 0.5f;
					if (row[i] < 0.0f) {
						row[i] = 0.0f;
					}
				}
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
