package com.nasyarobby.droidnewsreader;

import com.nasyarobby.droidnewsreader.article.Article;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArticlesSorter {

    public static class NewestArticlesFirst implements SortingStrategyInterface<ArticleInterface> {

        @Override
        public void sort(List<ArticleInterface> list) {
            Collections.sort(list, new Comparator<ArticleInterface>() {
                @Override
                public int compare(ArticleInterface o1, ArticleInterface o2) {
                    int date1 = (int) o1.getPublishedAt().getTime();
                    int date2 = (int) o2.getPublishedAt().getTime();

                    return date2 - date1;
                }
            });
        }
    }

    public static class OldestArticleFirst implements SortingStrategyInterface<ArticleInterface> {

        @Override
        public void sort(List<ArticleInterface> list) {
            Collections.sort(list, new Comparator<ArticleInterface>() {
                @Override
                public int compare(ArticleInterface o1, ArticleInterface o2) {
                    int date1 = (int) o1.getPublishedAt().getTime();
                    int date2 = (int) o2.getPublishedAt().getTime();

                    return date1 - date2;
                }
            });
        }
    }

    public static class SortBySourceAtoZ implements SortingStrategyInterface<ArticleInterface> {

        @Override
        public void sort(List<ArticleInterface> list) {
            Collections.sort(list, new Comparator<ArticleInterface>() {
                @Override
                public int compare(ArticleInterface o1, ArticleInterface o2) {
                    return o1.getSource().compareTo(o2.getSource());
                }
            });
        }
    }

    public static class SortBySourceZtoA implements SortingStrategyInterface<ArticleInterface> {

        @Override
        public void sort(List<ArticleInterface> list) {
            Collections.sort(list, new Comparator<ArticleInterface>() {
                @Override
                public int compare(ArticleInterface o1, ArticleInterface o2) {
                    return o2.getSource().compareTo(o1.getSource());
                }
            });
        }
    }

    private List<ArticleInterface> list;
    private SortingStrategyInterface sortingStrategy;

    public ArticlesSorter(List<ArticleInterface> list) {
        this.list = list;
    }

    public List<ArticleInterface> sort() {
        sortingStrategy.sort(list);
        return list;
    }
    public void setSortingStrategy(SortingStrategyInterface<ArticleInterface> sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }
}
