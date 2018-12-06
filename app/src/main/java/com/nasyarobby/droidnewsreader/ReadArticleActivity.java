package com.nasyarobby.droidnewsreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nasyarobby.droidnewsreader.article.ArticleImageDecorator;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

public class ReadArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int position = extras.getInt("POSITION");
        ArticleImageDecorator article = (ArticleImageDecorator) MainActivity.articleList.get(position);

        setContentView(R.layout.activity_read_article);
        TextView title = findViewById(R.id.read_article_title);
        TextView source = findViewById(R.id.read_article_source);
        TextView content = findViewById(R.id.read_article_content);
        ImageView imageView = findViewById(R.id.read_article_image_view);

        title.setText(article.getTitle());
        source.setText(article.getSource());
        content.setText(article.getContent());

        if(article.getImage() != null) {
            new DownloadImageTask(imageView).execute(article.getImage());
        }

    }
}
