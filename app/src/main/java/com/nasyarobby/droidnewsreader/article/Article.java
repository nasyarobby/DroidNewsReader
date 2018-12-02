package com.nasyarobby.droidnewsreader.article;

import java.net.URL;
import java.util.Date;

public class Article implements ArticleInterface {
    private String title;
    private String description;
    private Date publishedAt;
    private URL url;
    private String content;
    private String source;
    private String author;

    public Article(String title, String description, Date publishedAt, URL url, String content, String source, String author) {
        setTitle(title);
        setDescription(description);
        setPublishedAt(publishedAt);
        setUrl(url);
        setContent(content);
        setSource(source);
        setAuthor(author);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the publishedAt
     */
    public Date getPublishedAt() {
        return publishedAt;
    }

    /**
     * @param publishedAt the publishedAt to set
     */
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}
