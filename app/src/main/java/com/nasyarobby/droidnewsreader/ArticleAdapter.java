package com.nasyarobby.droidnewsreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private Context context;

    public interface ArticleListActionListener {
        void onClickItem(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView excerpt;
        TextView source;
        TextView publishedAt;
        ImageView image;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.article_title_text_view);
            excerpt = v.findViewById(R.id.article_excerpt_text_view);
            source = v.findViewById(R.id.source_text_view);
            publishedAt = v.findViewById((R.id.published_at_text_view));
            image = v.findViewById(R.id.article_image_view);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ArticleListActionListener mListener = (ArticleListActionListener) v.getContext();
            mListener.onClickItem(getAdapterPosition());
        }
    }

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_image_fill_horizontal, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AndroidArticleWrapper article = (AndroidArticleWrapper) MainActivity.articleList.get(position);
        holder.title.setText(article.getTitle());

        if (article.isRead())
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.colorRead));
        else
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.colorUnread));

        holder.excerpt.setText(article.getDescription());
        holder.source.setText(article.getSource());
        DateFormat df = new SimpleDateFormat("MM/dd KK:mma");
        holder.publishedAt.setText(df.format(article.getPublishedAt()));
        String imageUrl = article.getImage();
        if (article.getBitmap() == null) {
            DownloadImageTask task = null;
            if (imageUrl != null) {
                task = new DownloadImageTask(holder.image);
                task.execute(article.getImage());
                try {
                    article.setBitmap(task.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } else {
            holder.image.setImageBitmap(article.getBitmap());
        }

    }

    @Override
    public int getItemCount() {
        return MainActivity.articleList.size();
    }
}
