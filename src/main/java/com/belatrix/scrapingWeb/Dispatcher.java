package com.belatrix.scrapingWeb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.belatrix.scrapingWeb.DAO.PageDAO;
import com.belatrix.scrapingWeb.model.Page;

public class Dispatcher {

	// Constante para el log de las operaciones
	private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	// Atributos del Dispatcher
	private ExecutorService executorService;
	private final Semaphore semp;
	private List<Page> pages;
	private String pathDir;
	private String pathFile;

	public Dispatcher(String pathDir, String pathFile) {
		this.executorService = Executors.newCachedThreadPool();
		// Semaforo con la cantidad maxima de concurrencia
		this.semp = new Semaphore(10);
		this.pathDir = pathDir;
		this.pathFile = pathFile;
		pages = Collections.synchronizedList(PageDAO.getPagesFrom(this.pathFile));
	}

	public void dispatchScraping() throws InterruptedException {
		JOptionPane.showMessageDialog(null, "Starting process, wait until the confimation message...");
		for (Page page : pages) {
			executorService.submit(scrapingPages(page));
		}
		this.finalizandoProceso(90);

		try {
			PageDAO.saveScrapingResult(pages, this.pathDir);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "Successful process!");

	}

	private Runnable scrapingPages(final Page page) {
		Runnable run = new Runnable() {
			public void run() {
				try {
					// se obtiene el semaforo para el control de los hilos concurrentes
					// si no se encuentra disponible espera que se libere alguna ejecucion
					semp.acquire();
					Thread.sleep(2 * 1000);

					try {
						System.out.println("Inicio");
						Document document = Jsoup.connect(page.getUrl()).get();
						String title = document.title(); // Get title
						page.getRef().add(title);
						System.out.println("Finalizo");
					} catch (IOException e) {
					}

					semp.release();
				} catch (InterruptedException e) {

					LOGGER.info("Error Tecnico");
				}

			}

		};
		return run;
	}

	private void finalizandoProceso(long limiteTiempo) {
		LOGGER.info("finalizando");
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(limiteTiempo, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			LOGGER.info("Error Tecnico");
		}

	}

}
