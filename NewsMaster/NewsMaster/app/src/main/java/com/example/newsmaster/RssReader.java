package com.example.newsmaster;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class RssReader {

    private String rssUrl;

    public RssReader(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public List<RssItem> getItems() throws Exception {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        RssParser handler = new RssParser();


        saxParser.parse(rssUrl, handler);

        return handler.getItems();

    }

}