package com.nasyarobby.droidnewsreader.article;

import java.net.URL;
import java.util.Date;

public interface ArticleInterface {
    String getTitle();

    String getDescription();

    Date getPublishedAt();

    URL getUrl();

    String getContent();

    String getSource();

    String getAuthor();
}
