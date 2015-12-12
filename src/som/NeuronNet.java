package som;

import java.awt.Point;
import java.util.Random;
import java.util.Set;

public class NeuronNet {
	private final Random random = new Random();
	private float[][] neurons;
	private float[][][] weights;

	public NeuronNet(int layerX, int layerY, int vectorLength) {
		initNet(layerX, layerY, vectorLength);
		randomizeNet();
	}

	public Point findWinner(float[] input, Set<Point> ignore) {
		Point winner = null;
		float min = Float.MAX_VALUE;
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				if (!ignore.contains(new Point(i, j))) {
					float output = calculateEuclideanDistance(weights[i][j], input);
					if (min > output) {
						min = output;
						winner = new Point(i, j);
					}
				}
			}
		}
		return winner;
	}

	public void applyGrossbergRule(float learningRate, float mexicanHatParam, Point winner, float[] input) {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				float hat = learningRate * mexicanHat(winner, i, j, mexicanHatParam);
				if (hat != 0.0) {
					for (int k = 0; k < weights[i][j].length; k++) {
						float difference = hat * (input[k] - weights[i][j][k]);
						weights[i][j][k] += difference;
					}
				}
			}
		}
	}

	public float[][] retrieve(float[] input) {
		for (int i = 0; i < neurons.length; i++) {
			for (int j = 0; j < neurons[i].length; j++) {
				neurons[i][j] = calculateOutput(weights[i][j], input);
			}
		}
		return neurons;
	}

	private float calculateOutput(float[] weights, float[] input) {
		float sum = 0.0f;
		for (int k = 0; k < weights.length; k++) {
			sum += weights[k] * input[k];
		}
		return sum;
	}

	private float calculateEuclideanDistance(float[] weights, float[] input) {
		float result = 0.0f;
		for (int i = 0; i < weights.length; i++) {
			result += Math.pow(weights[i] - input[i], 2);
		}
		return result;
	}

	private float mexicanHat(Point winner, int x, int y, float a) {
		float distance = (float) Point.distance(winner.getX(), winner.getY(), x, y);
		float ar = a * distance;
		if (distance == 0.0f) {
			return 1;
		} else if (ar < Math.PI) {
			return (float) (Math.sin(ar) / ar);
		} else {
			return 0.0f;
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
					weights[i][j][k] = random.nextFloat() * 2 - 1;
				}
			}
		}
	}

}
