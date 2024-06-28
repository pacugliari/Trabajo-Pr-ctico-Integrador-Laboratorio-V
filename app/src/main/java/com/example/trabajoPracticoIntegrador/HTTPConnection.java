package com.example.trabajoPracticoIntegrador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPConnection {

    public byte[] getInfo(String urlString) {

        try {
            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int response = urlConnection.getResponseCode();
            if (response == 200) {
                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];

                int byteLeido = 0;

                while ((byteLeido = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, byteLeido);
                }

                is.close();
                return baos.toByteArray();

            }

        } catch (IOException e) {
            //throw new RuntimeException(e);
        }


        return null;
    }

}
