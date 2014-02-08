package com.hatenablog.shoma2da.android.topocket.oauth;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;

public class RequestTokenLoader extends AsyncTaskLoader<RequestToken> {

    public static final String TOKEN_URL = "https://getpocket.com/v3/oauth/request";

    public RequestTokenLoader(Context context) {
        super(context);
    }
    
    ConsumerKey createConsumerKey() {
        return new ConsumerKey();
    }

    @Override
    public RequestToken loadInBackground() {
        try {
            
            URL url = new URL(TOKEN_URL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            
            OutputStream outputStream = connection.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(String.format("consumer_key=%s&redirect_uri=http://yahoo.co.jp", createConsumerKey().getValidKey()));
            printWriter.flush();
            printWriter.close();
            
            if (connection.getResponseCode() != 200) {
                return null;
            }
            
            Scanner scanner = new Scanner(connection.getInputStream());
            String line = scanner.nextLine();
            scanner.close();
            
            String[] splited = line.split("=");
            return new RequestToken(splited[1]);
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
