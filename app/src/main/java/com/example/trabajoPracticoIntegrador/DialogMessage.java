package com.example.trabajoPracticoIntegrador;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Map;

public class DialogMessage extends DialogFragment {

    private final String title;

    private final CharSequence[] options;

    private final ClickListenerDialog listener;

    private AlertDialog.Builder builder = null;


    public DialogMessage(String title) {
        this.title = title;
        listener = new ClickListenerDialog();
        options = new CharSequence[]{
                "Lo ultimo",
                "Politica",
                "Mundo",
                "Sociedad",
                "Policiales",
                "Ciudades",
                "Opinion",
                "Cartas al pais",
                "Cultura",
                "Rural",
                "Economia",
                "Tecnologia",
                "Internacional",
                "Revista enie",
                "Viva",
                "Br",
                "Deportes",
                "Espectaculos de tv",
                "Espectaculos de cine",
                "Espectaculos de musica",
                "Espectaculos de teatro",
                "Espectaculos",
                "Autos",
                "Buena vida",
                "Viajes",
                "Arq"
        };

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (builder == null) {
            builder = new AlertDialog.Builder(this.getActivity());
        }

        builder.setTitle(this.title);
        builder.setPositiveButton("Guardar", listener);
        builder.setNegativeButton("Limpiar filtros", listener);

        boolean[] checked = new boolean[options.length];

        for (Map.Entry<String, ?> entry : MainActivity.sharedPreferences.getAll().entrySet()) {
            checked[listener.getPositionOfLink(entry.getKey())] = (Boolean) entry.getValue();
        }

        builder.setMultiChoiceItems(
                options,
                checked,
                listener
        );
        return builder.create();
    }
}
