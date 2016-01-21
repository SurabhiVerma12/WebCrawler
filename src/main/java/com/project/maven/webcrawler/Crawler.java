package com.project.maven.webcrawler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class Crawler {
	private static final Logger LOGGER = Logger.getLogger(Crawler.class);

	private Set<String> pagesVisited = new HashSet<String>();
	private Set<String> pagesToVisit = new HashSet<String>();

	public void search(String url, String keyword) {
		do {
			String currentUrl;
			CrawlerConnection crawlConn = new CrawlerConnection();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}

			boolean success = crawlConn.searhMail(currentUrl,keyword);
			if (success) {
				DownloadMail mail = new DownloadMail();
				mail.downloadMail(currentUrl,keyword,"Mail-"+keyword);
			

			}
			else{
				this.pagesToVisit.addAll(crawlConn.crawl(currentUrl,keyword));
			}


			
		} while (!this.pagesToVisit.isEmpty());
		 LOGGER.debug("All the mails have been downloaded");

	}

	private String nextUrl() {
		String nextUrl = null;
		
		Iterator<String> it = this.pagesToVisit.iterator();
	    do 
	    {
	    	nextUrl=it.next().toString();
	    	it.remove();
	    }while(this.pagesVisited.contains(nextUrl) );
	    
	    this.pagesVisited.add(nextUrl);
		return nextUrl;
	}}
