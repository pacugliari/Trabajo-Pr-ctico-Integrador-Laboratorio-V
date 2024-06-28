package com.example.trabajoPracticoIntegrador.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Noticia implements Comparable<Noticia>, Serializable {
    private String title;
    private String detail;
    private Date date;
    private String urlImg;
    private byte[] img;
    private String link;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
                "titulo='" + title + '\'' +
                ", detalle='" + detail + '\'' +
                ", fecha=" + date +
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
        return noticia.getDate().compareTo(this.getDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noticia noticia = (Noticia) o;
        return title.equals(noticia.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
