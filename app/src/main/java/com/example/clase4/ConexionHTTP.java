package com.example.clase4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionHTTP {
    //ARRAY DE BYTE PUDE SER CUALQUIER COSA
    public byte[] getInfo(String urlString) {
        //urlString = "https://www.clarin.com/rss/tecnologia";
        //urlString = "https://www.clarin.com/img/2024/05/23/UlNcYFM5k_1200x630__1.jpg";
        try {
            URL url = new URL(urlString); //CHEQUEA EL URL ESTE BIEN FORMADO

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect(); //EJECUTA EL CONSUMO DEL ENPOINT
            int response = urlConnection.getResponseCode();
            if (response == 200) {
                InputStream is = urlConnection.getInputStream();//INFORMACION EN DIRECTO
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];

                int byteLeido = 0;

                while ((byteLeido = is.read(buffer)) != -1) { //IGUAL A -1 TERMINO DE LEER TODO
                    baos.write(buffer, 0, byteLeido);
                }

                is.close();
                return baos.toByteArray();

            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) { //POR ERROR DE CONEXION,O FALTA DE PERMISOS
            throw new RuntimeException(e);
        }


        return null;
    }

}
