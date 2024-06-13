package com.example.clase4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final List<Noticia> lista;

    private Handler handler;

    public MyAdapter(List<Noticia> lista, Handler handler) {

        this.lista = lista;
        this.handler = handler;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Noticia p = lista.get(position);
        holder.txtTitulo.setText(p.getTitulo());
        holder.txtDetalle.setText(p.getDetalle());
        SimpleDateFormat sdfFormateado = new SimpleDateFormat("dd/MM/yyyy");
        holder.txtFecha.setText(sdfFormateado.format(p.getFecha()));
        //holder.setPosition(position);
        if (p.getImg() == null) {
            HiloConexion hc = new HiloConexion(this.handler, p.getUrlImg(), true, position);
            hc.start();
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(p.getImg(), 0, p.getImg().length));
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
