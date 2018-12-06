package com.nasyarobby.droidnewsreader;

import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.util.List;

interface SortingStrategyInterface<T> {
    void sort(List<T> list);
}
