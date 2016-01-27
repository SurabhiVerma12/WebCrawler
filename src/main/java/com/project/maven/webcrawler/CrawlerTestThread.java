package com.project.maven.webcrawler;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CrawlerTestThread {

	static ExecutorService addUrl = Executors.newFixedThreadPool(12);
	static ExecutorService downloadUrl = Executors.newFixedThreadPool(12);
	
	static BlockingQueue<String> addUrlsQueue = new LinkedBlockingQueue<String>();
	static BlockingQueue<String> downloadUrlsQueue = new LinkedBlockingQueue<String>();
	static Set<String> PagesVigited = new HashSet<String>();
	

	public static void main(String... args) {
		try {
			
			  Scanner in = new Scanner(System.in);
			 
		      System.out.println("Enter the URL to be crawled");
		      String crawlUrl = in.nextLine();
		      System.out.println("Enter the year ");
		      String year = in.nextLine();
		     
		      
		 
			addUrlsQueue.put(crawlUrl);
			for (int i = 0; i < 12; i++) {
				Runnable urlWorker = new CrawlerQueueThread(addUrlsQueue, downloadUrlsQueue, year);
				addUrl.execute(urlWorker);
			}

			for (int i = 0; i < 12; i++) {
				Runnable downloadWorker = new DownloadMailThread(downloadUrlsQueue, year);
				downloadUrl.execute(downloadWorker);
			}
			addUrl.shutdown();
			downloadUrl.shutdown();
			addUrl.awaitTermination(1, TimeUnit.DAYS);
			downloadUrl.awaitTermination(1, TimeUnit.DAYS);

		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}

}
