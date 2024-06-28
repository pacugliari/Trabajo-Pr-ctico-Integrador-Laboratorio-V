package com.example.trabajoPracticoIntegrador;

import android.content.DialogInterface;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

public class ClickListenerDialog implements DialogInterface.OnClickListener, DialogInterface.OnMultiChoiceClickListener {

    public String[] links;

    private final SharedPreferences.Editor edit;

    public ClickListenerDialog() {

        edit = MainActivity.sharedPreferences.edit();

        links = new String[]{
                "https://www.clarin.com/rss/lo-ultimo/",
                "https://www.clarin.com/rss/politica/",
                "https://www.clarin.com/rss/mundo/",
                "https://www.clarin.com/rss/sociedad/",
                "https://www.clarin.com/rss/policiales/",
                "https://www.clarin.com/rss/ciudades/",
                "https://www.clarin.com/rss/opinion/",
                "https://www.clarin.com/rss/cartas_al_pais/",
                "https://www.clarin.com/rss/cultura/",
                "https://www.clarin.com/rss/rural/",
                "https://www.clarin.com/rss/economia/",
                "https://www.clarin.com/rss/tecnologia/",
                "https://www.clarin.com/rss/internacional/",
                "https://www.clarin.com/rss/revista-enie/",
                "https://www.clarin.com/rss/viva/",
                "https://www.clarin.com/rss/br/",
                "https://www.clarin.com/rss/deportes/",
                "https://www.clarin.com/rss/espectaculos/tv/",
                "https://www.clarin.com/rss/espectaculos/cine/",
                "https://www.clarin.com/rss/espectaculos/musica/",
                "https://www.clarin.com/rss/espectaculos/teatro/",
                "https://www.clarin.com/rss/espectaculos/",
                "https://www.clarin.com/rss/autos/",
                "https://www.clarin.com/rss/buena-vida/",
                "https://www.clarin.com/rss/viajes/",
                "https://www.clarin.com/rss/arq/"
        };

        if (MainActivity.sharedPreferences.getAll().size() < links.length) {
            this.resetFilters();
        }

    }


    public int getPositionOfLink(String link) {
        List<String> linksList = Arrays.asList(this.links);
        return linksList.indexOf(link);
    }

    private void resetFilters() {
        for (String link : links) {
            edit.putBoolean(link, false);
        }
        edit.commit();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_NEGATIVE) {
            this.resetFilters();
        }

        if (which == DialogInterface.BUTTON_NEGATIVE || which == DialogInterface.BUTTON_POSITIVE)
            MainActivity.loadNews();
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        edit.putBoolean(links[which], isChecked);
        edit.commit();
    }
}
