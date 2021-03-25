package com.example.movieappschool;

import android.os.AsyncTask;

import com.example.movieappschool.domain.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieAPITask extends AsyncTask<String, Void, List<Movie>> {

    private MainActivity mListener = null;

    public MovieAPITask(MainActivity listener) {
        mListener = listener;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        if (params.length == 0) return null;

        String API_URL = params[0];
        URL url = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(API_URL);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                // Initialize API JSON response
                String response = scanner.next();

                // Return the converted List of Movie objects
                return convertJsonToList(response);
            }

        } catch (IOException e) {
            // TODO: Handle error
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);

        // Send list of movies to listener
        mListener.setMovies(movies);
    }

    private List<Movie> convertJsonToList(String response) {
        ArrayList<Movie> resultList = new ArrayList<>();

        try {
            // Initialize JSON object from API response
            JSONObject responseObject = new JSONObject(response);

            // Initialize JSONArray movies from API response
            JSONArray movies = responseObject.getJSONArray("results");

            // Loop through JSON array of movies
            for (int i = 0; i < movies.length(); i++) {
                // Initialize movie item
                JSONObject movieItem = movies.getJSONObject(i);

                // Initialize and load Movie object attributes
                int id = movieItem.getInt("id");

                String title = movieItem.getString("title");

                String description = movieItem.getString("overview");

                String language = movieItem.getString("original_language");

                // Load genres and store in genreIds ArrayList
                ArrayList<Integer> genreIds = new ArrayList<>();
                JSONArray jsonGenres = movieItem.getJSONArray("genre_ids");
                if (jsonGenres != null) {
                    for (int j = 0; j < jsonGenres.length(); j++) {
                        genreIds.add((Integer) jsonGenres.get(j));
                    }
                }

                String releaseDate = movieItem.getString("release_date");

                String posterPath = movieItem.getString("poster_path");

                double ratingAverage = movieItem.getDouble("vote_average");

                int ratingCount = movieItem.getInt("vote_count");

                // Create Movie object and add to resultList array
                resultList.add(new Movie(id, title, description, language, genreIds, releaseDate, posterPath, ratingAverage, ratingCount));
            }
        } catch (JSONException e) {
            // TODO: Handle error
            e.printStackTrace();
        }

        // Return the resultList with Movie objects
        return resultList;
    }

    // Define interface for the Movie listener
    public interface MovieListener {
        void setMovies(List<Movie> movies);
    }
}