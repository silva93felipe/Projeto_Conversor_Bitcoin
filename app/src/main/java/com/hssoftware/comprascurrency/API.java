package com.hssoftware.comprascurrency;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class API {

    public static JSONObject requestAPI(String urlString) throws IOException, JSONException {
        HttpsURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(60000);
        urlConnection.setConnectTimeout(60000);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while((line = br.readLine()) != null){
            sb.append(line + "\n");
        }

        br.close();

        String jsonString = sb.toString();
        return new JSONObject(jsonString);
    }
}
