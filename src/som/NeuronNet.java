package som;

import java.awt.Point;
import java.util.Random;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class NeuronNet {
	private final EuclideanDistance euclideanDistance = new EuclideanDistance();
	private final Random random = new Random();
	private double[][] neurons;
	private double[][][] weights;

	public NeuronNet(int layerX, int layerY, int vectorLength) {
		initNet(layerX, layerY, vectorLength);
		randomizeNet();
	}

	public Point findWinner(double[] input) {
		Point winner = new Point();
		double max = Double.MIN_VALUE;
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				double output = calculateOutput(weights[i][j], input);
				if (max < output) {
					max = output;
					winner.x = i;
					winner.x = j;
				}
			}
		}
		return winner;
	}

	public void applyGrossbergRule(double learningRate, double mexicanHatParam, Point winner, double[] input) {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				for (int k = 0; k < weights[i][j].length; k++) {
					weights[i][j][k] += learningRate * mexicanHat(winner, i, j, mexicanHatParam)
							* (input[j] - weights[i][j][k]);
				}
			}
		}
	}

	public double[][] retrieve(double[] input) {
		for (int i = 0; i < neurons.length; i++) {
			for (int j = 0; j < neurons[i].length; j++) {
				neurons[i][j] = euclideanDistance.compute(weights[i][j], input);
			}
		}
		return neurons;
	}

	private double calculateOutput(double[] weights, double[] input) {
		double sum = 0.0;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i] * input[i];
		}
		return sum;
	}

	private double mexicanHat(Point winner, int x, int y, double a) {
		double distance = Point.distance(winner.getX(), winner.getY(), x, y);
		if (distance == 0.0) {
			return 1;
		} else if (distance < 2 * Math.PI / a) {
			return Math.sin(a * distance) / (a * distance);
		} else {
			return 0;
		}
	}

	private void initNet(int layerX, int layerY, int vectorLength) {
		neurons = new double[layerX][layerY];
		weights = new double[layerX][layerY][vectorLength];
	}

	private void randomizeNet() {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				for (int k = 0; k < weights[i][j].length; k++) {
					weights[i][j][k] = random.nextDouble();
				}
			}
		}
	}

}
