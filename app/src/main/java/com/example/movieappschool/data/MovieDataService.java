package com.example.movieappschool.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.movieappschool.domain.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDataService {
    private final String BASE_URL;
    private final String API_KEY;
    private final String LANGUAGE;
    private final Context mContext;

    public MovieDataService(String apiKey, String language, Context context) {
        BASE_URL = "https://api.themoviedb.org/3";
        API_KEY = apiKey;
        LANGUAGE = language;
        mContext = context;
    }

    // Define an interface to handle the response for the subscribers.
    public interface VolleyResponseListener {
        void onResponse(Object response);
        void onError(String message);
    }

    public void getPopularMovies(VolleyResponseListener volleyResponseListener) {
        // Construct the url.
        String url = BASE_URL + "/movie/popular" + "?api_key=" + API_KEY + "&language=" + LANGUAGE;

        // Make a request to the constructed result and handle the result.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            List<Movie> popularMovies = new ArrayList<>();

            try {
                JSONArray results = response.getJSONArray("results");

                // Loop through the results.
                for (int i = 0; i < results.length(); i++) {
                    JSONObject resultItem = results.getJSONObject(i);

                    // Parse the JSON Object to a regular Java Object and add it to the popularMovies ArrayList.
                    popularMovies.add(MovieParse.jsonObjectToObject(resultItem));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Pass the result (popularMovies) on to the listener.
            volleyResponseListener.onResponse(popularMovies);
        }, error -> {
            // TODO: Handle error
            // Pass the error message on to the listener.
            volleyResponseListener.onError("Something went wrong...");
        });

        // Add the request to the queue using the DataServiceSingleton.
        DataServiceSingleton.getInstance(mContext).addToRequestQueue(request);
    }
}
