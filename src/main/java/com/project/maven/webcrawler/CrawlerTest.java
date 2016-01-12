package com.project.maven.webcrawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class CrawlerTest 
{
    public static void main( String[] args )
    {
		Crawler crawler = new Crawler();
		crawler.search("http://mail-archives.apache.org/mod_mbox/maven-users/","2014");
        
       
    }
}
