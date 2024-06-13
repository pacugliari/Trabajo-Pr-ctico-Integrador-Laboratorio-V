package com.example.clase4;

import java.util.Arrays;
import java.util.Date;

public class Noticia implements Comparable<Noticia> {
    private String titulo;
    private String detalle;
    private Date fecha;
    private String urlImg;
    private byte[] img;
    private String link;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public byte[] getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "titulo='" + titulo + '\'' +
                ", detalle='" + detalle + '\'' +
                ", fecha=" + fecha +
                ", urlImg='" + urlImg + '\'' +
                ", img=" + Arrays.toString(img) +
                ", link='" + link + '\'' +
                '}';
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int compareTo(Noticia noticia) {
        return noticia.getFecha().compareTo(this.getFecha());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noticia noticia = (Noticia) o;
        return titulo.equals(noticia.titulo);
    }

    @Override
    public int hashCode() {
        return titulo.hashCode();
    }
}
