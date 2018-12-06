package com.nasyarobby.droidnewsreader.article;

import java.net.URL;
import java.util.Date;

public class ArticleImageDecorator implements ArticleInterface {
    private ArticleInterface article;
    private String imageUrl;

    public ArticleImageDecorator(ArticleInterface article, String imageUrl) {
        this.article = article;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getTitle() {
        return article.getTitle();
    }

    @Override
    public String getDescription() {
        return article.getDescription();
    }

    @Override
    public Date getPublishedAt() {
        return article.getPublishedAt();
    }

    @Override
    public URL getUrl() {
        return article.getUrl();
    }

    @Override
    public String getContent() {
        return article.getContent();
    }

    @Override
    public String getSource() {
        return article.getSource();
    }

    @Override
    public String getAuthor() {
        return article.getAuthor();
    }

    public String getImage() {
        return imageUrl;
    }
}
