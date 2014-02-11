package com.hatenablog.shoma2da.android.topocket.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;

public class AddRequestManager {
    
    public static final String ADD_URL = "https://getpocket.com/v3/add";
    
    private ConsumerKey mConsumerKey;
    private AccessToken mAccessToken;
    
    public AddRequestManager(ConsumerKey consumerKey, AccessToken accessToken) {
        mConsumerKey = consumerKey;
        mAccessToken = accessToken;
    }
    
    public void request(final String urlStr) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(ADD_URL);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    
                    OutputStream outputStream = connection.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    printWriter.write(String.format("url=%s&consumer_key=%s&access_token=%s", urlStr, mConsumerKey.getValidKey(), mAccessToken.getmValidToken()));
                    printWriter.flush();
                    printWriter.close();
                    
                    Log.d("toPocket", "response code is " + connection.getResponseCode());
                    
                    InputStream inputStream = connection.getInputStream();
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    
}
