package com.nasyarobby.droidnewsreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasyarobby.droidnewsreader.article.ArticleInterface;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<ArticleInterface> list;

    public interface ArticleListActionListener {
        void onClickItem(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView excerpt;
        TextView source;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.article_title_text_view);
            excerpt = v.findViewById(R.id.article_excerpt_text_view);
            source = v.findViewById(R.id.source_text_view);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ArticleListActionListener mListener = (ArticleListActionListener) v.getContext();
            mListener.onClickItem(getAdapterPosition());
        }
    }

    public ArticleAdapter(List<ArticleInterface> articles) {
        list = articles;
    }

    public List<ArticleInterface> getList() {
        return list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.article, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleInterface article = list.get(position);
        holder.title.setText(article.getTitle());
        holder.excerpt.setText(article.getDescription());
        holder.source.setText(article.getSource());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
