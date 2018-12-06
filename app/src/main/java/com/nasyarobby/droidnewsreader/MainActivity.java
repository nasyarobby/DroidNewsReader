package com.nasyarobby.droidnewsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nasyarobby.articlesource.ArticleSource;
import com.nasyarobby.articlesource.ArticleSourceFactory;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ArticleAdapter.ArticleListActionListener {

    public static List<ArticleInterface> articleList = new ArrayList<>();
    private ArticleAdapter articlesAdapter;
    ArticleSourceFactory factory;
    List<String> topics;

    public MainActivity() {
        factory = new ArticleSourceFactory();
        topics = new ArrayList<>();
        topics.add("Design Patterns");
        topics.add("Web Development");
    }

    class ArticleOnScrollListener extends RecyclerView.OnScrollListener {
        private static final String ARTICLES_LOADED_TEXT = "New articles loaded from the source.";
        ArticleSource source;
        public List<ArticleInterface> list;

        ArticleOnScrollListener(ArticleSource source) {
            this.source = source;
            this.list = list;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(1)) {
                Toast.makeText(MainActivity.this, ARTICLES_LOADED_TEXT, LENGTH_SHORT).show();
                List<ArticleInterface> newArticles = source.getArticles();
                if (newArticles.size() > 0) {
                    articlesAdapter.getList().addAll(source.getArticles());
                    articlesAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        Menu headlinesMenu = menu.addSubMenu("Headlines");
        for (int i = 0; i < factory.getSourceNames().size(); i++) {
            headlinesMenu.add(0, i, i, factory.getSourceNames().get(i));
        }

        Menu topicMenu = menu.addSubMenu("Topics");
        for (int i = 0; i < topics.size(); i++) {
            topicMenu.add(1, i, i, topics.get(i));
        }

        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView articleRecyclerView = findViewById(R.id.articles_recyler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(layoutManager);

        articlesAdapter = new ArticleAdapter(articleList);
        ArticleSource newsapi = factory.getSource("All Headlines");
        articleRecyclerView.setAdapter(articlesAdapter);
        loadArticleSource(newsapi);
        articleRecyclerView.addOnScrollListener(new ArticleOnScrollListener(newsapi));
    }

    protected void loadArticleSource(ArticleSource source) {
        List<ArticleInterface> newArticles = source.getArticles();

        if (newArticles != null && newArticles.size() > 0) {
            articleList.clear();
            articleList.addAll(newArticles);
            articlesAdapter.notifyDataSetChanged();
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(source.getName());
        } else {
            Toast.makeText(this, "No new articles found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int groupId = item.getGroupId();

        if (groupId == 0) {
            String sourceName = factory.getSourceNames().get(id);
            ArticleSource source = factory.getSource(sourceName);
            if (source != null) {
                Toast.makeText(this, sourceName, Toast.LENGTH_SHORT).show();
                loadArticleSource(source);
            } else
                Toast.makeText(this, "Source not found", Toast.LENGTH_SHORT).show();
        } else {
            String topic = topics.get(id);
            factory.getNewsapi().reset();
            ArticleSource source = factory.getTopicSource(topic);
            Toast.makeText(this, "Topic: " + topic, Toast.LENGTH_SHORT).show();
            loadArticleSource(source);
        }

        // CLOSE THE DRAWER
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openReadArticleActivity(int position) {
        Intent intent = new Intent(this, ReadArticleActivity.class);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }

    @Override
    public void onClickItem(int position) {
        openReadArticleActivity(position);
    }
}
