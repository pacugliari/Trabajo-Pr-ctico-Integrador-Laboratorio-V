package com.example.trabajoPracticoIntegrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabajoPracticoIntegrador.models.Noticia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewActivity extends AppCompatActivity {

    public final static String NEW = "noticia";

    private WebView webView;
    @SuppressLint({"SetJavaScriptEnabled", "IntentReset"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new);

        ActionBar actionBar = super.getSupportActionBar();

        if(actionBar != null){
            //actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = super.getIntent().getExtras();
        Noticia noticia = (Noticia) extras.get(NEW);
        Log.d("NOTICIA",noticia.toString());

        webView = (WebView) this.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        webView.loadUrl(noticia.getLink());

        FloatingActionButton fabShare = findViewById(R.id.fabShare);
        // Manejar el clic del botón flotante
        fabShare.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);

            sharingIntent.setData(this.getUriImage(noticia.getImg()));
            sharingIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharingIntent.putExtra(Intent.EXTRA_TITLE, noticia.getTitle());
            sharingIntent.putExtra(Intent.EXTRA_TEXT, noticia.getLink());
            startActivity(Intent.createChooser(sharingIntent, "Compartir vía"));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            super.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public File saveImageToCache(byte[] imageBytes) throws IOException {
        File cachePath = new File(getExternalCacheDir(), "shared_image.png");
        FileOutputStream fos = new FileOutputStream(cachePath);
        fos.write(imageBytes);
        fos.close();
        return cachePath;
    }
    public Uri getUriImage (byte[] imageBytes) {
        try {
            File imageFile = saveImageToCache(imageBytes);
            Uri imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);
            return imageUri;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}