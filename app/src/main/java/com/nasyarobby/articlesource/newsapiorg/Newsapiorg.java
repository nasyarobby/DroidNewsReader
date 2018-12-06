package com.nasyarobby.articlesource.newsapiorg;

import android.util.Log;

import com.nasyarobby.droidnewsreader.HttpHelper;
import com.nasyarobby.droidnewsreader.article.Article;
import com.nasyarobby.droidnewsreader.article.AndroidArticleDecorator;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Newsapiorg {
    enum Country {
        US("us");

        String code;

        Country(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code;
        }
    }

    private String apiKey;
    private String keyword;
    private Country country = Country.US;
    private String source;
    private int endpoint = 0;

    private String TOP_HEADLINES_ENDPOINTS = "https://newsapi.org/v2/top-headlines";
    private String EVERYTHING_ENDPOINTS = "https://newsapi.org/v2/everything";

    int page = 1;
    long totalResults;
    int pageSize = 20;

    public Newsapiorg(String apiKey) {
        this.apiKey = apiKey;
    }

    public Newsapiorg setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Newsapiorg setSource(String source) {
        this.source = source;
        return this;
    }

    public List<ArticleInterface> get() throws URISyntaxException, IOException, ParseException, java.text.ParseException {
        String finalURL = buildUrl();
        HttpHelper http = new HttpHelper();
        Log.d("JsonReqUrl", finalURL);
        String json = http.get(finalURL);
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        String status = (String) obj.get("status");
        List<ArticleInterface> list = null;
        if (status.equals("ok")) {
            totalResults = (long) obj.get("totalResults");
            JSONArray articles = (JSONArray) obj.get("articles");
            list = new ArrayList<>(articles.size());
            for (int i = 0; i < articles.size(); i++) {
                JSONObject articleObj = (JSONObject) articles.get(i);
                String title = (String) articleObj.get("title");
                String description = (String) articleObj.get("description");
                URL url = new URL((String) articleObj.get("url"));
                String content = (String) articleObj.get("content");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date publishedAt = df.parse((String) articleObj.get("publishedAt"));
                JSONObject sourceObj = (JSONObject) articleObj.get("source");
                String sourceName = (String) sourceObj.get("name");
                String author = (String) articleObj.get("author");
                ArticleInterface article = new Article(title, description, publishedAt, url, content, sourceName, author);
                article = new AndroidArticleDecorator(article, (String) articleObj.get("urlToImage"));
                list.add(article);
            }

            page++;
        }

        return list;
    }

    public Newsapiorg useHeadlinesEndpoint() {
        endpoint = 0;
        return this;
    }

    public Newsapiorg useEverythingEndpoint() {
        endpoint = 1;
        return this;
    }

    private String buildUrl() throws URISyntaxException {
        URI url = endpoint==0 ? new URI(TOP_HEADLINES_ENDPOINTS) : new URI(EVERYTHING_ENDPOINTS);
        
        if (source != null) {
            url = HttpHelper.appendUri(url, "sources=" + source);
        }
        else if(keyword!= null) {
            url = HttpHelper.appendUri(url, "q=" + keyword);
        }
        else {
            url = HttpHelper.appendUri(url, "country=" + country);
        }
        url = HttpHelper.appendUri(url, "apiKey=" + apiKey);
        url = HttpHelper.appendUri(url, "pageSize=" + pageSize);
        url = HttpHelper.appendUri(url, "page=" + page);

        url = HttpHelper.appendUri(url, "language=en");
        return url.toString();
    }

    public void reset() {
        keyword = null;
        source = null;
        page = 1;
    }

}
