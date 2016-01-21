package com.project.maven.webcrawler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class CrawlerQueueThread implements Runnable{
	
	BlockingQueue<String> pagesToVisit = new LinkedBlockingQueue<String>() ;
	String year;
	BlockingQueue<String> downloadUrlsQueue = new LinkedBlockingQueue<String>();
	List<String> urls = new LinkedList<String>();
	private static final Logger LOGGER = Logger.getLogger(CrawlerQueueThread.class);
	
	public CrawlerQueueThread(BlockingQueue<String> addUrlsQueue,BlockingQueue<String> downloadUrlsQueue,String year){
		this.pagesToVisit=addUrlsQueue;
		this.downloadUrlsQueue=downloadUrlsQueue;
		this.year = year;
	}
	private Set<String> pagesVisited = new HashSet<String>();


	public void search(String keyword) throws InterruptedException {
		do {
			String currentUrl = null;
			CrawlerConnection crawlConn = new CrawlerConnection();
			if (this.pagesToVisit.isEmpty()) {
				Thread.sleep(3000);

			} else {
				currentUrl = this.nextUrl();
			}

			boolean success = crawlConn.searhMail(currentUrl,keyword);
			if (success) {
				downloadUrlsQueue.put(currentUrl);


			}
			else{
				urls = crawlConn.crawl(currentUrl,keyword);
				for(int i =0;i<urls.size();i++)
				this.pagesToVisit.put(urls.get(i));
			}



		} while (!this.pagesToVisit.isEmpty());
		LOGGER.debug("All the mails have been downloaded");

	}

	private String nextUrl() {
		String nextUrl = null;

		
		do 
		{
			
			try {
				nextUrl=this.pagesToVisit.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}while(this.pagesVisited.contains(nextUrl) && !(this.pagesToVisit.isEmpty()));
		LOGGER.debug("while loop completed for next URL");

		this.pagesVisited.add(nextUrl);
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
