package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Application {
	private final static String WINDOW_LABEL = "SOMThermalFaces";

	private MenuPanel menuPanel;
	private ImagePanel imagePanel;
	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Application() {
		initializeApplication();
	}

	private void initializeApplication() {
		initFrame();
	}

	private void initFrame() {
		frame = new JFrame(WINDOW_LABEL);
		frame.setBounds(0, 0, 800, 680);
		frame.getContentPane().setLayout(new BorderLayout());
		imagePanel = new ImagePanel();
		frame.add(imagePanel, BorderLayout.CENTER);
		menuPanel = new MenuPanel(imagePanel);
		frame.add(menuPanel, BorderLayout.SOUTH);
	}

}