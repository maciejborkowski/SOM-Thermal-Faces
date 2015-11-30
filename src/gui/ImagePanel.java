package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private BufferedImage originalImage;
	private BufferedImage resultImage;

	public ImagePanel(BufferedImage image) {
		this.originalImage = image;
	}

	public ImagePanel() {
	}

	public BufferedImage getOriginalImage() {
		return originalImage;
	}

	public void setOriginalImage(BufferedImage image) {
		this.originalImage = image;
	}

	public BufferedImage getResultImage() {
		return resultImage;
	}

	public void setResultImage(BufferedImage resultImage) {
		this.resultImage = resultImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		int x = 0;
		if (originalImage != null) {
			x = originalImage.getWidth() + 20;
		}
		super.paintComponent(g);
		g.drawImage(originalImage, 0, 0, null);
		if (resultImage != null) {
			g.drawImage(resultImage.getScaledInstance(250, 250, 0), x, 0, null);
		}
	}

}