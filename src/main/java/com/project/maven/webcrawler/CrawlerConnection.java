package com.project.maven.webcrawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerConnection {
	private static final Logger LOGGER = Logger.getLogger(CrawlerConnection.class);
	Set<String> bodyText = new HashSet<String>();

	public Document returnHtmlDoc(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	public Set<String> crawl(Document doc, String keyword) {
		Set<String> links = new HashSet<String>();
		try {
			Elements linksOnPage = doc.select("a[href]");

			for (Element link : linksOnPage) {
				if (link.absUrl("href").toLowerCase()
						.contains("mail-archives.apache.org/mod_mbox/maven-users/" + keyword.toLowerCase())) {
					links.add(link.absUrl("href"));
				}
			}
		} catch (Exception exp) {
			LOGGER.error(exp);
		}

		return links;
	}

	public boolean searhMail(Document doc, String searchWord) {
		boolean result = false;
		boolean finalResult = false;
		try {
			Elements linksOnPage = doc.select("a[href]");
			String htmlString = doc.toString();
			String dateYear = "Date:(.*)" + searchWord;
			Pattern pattern = Pattern.compile(dateYear);
			Matcher matcher = pattern.matcher(htmlString);
			if (matcher.find()) {

				result = true;
			} else {
				result = false;
			}
			if (linksOnPage.size() == 0 && result == true) {
				finalResult = true;

			} else {
				finalResult = false;
			}

		} catch (Exception exp) {
			LOGGER.error(exp);
		}

		return finalResult;
	}
}
