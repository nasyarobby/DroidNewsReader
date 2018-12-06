package com.nasyarobby.articlesource;

import com.nasyarobby.articlesource.newsapiorg.Newsapiorg;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

public class BbcNewsSource implements ArticleSource {
    Newsapiorg source;
    static final String SOURCE_KEY = "bbc-news";
    static final String SOURCE_NAME = "BBC News";

    public BbcNewsSource(Newsapiorg source) {
        this.source = source;
    }

    @Override
    public List<ArticleInterface> getArticles() {

        List<ArticleInterface> list = null;
        try {
            source.setSource(SOURCE_KEY);
            list = source.get();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public String getName() {
        return SOURCE_NAME;
    }
}
