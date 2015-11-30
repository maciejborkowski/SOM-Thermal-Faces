package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

import org.apache.log4j.BasicConfigurator;

public class SOMApplication {
	private final static String WINDOW_LABEL = "SOM Thermal Faces";

	private MenuPanel menuPanel;
	private ImagePanel imagePanel;
	private JFrame frame;

	public static void main(String[] args) {
		BasicConfigurator.configure();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SOMApplication window = new SOMApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SOMApplication() {
		initializeApplication();
	}

	private void initializeApplication() {
		initFrame();
	}

	private void initFrame() {
		frame = new JFrame(WINDOW_LABEL);
		frame.setBounds(0, 0, 600, 420);
		frame.getContentPane().setLayout(new BorderLayout());
		imagePanel = new ImagePanel();
		frame.add(imagePanel, BorderLayout.CENTER);
		menuPanel = new MenuPanel(imagePanel);
		frame.add(menuPanel, BorderLayout.SOUTH);
	}

}