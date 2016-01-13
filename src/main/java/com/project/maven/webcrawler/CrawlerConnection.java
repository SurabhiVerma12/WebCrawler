package com.project.maven.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerConnection {
	

public List<String> crawl(String url) {
	List<String> links = new LinkedList<String>();
	try {
		Elements linksOnPage = Jsoup.connect(url).get().select("a[href]");
		System.out.println("Found (" + linksOnPage.size() + ") links");
		for (Element link : linksOnPage) {
			if (link.absUrl("href")
					.contains("mail-archives.apache.org/mod_mbox/maven-users/2014"))
			links.add(link.absUrl("href"));
		}
	} catch (IOException ioe) {
		System.out.println(ioe);
	}
	
	catch (IllegalArgumentException ioe) {
		System.out.println(ioe);
	}
	return links;
}

public boolean searhMail(String url,String searchWord) {
	boolean result = false;
	try {
		String htmlString = Jsoup.connect(url).get().toString();
		String dateYear="Date:(.*)"+searchWord;
		Pattern pattern = Pattern.compile(dateYear);
		Matcher matcher = pattern.matcher(htmlString);
		if (matcher.find()) {
			System.out.println("checking" + htmlString);
			result = true;
		}else{
			result = false;
		}
		

	}	catch (IOException exp) 
			{
		System.out.println(exp);
			}
	return result;
}
}
