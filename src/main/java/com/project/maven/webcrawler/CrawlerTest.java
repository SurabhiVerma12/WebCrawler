package com.project.maven.webcrawler;



import org.apache.log4j.Logger;


/**
 * Hello world!
 *
 */
public class CrawlerTest 
{
	private static final Logger LOGGER = Logger.getLogger(CrawlerTest.class);
	public static void main( String[] args )
	{
		Crawler crawler = new Crawler();
		LOGGER.debug("starting the program");
		crawler.search("http://mail-archives.apache.org/mod_mbox/maven-users/","2014");


	}
}
