package com.nasyarobby.droidnewsreader;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.nasyarobby.articlesource.ArticleSource;
import com.nasyarobby.articlesource.ArticleSourceFactory;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            ArticleAdapter.ArticleListActionListener,
            AddTopicDialogFragment.AddTopicDialogListener {

    public static List<ArticleInterface> articleList = new ArrayList<>();
    private ArticleAdapter articlesAdapter;
    ArticleSourceFactory factory;
    List<String> topics;
    private Menu topicMenu;

    public MainActivity() {
        factory = new ArticleSourceFactory();
        topics = new ArrayList<>();
        topics.add("Design Patterns");
        topics.add("Web Development");
    }

    @Override
    public void onClickAddTopicButton(DialogFragment dialog) {
        EditText topicEditText = dialog.getDialog().findViewById(R.id.topic_edit_text);
        String topic = topicEditText.getText().toString().trim();
        topics.add(topic);
        factory.getNewsapi().reset();
        ArticleSource source = factory.getTopicSource(topic);
        Toast.makeText(this, "Topic: " + topic, Toast.LENGTH_SHORT).show();
        loadArticleSource(source);
        dialog.dismiss();
        buildTopicsMenu();
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
                    articleList.addAll(source.getArticles());
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
        topicMenu = menu.addSubMenu("Topics");
        buildTopicsMenu();

        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView articleRecyclerView = findViewById(R.id.articles_recyler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(layoutManager);

        articlesAdapter = new ArticleAdapter(getApplicationContext());
        ArticleSource newsapi = factory.getSource("All Headlines");
        articleRecyclerView.setAdapter(articlesAdapter);
        loadArticleSource(newsapi);
        articleRecyclerView.addOnScrollListener(new ArticleOnScrollListener(newsapi));
    }

    private void buildTopicsMenu() {
        topicMenu.clear();
        for (int i = 0; i < topics.size(); i++) {
            topicMenu.add(1, i, i, topics.get(i));
        }
        topicMenu.add(1, topics.size(), topics.size(), "Add Topic");
    }

    public void openAddTopicFragmentDialog() {
        DialogFragment dialog = new AddTopicDialogFragment();
        dialog.show(getFragmentManager(), "topicDialog");
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
        articlesAdapter.notifyDataSetChanged();
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
        int id = item.getItemId();
        ArticlesSorter sorter = new ArticlesSorter(articleList);

        if (id == R.id.newest_article_first) {
            sorter.setSortingStrategy(new ArticlesSorter.NewestArticlesFirst());
            Toast.makeText(this, "Newest articles first", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.oldest_articles_first) {
            sorter.setSortingStrategy(new ArticlesSorter.OldestArticleFirst());
            Toast.makeText(this, "Oldest articles first", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.sort_by_source_az) {
            sorter.setSortingStrategy(new ArticlesSorter.SortBySourceAtoZ());
            Toast.makeText(this, "Sorted by source A to Z", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.sort_by_source_za) {
            sorter.setSortingStrategy(new ArticlesSorter.SortBySourceZtoA());
            Toast.makeText(this, "Sorted by source Z to A", Toast.LENGTH_SHORT).show();
        }
        sorter.sort();
        articlesAdapter.notifyDataSetChanged();
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
            if (id == topics.size()) {
                openAddTopicFragmentDialog();
            } else {
                String topic = topics.get(id);
                factory.getNewsapi().reset();
                ArticleSource source = factory.getTopicSource(topic);
                Toast.makeText(this, "Topic: " + topic, Toast.LENGTH_SHORT).show();
                loadArticleSource(source);
            }
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
