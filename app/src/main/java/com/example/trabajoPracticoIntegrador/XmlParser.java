package com.example.trabajoPracticoIntegrador;

import android.util.Xml;

import com.example.trabajoPracticoIntegrador.models.Noticia;

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
                    n.setTitle(texto);
                } else if (tag.equals("description") && n != null) {
                    String texto = xmlPullParser.nextText();
                    String htmlText = "<html><head><style>body { color: "+MainActivity.COLOR_TEXT+"; }</style></head><body>"
                            + texto
                            + "</body></html>";
                    n.setDetail(htmlText);
                } else if (tag.equals("link") && n != null) {
                    String texto = xmlPullParser.nextText();
                    n.setLink(texto);
                } else if (tag.equals("enclosure") && n != null) {
                    String url = xmlPullParser.getAttributeValue(null, "url");
                    n.setUrlImg(url);
                } else if (tag.equals("pubDate") && n != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    String fechaTexto = xmlPullParser.nextText();
                    n.setDate(sdf.parse(fechaTexto));
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
