package som;

import java.awt.Point;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class Engine {
	private final NeuronNet neuronNet;
	private final List<double[]> images;

	public Engine(String pictures) throws Exception {
		images = ImageLoader.load(new File(pictures));
		ImageLoader.validateImageIntegrity(images);
		neuronNet = new NeuronNet(10, 20, images.get(0).length);
	}

	public void learn() {
		double learningRate = 1.0;
		double mexicanHatParam = 1.0;
		while (learningRate > 0.0) {
			Collections.shuffle(images);
			for (double[] image : images) {
				Point winner = neuronNet.findWinner(image);
				neuronNet.applyGrossbergRule(learningRate, mexicanHatParam, winner, image);
			}
			learningRate -= 0.1;
			mexicanHatParam -= 0.1;
		}
	}

	public void retrieve(double[] image) {
		neuronNet.retrieve(image);
	}
}
