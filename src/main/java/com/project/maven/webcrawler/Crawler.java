package com.project.maven.webcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crawler {private Set<String> pagesVisited = new HashSet<String>();
private List<String> pagesToVisit = new LinkedList<String>();

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
			mail.downloadMail(currentUrl);
			break;

		}
		else{
			this.pagesToVisit.addAll(crawlConn.crawl(currentUrl));
		}
		

		System.out.println("\n**Done** Visited " + this.pagesVisited.size()
				+ " web page(s)");
	} while (!this.pagesToVisit.isEmpty());

}

private String nextUrl() {
	String nextUrl;
	do {
		nextUrl = this.pagesToVisit.remove(0);
	} while (this.pagesVisited.contains(nextUrl));
	this.pagesVisited.add(nextUrl);
	return nextUrl;
}}
