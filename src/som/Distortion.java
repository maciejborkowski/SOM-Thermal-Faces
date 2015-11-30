package som;

import java.awt.Point;

public class Distortion {

	public static void chillDownImage(float[] image, float percent) {
		for (int i = 0; i < image.length; i++) {
			image[i] *= percent / 100f;
		}
	}

	public static void heatUpPixels(float[] pixels, int width, Point centre, float radius, float percent) {
		for (int i = 0; i < pixels.length; i++) {
			if ((Point.distance(centre.x, centre.y, i % width, i / width)) < radius) {
				float value = (1.0f - pixels[i]) * percent / 100f;
				pixels[i] += value;
			}
		}
		System.out.println();
	}
}
