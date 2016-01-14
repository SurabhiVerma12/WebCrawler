package com.project.maven.webcrawler;

import java.io.IOException;


import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerConnection {
	private static final Logger LOGGER = Logger.getLogger(CrawlerConnection.class);


	public List<String> crawl(String url,String keyword) {
		List<String> links = new LinkedList<String>();
		try {
			Elements linksOnPage = Jsoup.connect(url).get().select("a[href]");


			for (Element link : linksOnPage) {
				if (link.absUrl("href")
						.contains("mail-archives.apache.org/mod_mbox/maven-users/"+keyword))
					links.add(link.absUrl("href"));
				LOGGER.debug("Found : " + link.absUrl("href"));
			}
		} catch (IOException exp) {
			LOGGER.error(exp);
		}

		catch (IllegalArgumentException exp) {
			LOGGER.error(exp);
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
				LOGGER.debug( htmlString);
				result = true;
			}else{
				result = false;
			}


		}	catch (IOException exp) 
		{
			LOGGER.error(exp);
		}
		catch (IllegalArgumentException exp) {
			LOGGER.error(exp);
		}
		return result;
	}
}
