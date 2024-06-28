package com.example.trabajoPracticoIntegrador;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.trabajoPracticoIntegrador.models.Noticia;

import java.text.SimpleDateFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Noticia> newsList;

    private final Handler handler;

    private final SimpleDateFormat simpleDateFormat;
    private final OnItemClick listener;


    @SuppressLint("SimpleDateFormat")
    public Adapter(List<Noticia> newsList, Handler handler,OnItemClick listener) {

        this.newsList = newsList;
        this.handler = handler;
        this.simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v,this.listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticia p = newsList.get(position);
        holder.textViewTitle.setText(p.getTitle());
        //holder.webViewDetail.loadData(p.getDetail(), "text/html", "UTF-8");
        holder.webViewDetail.loadDataWithBaseURL(null, p.getDetail(), "text/html", "UTF-8", null);
        holder.textViewDate.setText(simpleDateFormat.format(p.getDate()));
        holder.setPosition(position);
        if (p.getImg() == null) {
            ThreadConnection hc = new ThreadConnection(this.handler, p.getUrlImg(), true, position);
            hc.start();
            Glide.with(holder.itemView)
                    .load(R.drawable.loading_spinner)
                    .fitCenter()
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(p.getImg(), 0, p.getImg().length));
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
