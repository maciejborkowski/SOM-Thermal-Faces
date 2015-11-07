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
		super.paintComponent(g);
		g.drawImage(originalImage, 0, 0, null);
	}

}