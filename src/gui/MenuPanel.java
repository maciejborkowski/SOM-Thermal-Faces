package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import som.Distortion;
import som.Engine;
import som.EngineOptions;
import som.ImageLoader;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private final static String LEARN_LABEL = "Learn";
	private final static String IMAGE_LABEL = "Image";

	private ImagePanel imagePanel;

	private JButton learnButton;
	private JButton imageButton;
	private JFileChooser fileChooser = new JFileChooser();

	private Engine engine;

	private JTextField sizeX = new JTextField("10");
	private JTextField sizeY = new JTextField("10");
	private JTextField learningRateFrom = new JTextField("1.0");
	private JTextField learningRateTo = new JTextField("0.1");
	private JTextField mexicanHatParamFrom = new JTextField("1.0");
	private JTextField mexicanHatParamTo = new JTextField("5.0");
	private JTextField loops = new JTextField("400");
	private JTextField chill = new JTextField("0");
	private JTextField heat = new JTextField("0");

	public MenuPanel(ImagePanel imagePanel) {
		this.imagePanel = imagePanel;
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		addLabel("Size X", constraints);
		addComponent(sizeX, constraints);
		addLabel("Size Y", constraints);
		addComponent(sizeY, constraints);
		newLine(constraints);
		addLabel("LearningRate From", constraints);
		addComponent(learningRateFrom, constraints);
		addLabel("LearningRate To", constraints);
		addComponent(learningRateTo, constraints);
		newLine(constraints);
		addLabel("MexicanHat From", constraints);
		addComponent(mexicanHatParamFrom, constraints);
		addLabel("MexicanHat To", constraints);
		addComponent(mexicanHatParamTo, constraints);
		newLine(constraints);
		addLabel("Loops", constraints);
		addComponent(loops, constraints);
		newLine(constraints);
		addLabel("Chill", constraints);
		addComponent(chill, constraints);
		addLabel("Heat", constraints);
		addComponent(heat, constraints);
		newLine(constraints);
		newLine(constraints);

		addLearnButton(constraints);
		addImageChooser(constraints);
	}

	private void addLearnButton(GridBagConstraints constraints) {
		learnButton = new JButton(LEARN_LABEL);
		learnButton.addActionListener(new RunListener());
		addComponent(learnButton, constraints);
	}

	private void addImageChooser(GridBagConstraints constraints) {
		imageButton = new JButton(IMAGE_LABEL);
		imageButton.addActionListener(new ImageChooserListener());
		imageButton.setEnabled(false);
		addComponent(imageButton, constraints);
	}

	private void addLabel(String value, GridBagConstraints constraints) {
		JLabel label = new JLabel(value);
		addComponent(label, constraints);
	}

	private void addComponent(Component field, GridBagConstraints constraints) {
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.9;
		field.setPreferredSize(new Dimension(120, 20));
		add(field, constraints);
		constraints.gridx++;
	}

	private void newLine(GridBagConstraints constraints) {
		constraints.gridy++;
		constraints.gridx = 0;
	}

	private final class RunListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				imageButton.setEnabled(false);
				EngineOptions options = new EngineOptions();
				String pictures = "pictures/learning";
				options.setPictures(pictures);
				options.setLayerX(Integer.parseInt(sizeX.getText()));
				options.setLayerY(Integer.parseInt(sizeY.getText()));
				options.setLearningRateFrom(Float.parseFloat(learningRateFrom.getText()));
				options.setLearningRateTo(Float.parseFloat(learningRateTo.getText()));
				options.setMexicanHatParamFrom(Float.parseFloat(mexicanHatParamFrom.getText()));
				options.setMexicanHatParamTo(Float.parseFloat(mexicanHatParamTo.getText()));
				options.setLoops(Integer.parseInt(loops.getText()));
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
				File file = fileChooser.getSelectedFile();
				try {
					float[] imagePixels = ImageLoader.loadFile(file);
					float chillVal = Float.parseFloat(chill.getText());
					float heatVal = Float.parseFloat(heat.getText());
					BufferedImage img = ImageIO.read(file);
					if (chillVal > 0f && chillVal <= 100f) {
						Distortion.chillDownImage(imagePixels, 100f - chillVal);
						FImage fimage = new FImage(imagePixels, img.getWidth(), img.getHeight());
						img = ImageUtilities.createBufferedImage(fimage);
					}
					if (heatVal > 0f && heatVal <= 100f) {
						Distortion.heatUpImage(imagePixels, 100f - heatVal);
						FImage fimage = new FImage(imagePixels, img.getWidth(), img.getHeight());
						img = ImageUtilities.createBufferedImage(fimage);
					}
					imagePanel.setOriginalImage(img);
					BufferedImage result = engine.retrieve(imagePixels);
					imagePanel.setResultImage(result);
					imagePanel.repaint();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
