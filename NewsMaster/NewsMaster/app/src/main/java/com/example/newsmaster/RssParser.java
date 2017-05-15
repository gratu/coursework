package com.example.newsmaster;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 20.4.2017.
 */
public class RssParser extends DefaultHandler {

    private List<RssItem> rssItems;
    private RssItem currentItem;
    private boolean parsingTitle;
    private boolean parsingLink;
    private boolean parsingText;
    private boolean parsingImgUrl;
    private boolean parsingPubDate;

    private String elem;
    private String imglink;

    public RssParser() {
        rssItems = new ArrayList<RssItem>();
    }

    public List<RssItem> getItems() {
        return rssItems;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        switch(qName) {
            case "item":
                currentItem = new RssItem();
                break;
            case "title":
                parsingTitle = true;
                break;
            case "link":
                parsingLink = true;
                break;
            case "description":
                parsingText = true;
                break;
            case "image":
                break;
            case "url":
                parsingImgUrl = true;
                break;
            case "pubDate":
                parsingPubDate = true;
                break;

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        switch(qName) {
            case "item":
                currentItem.setImglink(imglink);
                rssItems.add(currentItem);
                currentItem = null;
                break;
            case "title":
                parsingTitle = false;
                break;
            case "link":
                parsingLink = false;
                break;
            case "description":
                parsingText = false;
                if (currentItem != null) {

                    currentItem.setText(elem);
                }
                elem="";
                break;
            case "image":
                break;
            case "url":
                parsingImgUrl = false;
                break;
            case "pubDate":
                parsingPubDate = false;
                break;

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingTitle) {
            if (currentItem != null){
                currentItem.setTitle(new String(ch, start, length));
            }else{

            }
        } else if (parsingLink) {
            if (currentItem != null)
                currentItem.setLink(new String(ch, start, length));

        }else if (parsingText) {
            elem= elem + new String(ch, start, length);
        }else if (parsingImgUrl) {
            imglink = new String(ch, start, length);

        }else if (parsingPubDate) {
            if (currentItem != null)
                currentItem.setPubDate(new String(ch, start, length));

        }

    }

}
