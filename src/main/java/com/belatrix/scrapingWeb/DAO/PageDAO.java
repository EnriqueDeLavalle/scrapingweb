package com.belatrix.scrapingWeb.DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

import com.belatrix.scrapingWeb.model.Page;


public class PageDAO {

	// Constante para el log de las operaciones
	private final static Logger LOGGER = Logger.getLogger(PageDAO.class.getName());

	public static ArrayList<Page> getPagesFrom(String pathDir) {

		ArrayList<Page> pages = new ArrayList<Page>();

		Path path = FileSystems.getDefault().getPath(pathDir);
		Charset charset = Charset.forName("US-ASCII");

		try {
			BufferedReader reader = Files.newBufferedReader(path, charset);
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				pages.add(new Page(line));
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

		return pages;

	}

	public static boolean saveScrapingResult(List<Page> pages, String pathDir) throws MalformedURLException {
		BufferedWriter writer = null;
		Charset charset = Charset.forName("US-ASCII");
		for (Page page : pages) {
			URL url = new URL(page.getUrl());
			Path path = FileSystems.getDefault().getPath(pathDir + "/"+ url.getHost()+".txt");
			System.out.println(url.getHost()+".txt");
			try  {
				writer = Files.newBufferedWriter(path, charset);
				writer.write(page.getUrl() + "\n");
				List<String> references = Collections.synchronizedList(page.getRef()); 
				for (String ref : references) {
					writer.write(ref + "\n");
				}
				
			} catch (IOException x) {				
				return false;
			}finally { 
				try {
					writer.close();
				} catch (IOException e) {				
				}
			}
		}

		return true;
	}


}
