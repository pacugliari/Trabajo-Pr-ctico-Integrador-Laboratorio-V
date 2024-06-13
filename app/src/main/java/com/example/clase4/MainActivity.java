package com.example.clase4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements Handler.Callback, SearchView.OnQueryTextListener {

    MyAdapter adapter = null;

    TextView txtEmpty;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mi = menu.findItem(R.id.search_bar);
        SearchView sv = (SearchView) mi.getActionView();
        sv.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    static List<Noticia> noticias = new ArrayList<Noticia>();
    static List<Noticia> noticiasCopia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtEmpty = findViewById(R.id.textViewNoResults);
        Handler colaMensaje = new Handler(this);

        String[] links = {
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

        for (String link : links) {
            HiloConexion hc = new HiloConexion(colaMensaje, link, false);//SE HACE EN UN HILO A PARTE PORQUE SINO CONGELA EL HILO PRINCIPAL Y CRASHEA LA APP
            hc.start();
        }

        DialogMessage dialogMessage = new DialogMessage("titulo","constructor");
        dialogMessage.show(getSupportFragmentManager(),"main");

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapter = new MyAdapter(noticias, colaMensaje);
        list.setAdapter(adapter);

        //HiloConexion hc2 = new HiloConexion(colaMensaje,"https://www.clarin.com/img/2024/05/23/UlNcYFM5k_1200x630__1.jpg",true);//SE HACE EN UN HILO A PARTE PORQUE SINO CONGELA EL HILO PRINCIPAL Y CRASHEA LA APP
        //hc2.start();

        //LA CONEXCION ENTRE 2 HILOS SE HACE CON UNA COLA DE MENSAJERIA Y SE LLAMA HANDLER
        //YA QUE DESDE EL HILO SECUNDARIA NO PUEDO RENDERIZAR NADA EN LA VISTA, SOLO LO PUEDE HACER
        //EL HILO DEL MAIN
        SharedPreferences sp = super.getSharedPreferences("rssConfig", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name","CAMBIADO");
        edit.commit();

        Log.d("SP",sp.getString("name","defaultName"));

    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.arg1 == HiloConexion.IMG) {
            MainActivity.noticias.get(msg.arg2).setImg((byte[]) msg.obj);
            adapter.notifyItemChanged(msg.arg2);
            //ImageView iv = super.findViewById(R.id.img);
            //byte[] imagen = (byte[]) msg.obj;
            //iv.setImageBitmap(BitmapFactory.decodeByteArray(imagen,0,imagen.length));
        } else {
            Set<Noticia> noticiasUnicas = new LinkedHashSet<>((List<Noticia>) msg.obj);
            noticiasUnicas.addAll(noticias);
            noticias.clear();
            noticias.addAll(new ArrayList<>(noticiasUnicas));
            Collections.sort(MainActivity.noticias);
            adapter.notifyDataSetChanged();
        }

        return false;
    }

    //REALIZAR EL FILTRADO DE LAS NOTICIAS,DEBE FILTRAR A PARTIR DEL 3 CARACTER, AL LIMPIAR EL SEARCH DEBE VOLVER LA LISTA ENTERA
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit", "onQueryTextSubmit");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (noticiasCopia == null) {
            noticiasCopia = new ArrayList<>(noticias);
        }
        noticias.clear();

        if (newText.length() >= 3) {
            List<Noticia> filtradas = noticiasCopia.stream()
                    .filter(noticia -> noticia.getTitulo().toLowerCase().contains(newText.toLowerCase()))
                    .collect(Collectors.toList());
            noticias.addAll(filtradas);
        } else {
            noticias.addAll(noticiasCopia);
        }

        if (noticias.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.GONE);
        }


        adapter.notifyDataSetChanged();
        return true;
    }
}