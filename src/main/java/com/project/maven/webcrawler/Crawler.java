package com.project.maven.webcrawler;

public class Crawler {

	public void search(String url)
	{

		CrawlerConnection conn = new CrawlerConnection();
		conn.crawl(url); 


	}




}
