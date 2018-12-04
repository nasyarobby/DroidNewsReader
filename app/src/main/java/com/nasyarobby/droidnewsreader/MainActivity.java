package com.nasyarobby.droidnewsreader;

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
import com.nasyarobby.articlesource.newsapiorg.Newsapiorg;
import com.nasyarobby.articlesource.newsapiorg.NewsapiorgSourceAllHeadlines;
import com.nasyarobby.droidnewsreader.article.ArticleInterface;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<ArticleInterface> articleList = new ArrayList<>();
    private ArticleAdapter  articlesAdapter;

    class ArticleOnScrollListener extends RecyclerView.OnScrollListener {
        private static final String ARTICLES_LOADED_TEXT = "New articles loaded from the source.";
        ArticleSource source;
        List<ArticleInterface> list;
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
                articlesAdapter.getList().addAll(source.getArticles());
                articlesAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView articleRecyclerView = (RecyclerView) findViewById(R.id.articles_recyler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(layoutManager);

        /*try {
            articleList.add(new Article("Test", "test", null, new URL("http://www.google.com"), "test", "test", "test"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/

        ArticleSource newsapi = new NewsapiorgSourceAllHeadlines(new Newsapiorg("1dfd051041da4379987904e6b77c42d5"));
        List<ArticleInterface> newArticles = newsapi.getArticles();
        articleList.addAll(newArticles);

        articlesAdapter = new ArticleAdapter(articleList);
        articleRecyclerView.setAdapter(articlesAdapter);

        articleRecyclerView.addOnScrollListener(new ArticleOnScrollListener(newsapi));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
