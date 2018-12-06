package com.nasyarobby.articlesource.newsapiorg;

import com.nasyarobby.articlesource.ArticleSource;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

public class NewsapiorgSourceAllHeadlines implements ArticleSource {
    private Newsapiorg	source;
    private static String SOURCE_NAME = "All Headlines";

    public NewsapiorgSourceAllHeadlines(Newsapiorg source) {
        this.source = source;
    }

    @Override
    public List<ArticleInterface> getArticles() {
        List<ArticleInterface> list = null;
        try {
            list = this.source.get();
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
