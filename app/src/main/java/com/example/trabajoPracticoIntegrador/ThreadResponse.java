package com.example.trabajoPracticoIntegrador;

import com.example.trabajoPracticoIntegrador.models.Noticia;

import java.util.List;

public class ThreadResponse {
    public List<Noticia> newsList;
    public String url;

    public ThreadResponse(List<Noticia> newsList, String url) {
        this.newsList = newsList;
        this.url = url;
    }


}
