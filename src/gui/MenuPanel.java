package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import som.Engine;
import som.EngineOptions;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private final static String LEARN_LABEL = "Learn";
	private final static String IMAGE_LABEL = "Image";

	private ImagePanel imagePanel;

	private JButton learnButton;
	private JButton imageButton;
	private JFileChooser fileChooser = new JFileChooser();

	private Engine engine;

	public MenuPanel(ImagePanel imagePanel) {
		this.imagePanel = imagePanel;
		addLearnButton();
		addImageChooser();

	}

	private void addLearnButton() {
		learnButton = new JButton(LEARN_LABEL);
		learnButton.addActionListener(new RunListener());
		add(learnButton);
	}

	private void addImageChooser() {
		imageButton = new JButton(IMAGE_LABEL);
		imageButton.addActionListener(new ImageChooserListener());
		imageButton.setEnabled(false);
		add(imageButton);
	}

	private final class RunListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				EngineOptions options = new EngineOptions();
				String pictures = "pictures/few";
				options.setPictures(pictures);
				options.setLayerX(96);
				options.setLayerY(96);
				options.setLearningRate(1);
				options.setMexicanHatParam(1);
				engine = new Engine(options);
				engine.learn();
				imageButton.setEnabled(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private final class ImageChooserListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fileChooser.setCurrentDirectory(new File("E:/workspaceMars/SOMThermalFaces/pictures"));
			int returnVal = fileChooser.showOpenDialog(MenuPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File image = fileChooser.getSelectedFile();
				try {
					imagePanel.setOriginalImage(ImageIO.read(image));
					BufferedImage result = engine.retrieve(image);
					imagePanel.setResultImage(result);
					imagePanel.repaint();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
