package com.example.clase4;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener

    TextView txtTitulo;
    TextView txtDetalle;

    TextView txtFecha;

    ImageView imageView;

    //private int position;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
        txtDetalle = (TextView) itemView.findViewById(R.id.txtDetalle);
        txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    /*@Override
    public void onClick(View v) {
        listener.onItemClick(position);
    }*/

    /*public void setPosition(int position)
    {
        this.position = position;
    }*/
}
