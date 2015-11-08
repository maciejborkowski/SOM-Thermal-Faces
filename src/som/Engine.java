package som;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;

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
			Collections.shuffle(images);
			for (float[] image : images) {
				Point winner = neuronNet.findWinner(image);
				neuronNet.applyGrossbergRule(learningRate, mexicanHatParam, winner, image);
			}
			learningRate -= 0.1;
			mexicanHatParam -= 0.1;
		}
	}

	public BufferedImage retrieve(File imageFile) {
		float[] image = ImageLoader.loadFile(imageFile);
		normalize(image);
		float[][] output = neuronNet.retrieve(image);
		float[][] converted = new float[output.length][output[0].length];
		for (int i = 0; i < output.length; i++) {
			for (int j = 0; j < output[i].length; j++) {
				converted[i][j] = (float) output[i][j];
			}
		}
		FImage retrieved = new FImage(converted);
		return ImageUtilities.createBufferedImage(retrieved);
	}

	private void normalize(List<float[]> images) {
		for (float[] image : images) {
			normalize(image);
		}
	}

	private void normalize(float[] image) {
		float sum = 0;
		for (float value : image) {
			sum += value;
		}
		for (int i = 0; i < image.length; i++) {
			image[i] /= sum;
		}
	}
}
