package com.nasyarobby.articlesource;

import com.nasyarobby.articlesource.newsapiorg.Newsapiorg;
import com.nasyarobby.articlesource.newsapiorg.NewsapiorgSourceAllHeadlines;
import com.nasyarobby.articlesource.newsapiorg.NewsapiorgSourceByTopic;

import java.util.ArrayList;
import java.util.List;

public class ArticleSourceFactory {
    private Newsapiorg newsapi;
    private List<String> sources;
    public ArticleSourceFactory() {
        newsapi = new Newsapiorg("1dfd051041da4379987904e6b77c42d5");

        sources = new ArrayList<>();
        sources.add("All Headlines");
        sources.add("BBC News");
        sources.add("CNN News");
    }

    public ArticleSource getSource(String name) {
        newsapi.reset();

        ArticleSource source = null;
        if(name.equals("All Headlines")) {
            source = new NewsapiorgSourceAllHeadlines(newsapi);
        }
        else if(name.equals("BBC News")) {
            source = new BbcNewsSource(newsapi);
        } else if(name.equals("CNN News")){
            source = new CnnSource(newsapi);
        }
        return source;
    }

    public Newsapiorg getNewsapi() {
        return newsapi;
    }

    public List<String> getSourceNames() {
        return sources;
    }

    public ArticleSource getTopicSource(String topic) {
        return new NewsapiorgSourceByTopic(newsapi, topic);
    }
}
