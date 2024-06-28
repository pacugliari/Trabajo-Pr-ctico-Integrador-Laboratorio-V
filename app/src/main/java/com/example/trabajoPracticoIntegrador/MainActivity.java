package com.example.trabajoPracticoIntegrador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trabajoPracticoIntegrador.models.Noticia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements Handler.Callback, SearchView.OnQueryTextListener,OnItemClick {

    private static final List<Noticia> noticias = new ArrayList<Noticia>();
    private static Adapter adapter = null;
    public static String COLOR_TEXT = null;
    @SuppressLint("StaticFieldLeak")
    private static TextView txtEmpty;

    @SuppressLint("StaticFieldLeak")
    private static ImageView imageViewLoading;

    private static Handler colaMensaje;

    public static SharedPreferences sharedPreferences;

    private DialogMessage dialogMessage;

    private static Map<String, List<Noticia>> noticiasCargadas;

    private static final List<Noticia> newsCopy = new ArrayList<Noticia>();

    private static RecyclerView recyclerView;

    private void initApp() {
        txtEmpty = findViewById(R.id.textViewNoResults);
        colaMensaje = new Handler(this);
        sharedPreferences = super.getSharedPreferences("fssFilters", Context.MODE_PRIVATE);
        dialogMessage = new DialogMessage("Filtros");
        noticiasCargadas = new HashMap<String, List<Noticia>>();
        imageViewLoading = findViewById(R.id.imageViewLoading);
        recyclerView = findViewById(R.id.list);

        getTextColorBasedOnTheme();

        Glide.with(this)
                .load(R.drawable.loading_spinner)
                .into(imageViewLoading);



        //RecyclerView
        RecyclerView rv = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new Adapter(noticias, colaMensaje,this);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_button) {
            dialogMessage.show(getSupportFragmentManager(), "main");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mi = menu.findItem(R.id.search_bar);
        SearchView sv = (SearchView) mi.getActionView();
        assert sv != null;
        sv.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        this.initApp();
        MainActivity.loadNews();

    }

    public static void showSpinner(Boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            imageViewLoading.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            imageViewLoading.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public static boolean loadNews() {
        noticias.clear();
        showEmpty(false);
        boolean hayNoticias = false;
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                List<Noticia> newsList = noticiasCargadas.get(entry.getKey());
                if (newsList == null) {
                    ThreadConnection hc = new ThreadConnection(colaMensaje, entry.getKey(), false);
                    hc.start();
                    showSpinner(true);
                    Log.d("cargarNoticias", "HILO");
                } else {
                    Log.d("cargarNoticias", "YA CARGADO");
                    MainActivity.filterAndSortNews(newsList);
                }
                hayNoticias = true;
            }
        }
        if (adapter != null) adapter.notifyDataSetChanged();
        if(!hayNoticias){
            showEmpty(true);
            showSpinner(false);
        }
        return hayNoticias;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.arg1 == ThreadConnection.IMG) {
            MainActivity.noticias.get(msg.arg2).setImg((byte[]) msg.obj);
            adapter.notifyItemChanged(msg.arg2);
        } else {
            ThreadResponse responseObj = (ThreadResponse) msg.obj;
            noticiasCargadas.put(responseObj.url, responseObj.newsList);
            MainActivity.filterAndSortNews(responseObj.newsList);
            adapter.notifyDataSetChanged();
        }

        showSpinner(false);
        showEmpty(noticias.isEmpty());

        return false;
    }

    private static void filterAndSortNews(List<Noticia> noticias) {

        Set<Noticia> unifiedNews = new LinkedHashSet<>(noticias);
        unifiedNews.addAll(MainActivity.noticias);
        MainActivity.noticias.clear();
        MainActivity.newsCopy.clear();
        MainActivity.noticias.addAll(new ArrayList<>(unifiedNews));
        MainActivity.newsCopy.addAll(new ArrayList<>(unifiedNews));
        Collections.sort(MainActivity.noticias);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public static void showEmpty(Boolean show) {
        if (show) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onQueryTextChange(String newText) {
        noticias.clear();

        if (newText.length() >= 3) {
            List<Noticia> leakedNews = newsCopy.stream()
                    .filter(noticia -> noticia.getTitle().toLowerCase().contains(newText.toLowerCase()))
                    .collect(Collectors.toList());
            noticias.addAll(leakedNews);
        } else {
            noticias.addAll(MainActivity.newsCopy);
        }

        showEmpty(noticias.isEmpty());

        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Noticia noticia = MainActivity.noticias.get(position);
        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra(NewActivity.NEW,noticia);
        super.startActivity(intent);
    }

    private void getTextColorBasedOnTheme() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            COLOR_TEXT = "#FFFFFF"; // Modo oscuro: texto blanco
        } else {
            COLOR_TEXT =  "#000000"; // Modo claro: texto negro
        }
    }
}

/*
WebView wv = findViewById(R.id.wv);
        wv.setWebViewClient(new WebViewClient());
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
       // ws.setSupportMultipleWindows(true);
     //   ws.setJavaScriptCanOpenWindowsAutomatically(true);
   //   ws.setLoadsImagesAutomatically(true);
        ws.setDomStorageEnabled(true);
 wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        ws.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//ws.setLoadWithOverviewMode(true);
  ws.setUseWideViewPort(true);
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);



wv.loadUrl("https://www.clarin.com/");

*/