package com.nasyarobby.droidnewsreader;

import android.graphics.Bitmap;

import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.net.URL;
import java.util.Date;

public class AndroidArticleWrapper implements ArticleInterface {
    private ArticleInterface article;
    private String imageUrl;
    private boolean read;
    private Bitmap bitmap;

    public AndroidArticleWrapper(ArticleInterface article, String imageUrl) {
        this.article = article;
        this.imageUrl = imageUrl;

        this.read = false;
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

    public Bitmap getBitmap() { return bitmap;}

    public void setBitmap(Bitmap bmp) { bitmap = bmp;}

    public boolean isRead() {
        return read;
    }

    public void setRead() {
        read = true;
    }
}
