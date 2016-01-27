package com.project.maven.webcrawler;

import java.io.IOException;
import java.util.HashSet;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlerQueueThread implements Runnable {

	BlockingQueue<String> pagesToVisit = null;
	String year;
	BlockingQueue<String> downloadUrlsQueue = null;
	Set<String> urls = new HashSet<String>();
	private static final Logger LOGGER = Logger.getLogger(CrawlerQueueThread.class);

	public Document returnHtmlDoc(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {

		}
		return doc;

	}

	private static Set<String> pagesVisited = new HashSet<String>();

	public CrawlerQueueThread(BlockingQueue<String> addUrlsQueue, BlockingQueue<String> downloadUrlsQueue,
			String year) {
		this.pagesToVisit = addUrlsQueue;
		this.downloadUrlsQueue = downloadUrlsQueue;
		this.year = year;

	}

	public void search(String keyword) throws InterruptedException {

		while (!this.pagesToVisit.isEmpty()) {
			String currentUrl = null;
			CrawlerConnection crawlConn = new CrawlerConnection();
			currentUrl = this.nextUrl();
			boolean success = crawlConn.searhMail(returnHtmlDoc(currentUrl), keyword);
			if (success) {
				downloadUrlsQueue.put(currentUrl);
				LOGGER.debug("Download url :" + currentUrl);
			} else {
				urls = crawlConn.crawl(returnHtmlDoc(currentUrl), keyword);

				for (String url : urls) {
					this.pagesToVisit.put(url);

				}
			}
		}

		LOGGER.debug("All the mails have been downloaded");

	}

	private String nextUrl() {
		String nextUrl = null;

		do {

			try {
				nextUrl = this.pagesToVisit.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (pagesVisited.contains(nextUrl) && !this.pagesToVisit.isEmpty());

		pagesVisited.add(nextUrl);
		return nextUrl;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			search(year);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
