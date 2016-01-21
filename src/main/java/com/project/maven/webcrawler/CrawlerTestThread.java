package com.project.maven.webcrawler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CrawlerTestThread  {
	
	static ExecutorService addUrl = Executors.newFixedThreadPool(12);
	static ExecutorService downloadUrl=Executors.newFixedThreadPool(12);
	static String year = "2014";
	static BlockingQueue<String> addUrlsQueue = new LinkedBlockingQueue<String>() ;
	static BlockingQueue<String> downloadUrlsQueue = new LinkedBlockingQueue<String>();
	
	
	public static void main(String...args){
		try {
			addUrlsQueue.put("http://mail-archives.apache.org/mod_mbox/maven-users/");
			for(int i= 0;i<12;i++){
				Runnable urlWorker = new CrawlerQueueThread(addUrlsQueue,downloadUrlsQueue,year);
				addUrl.execute(urlWorker);
			}
			
			for(int i= 0;i<12;i++){
				Runnable downloadWorker =  new DownloadMailThread(downloadUrlsQueue,year);
				downloadUrl.execute(downloadWorker);
			}
			addUrl.shutdown();
			downloadUrl.shutdown();
			addUrl.awaitTermination(1, TimeUnit.DAYS);
			
			downloadUrl.awaitTermination(1, TimeUnit.DAYS);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
