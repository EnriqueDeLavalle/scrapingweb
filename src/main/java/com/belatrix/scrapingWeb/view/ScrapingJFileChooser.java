package com.belatrix.scrapingWeb.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.belatrix.scrapingWeb.Dispatcher;

public class ScrapingJFileChooser extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3299675910160230379L;
	private static final String TITLE = "Scraping text";
	JButton go;

	JFileChooser chooser;
	
	private String directory;
	private String file;

	public ScrapingJFileChooser() {
		go = new JButton("Select the text file");
		go.addActionListener(this);
		add(go);
	}

	public void actionPerformed(ActionEvent e) {
		

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(TITLE);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory().getPath());
			this.directory = chooser.getCurrentDirectory().getPath();
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile().getPath());
			this.file = chooser.getSelectedFile().getPath();
			
			Dispatcher dispatcher = new Dispatcher(this.directory, this.file);
			try {
				dispatcher.dispatchScraping();
			} catch (InterruptedException e1) {
			
			}
		} else {
			JOptionPane.showMessageDialog(null, "No Selection!");
		}
		
		
	}

	public Dimension getPreferredSize() {
		return new Dimension(300, 100);
	}

	
}