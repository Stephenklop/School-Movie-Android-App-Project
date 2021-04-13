package com.example.movieappschool.data;

import android.util.Log;

import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.logic.Language;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieAPIService {
    private final String API_KEY;
    private final String LANGUAGE;
    HttpURLConnection connection;

    public MovieAPIService(String apiKey, Language language) {
        API_KEY = apiKey;
        LANGUAGE = language.getLanguage();
    }

    private void connect(String url) throws IOException {
        URL connectionUrl = new URL(url);

        connection = (HttpURLConnection) connectionUrl.openConnection();
    }

    private void disconnect() {
        connection.disconnect();
    }

    public List<Movie> getMoviesByIds(List<Integer> ids) {
        List<Movie> result = new ArrayList<>();

        for (int id : ids) {
            try {
                connect("https://api.themoviedb.org/3/movie/" + id + "?api_key=" + API_KEY + "&language=" + LANGUAGE);
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject(inputLine);

                    result.add(MovieParse.jsonObjectToObject(jsonObject));
                }
            } catch (IOException | JSONException e) {
                // Handle error
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }

        return result;
    }
}
