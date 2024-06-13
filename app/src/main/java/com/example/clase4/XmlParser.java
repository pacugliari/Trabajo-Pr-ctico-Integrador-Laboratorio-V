package com.example.clase4;

import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class XmlParser {

    public static List<Noticia> parserJsonNoticias(String jsonNoticias) throws JSONException {
        String not = "[{'titulo':'TEXTO','edad':12},{'titulo':'TEXTO2'}]";

        JSONArray aNoticias = new JSONArray(jsonNoticias);
        for (int i = 0; i < aNoticias.length(); i++) {
            JSONObject n1 = aNoticias.getJSONObject(i);
            String titulo = n1.getString("titulo");
            int edad = n1.getInt("edad");
            int edad2 = n1.optInt("edad");
            int edad3 = n1.optInt("edad", -1);
        }

        return null;
    }

    public static List<Noticia> parserNoticias(String xmlNoticias) throws XmlPullParserException, IOException, ParseException {
        List<Noticia> noticias = new ArrayList<>();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(new StringReader(xmlNoticias));
        int event = xmlPullParser.getEventType();
        Noticia n = null;
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.START_TAG) {
                String tag = xmlPullParser.getName();
                if (tag.equals("item")) {
                    n = new Noticia();
                } else if (tag.equals("title") && n != null) {
                    String texto = xmlPullParser.nextText();
                    n.setTitulo(texto);
                } else if (tag.equals("description") && n != null) {
                    /*String texto = xmlPullParser.nextText();
                    n.setDetalle(texto);*/
                    String texto = xmlPullParser.nextText();
                    texto = texto.replace("<ul><li>", "");
                    texto = texto.replace("</li></ul>", "");
                    texto = texto.replace("</li><li>", "");
                    n.setDetalle(texto);
                } else if (tag.equals("link") && n != null) {
                    String texto = xmlPullParser.nextText();
                    n.setLink(texto);
                } else if (tag.equals("enclosure") && n != null) {
                    String url = xmlPullParser.getAttributeValue(null, "url");
                    n.setUrlImg(url);
                } else if (tag.equals("pubDate") && n != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    String fechaTexto = xmlPullParser.nextText();
                    n.setFecha(sdf.parse(fechaTexto));
                }
            } else if (event == XmlPullParser.END_TAG) {
                String tag = xmlPullParser.getName();
                if (tag.equals("item")) {
                    noticias.add(n);
                    n = null;
                }
            }
            event = xmlPullParser.next();
        }
        return noticias;
    }
}
