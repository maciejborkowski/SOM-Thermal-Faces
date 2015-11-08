package som;

import java.awt.Point;
import java.util.Random;

public class NeuronNet {
	private final Random random = new Random();
	private float[][] neurons;
	private float[][][] weights;

	public NeuronNet(int layerX, int layerY, int vectorLength) {
		initNet(layerX, layerY, vectorLength);
		randomizeNet();
	}

	public Point findWinner(float[] input) {
		Point winner = new Point();
		float min = Float.MAX_VALUE;
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				// float output = calculateOutput(weights[i][j], input);
				float output = calculateEuclideanDistance(weights[i][j], input);
				if (min > output) {
					min = output;
					winner.x = i;
					winner.y = j;
				}
			}
		}
		return winner;
	}

	public void applyGrossbergRule(float learningRate, float mexicanHatParam, Point winner, float[] input) {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				for (int k = 0; k < weights[i][j].length; k++) {
					weights[i][j][k] += learningRate * mexicanHat(winner, i, j, mexicanHatParam)
							* (input[j] - weights[i][j][k]);
				}
			}
		}
	}

	public float[][] retrieve(float[] input) {
		for (int i = 0; i < neurons.length; i++) {
			for (int j = 0; j < neurons[i].length; j++) {
				normalize(weights[i][j]);
				neurons[i][j] = calculateOutput(weights[i][j], input);
			}
		}
		return neurons;
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

	private float calculateOutput(float[] weights, float[] input) {
		float sum = 0.0f;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i] * input[i];
		}
		return sum;
	}

	private float calculateEuclideanDistance(float[] weights, float[] input) {
		float result = 0.0f;
		for (int i = 0; i < weights.length; i++) {
			result += Math.pow(weights[i] - input[i], 2);
		}
		result = (float) Math.sqrt(new Float(result).doubleValue());
		return result;
	}

	private float mexicanHat(Point winner, int x, int y, float a) {
		float distance = (float) Point.distance(winner.getX(), winner.getY(), x, y);
		if (distance == 0.0) {
			return 1;
		} else if (distance < 2 * Math.PI / a) {
			return (float) (Math.sin(a * distance) / (a * distance));
		} else {
			return 0;
		}
	}

	private void initNet(int layerX, int layerY, int vectorLength) {
		neurons = new float[layerX][layerY];
		weights = new float[layerX][layerY][vectorLength];
	}

	private void randomizeNet() {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				for (int k = 0; k < weights[i][j].length; k++) {
					weights[i][j][k] = random.nextFloat() * 0.001f;
				}
			}
		}
	}

}
