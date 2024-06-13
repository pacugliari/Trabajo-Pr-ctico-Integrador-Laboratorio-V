package com.example.clase4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogMessage extends DialogFragment {

    String mensaje;
    String titulo;

    public DialogMessage(String titulo, String mensaje){
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(this.titulo);
        //builder.setMessage(this.mensaje);
        ClickListenerDialog listener = new ClickListenerDialog();
        builder.setPositiveButton("OK",listener);
        builder.setNegativeButton("NEGATIVE",listener);
        builder.setNeutralButton("INFO",listener);
        //builder.setItems(new String[]{"item1", "item2","item3"},listener);
        builder.setView(R.layout.item_layout);
        //builder.setMultiChoiceItems((CharSequence[]) new String[]{"item1", "item2","item3"},new boolean[]{false,false,false}, null);
        return builder.create();
    }
}
