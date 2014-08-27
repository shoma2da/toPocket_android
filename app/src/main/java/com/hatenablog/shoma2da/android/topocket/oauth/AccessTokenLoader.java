package com.hatenablog.shoma2da.android.topocket.oauth;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;

public class AccessTokenLoader extends AsyncTaskLoader<AccessToken> {
    
    public static final String TOKEN_URL = "https://getpocket.com/v3/oauth/authorize";
    
    private ConsumerKey mConsumerKey;
    private RequestToken mRequestToken;
    
    public AccessTokenLoader(Context context, ConsumerKey consumerKey, RequestToken requestToken) {
        super(context);
        mConsumerKey = consumerKey;
        mRequestToken = requestToken;
    }

    @Override
    public AccessToken loadInBackground() {
        try {
            URL url = new URL(TOKEN_URL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            
            OutputStream outputStream = connection.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(String.format("consumer_key=%s&code=%s", mConsumerKey.getValidKey(), mRequestToken.getValidToken()));
            printWriter.flush();
            printWriter.close();
            
            if (connection.getResponseCode() != 200) {
                return null;
            }
            
            Scanner scanner = new Scanner(connection.getInputStream());
            String line = scanner.nextLine();
            scanner.close();
            
            //レスポンス文字列をマッピング
            HashMap<String, String> map = new HashMap<String, String>();
            for (String keyValue : line.split("&")) {
                String[] keyValueArray = keyValue.split("=");
                map.put(keyValueArray[0], keyValueArray[1]);
            }
            
            return new AccessToken(map.get("access_token"), map.get("username"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
