package com.nasyarobby.articlesource;

import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.util.List;

/**
 * This interface abstract the article source.
 * The concrete object of this interface can be used to get the list of articles using the
 * getArticle() method.
 */

public interface ArticleSource {
    /**
     * getArticles
     * @return List of Article
     */
    List<ArticleInterface> getArticles();
}
