package com.project.maven.webcrawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerConnection {
    
    private List<String> links = new LinkedList<String>();
    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url);
            Document htmlDocument = connection.get();
            
            if(connection.response().statusCode() == 200) 
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
            }
            for(int i=0 ; i<links.size();i++){
              System.out.println(links.get(i));
            }
            
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }


    

}
