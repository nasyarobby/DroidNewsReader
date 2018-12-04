package com.nasyarobby.articlesource.newsapiorg;
import com.nasyarobby.droidnewsreader.HttpHelper;
import com.nasyarobby.droidnewsreader.article.Article;
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

    private String	apiKey;
    private String	keyword;

    private String	EVERYTHING_ENDPOINTS	= "https://newsapi.org/v2/everything";

    public Newsapiorg(String apiKey) {
        this.apiKey = apiKey;
    }

    public Newsapiorg setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public List<ArticleInterface> get() throws URISyntaxException, IOException, ParseException, java.text.ParseException {
        String finalURL = buildUrl();
        HttpHelper http = new HttpHelper();
        String json = http.get(finalURL);
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        String status = (String) obj.get("status");
        List<ArticleInterface> list = null;
        if (status.equals("ok")) {
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
                list.add(article);
            }
        }

        return list;
    }

    private String buildUrl() throws URISyntaxException {
        URI url;
        url = new URI(EVERYTHING_ENDPOINTS);

        if (keyword != null)
            url = HttpHelper.appendUri(url, "q=" + keyword);

        url = HttpHelper.appendUri(url, "apiKey=" + apiKey);

        url = HttpHelper.appendUri(url, "language=en");
        return url.toString();
    }

}
