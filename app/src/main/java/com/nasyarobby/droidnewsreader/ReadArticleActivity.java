package com.nasyarobby.droidnewsreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ReadArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int position = extras.getInt("POSITION");
        AndroidArticleWrapper article = (AndroidArticleWrapper) MainActivity.articleList.get(position);
        article.setRead();
        setContentView(R.layout.activity_read_article);
        TextView title = findViewById(R.id.read_article_title);
        TextView source = findViewById(R.id.read_article_source);
        TextView content = findViewById(R.id.read_article_content);
        ImageView imageView = findViewById(R.id.read_article_image_view);

        title.setText(article.getTitle());
        source.setText(article.getSource());
        String modifiedContent = article.getContent().replaceAll("\\[\\+\\d{1,} chars\\]", "[read more]");
        content.setText(modifiedContent);

        if(article.getImage() != null) {
            new DownloadImageTask(imageView).execute(article.getImage());
        }

    }
}
