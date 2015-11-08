package som;

import java.awt.Point;
import java.util.Random;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class NeuronNet {
	private final EuclideanDistance euclideanDistance = new EuclideanDistance();
	private final Random random = new Random();
	private float[][] neurons;
	private float[][][] weights;

	public NeuronNet(int layerX, int layerY, int vectorLength) {
		initNet(layerX, layerY, vectorLength);
		randomizeNet();
	}

	public Point findWinner(float[] input) {
		Point winner = new Point();
		float max = Float.MIN_VALUE;
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				float output = calculateOutput(weights[i][j], input);
				if (max < output) {
					max = output;
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
				neurons[i][j] = calculateOutput(weights[i][j], input);
				// neurons[i][j] = euclideanDistance.compute(weights[i][j],
				// input);
			}
		}
		return neurons;
	}

	private float calculateOutput(float[] weights, float[] input) {
		float sum = 0.0f;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i] * input[i];
		}
		return sum;
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
					weights[i][j][k] = random.nextFloat();
				}
			}
		}
	}

}
