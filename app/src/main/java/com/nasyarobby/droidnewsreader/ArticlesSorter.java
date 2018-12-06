package com.nasyarobby.droidnewsreader;

import com.nasyarobby.droidnewsreader.article.Article;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.util.List;

public class ArticlesSorter {
    private List<ArticleInterface> list;
    private SortingStrategyInterface sortingStrategy;

    public ArticlesSorter(List<ArticleInterface> list) {
        this.list = list;
    }

    public List<ArticleInterface> sort() {
        sortingStrategy.sort(list);
        return list;
    }
    public void setSortingStrategy(SortingStrategyInterface sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }
}
