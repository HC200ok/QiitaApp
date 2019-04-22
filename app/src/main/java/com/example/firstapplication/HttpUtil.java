package com.example.firstapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
    public static String sendUrl(String sendUrl) {
        try {
            URL url = new URL(sendUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

//            读入流
            InputStream stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String str = null;

            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendUrl;
    }

    public static Bitmap loadImage(String sendUrl) {
        try {
            URL url = new URL(sendUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

//            读入流
            InputStream stream = conn.getInputStream();

            String fileName = String.valueOf(System.currentTimeMillis());
            FileOutputStream outputStream = null;
            File fileDownload = null;

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File parent = Environment.getExternalStorageDirectory();
                fileDownload = new File(parent, fileName);
                outputStream = new FileOutputStream(fileDownload);
            }

            byte[] bytes = new byte[2*1024];
            int lens;

            if (outputStream != null) {
                while ((lens = stream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, lens);
                }
                return BitmapFactory.decodeFile(fileDownload.getAbsolutePath());
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
