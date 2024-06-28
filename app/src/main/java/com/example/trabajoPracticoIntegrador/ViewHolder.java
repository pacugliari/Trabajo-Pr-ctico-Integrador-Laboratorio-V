package com.example.trabajoPracticoIntegrador;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnTouchListener {

    public TextView textViewTitle;
    public WebView webViewDetail;
    public TextView textViewDate;
    public CardView cardView;
    public ImageView imageView;
    private int position;

    public View itemView;

    private final OnItemClick listener;

    @SuppressLint("ClickableViewAccessibility")
    public ViewHolder(@NonNull View itemView, OnItemClick listener) {
        super(itemView);
        this.itemView = itemView;
        textViewTitle = (TextView) itemView.findViewById(R.id.txtTitulo);
        webViewDetail = (WebView) itemView.findViewById(R.id.txtDetalle);
        textViewDate = (TextView) itemView.findViewById(R.id.txtFecha);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        webViewDetail.setWebViewClient(new WebViewClient());
        webViewDetail.setOnTouchListener(this);
        webViewDetail.setBackgroundColor(Color.TRANSPARENT);
        //WebSettings ws = webViewDetail.getSettings();
        //ws.setJavaScriptEnabled(true);

        cardView = itemView.findViewById(R.id.cardView);
        cardView.setOnClickListener(this);

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(position);
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    private float startX;
    private float startY;
    private static final int CLICK_DISTANCE_THRESHOLD = 10;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();

                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceX < CLICK_DISTANCE_THRESHOLD && distanceY < CLICK_DISTANCE_THRESHOLD) {
                    listener.onItemClick(position);
                    return true;
                }
                break;
        }
        return false;
    }
}
