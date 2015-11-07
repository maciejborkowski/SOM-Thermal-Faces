package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import som.Engine;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private final static String RUN_LABEL = "Run";
	private final static String IMAGE_LABEL = "Image";

	private ImagePanel imagePanel;

	private JButton runButton;
	private JButton imageButton;
	private JFileChooser fileChooser = new JFileChooser();

	public MenuPanel(ImagePanel imagePanel) {
		this.imagePanel = imagePanel;
		addImageChooser();
		addRunButton();
	}

	private void addImageChooser() {
		imageButton = new JButton(IMAGE_LABEL);
		imageButton.addActionListener(new ImageChooserListener());
		add(imageButton);
	}

	private void addRunButton() {
		runButton = new JButton(RUN_LABEL);
		runButton.addActionListener(new RunListener());
		add(runButton);
	}

	private final class RunListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String pictures = "pictures/small.bmp";
				// String pictures = "pictures/L-46.bmp";
				Engine engine = new Engine(pictures);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private final class ImageChooserListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fileChooser.setCurrentDirectory(new File("E:/workspaceMars/HopfieldThermalFaces/pictures"));
			int returnVal = fileChooser.showOpenDialog(MenuPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File image = fileChooser.getSelectedFile();
				try {
					imagePanel.setOriginalImage(ImageIO.read(image));
					imagePanel.repaint();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
