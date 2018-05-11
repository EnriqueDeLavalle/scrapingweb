package com.belatrix.scrapingWeb.executor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.belatrix.scrapingWeb.view.ScrapingJFileChooser;

public class AppExecutor {
	
	private static final String TITLE = "Scraping text";
	
	
	public static void main(String[] args) {

		JFrame frame = new JFrame(TITLE);

		ScrapingJFileChooser panel = new ScrapingJFileChooser();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
		frame.setVisible(true);
		
		
	}
}
