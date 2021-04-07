package com.example.movieappschool.data;

import com.example.movieappschool.domain.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieParse {

    public static Movie jsonObjectToObject (JSONObject jsonMovieObject) throws JSONException {

        // Load all attributes and return a Java object.
        int id = jsonMovieObject.getInt("id");
        String title = jsonMovieObject.getString("title");
        String description = jsonMovieObject.getString("overview");
        String language = jsonMovieObject.getString("original_language");

        // Load genres and store in genreIds ArrayList.
        ArrayList<String> genres = new ArrayList<>();
        JSONArray jsonGenres = jsonMovieObject.getJSONArray("genres");
        for (int i = 0; i < jsonGenres.length(); i++) {
            String genre = jsonGenres.getJSONObject(i).getString("name");
            genres.add(genre);
        }

        String releaseDate = jsonMovieObject.getString("release_date");
        String posterPath = "https://image.tmdb.org/t/p/w500" + jsonMovieObject.getString("poster_path");
        double ratingAverage = jsonMovieObject.getDouble("vote_average");
        int ratingCount = jsonMovieObject.getInt("vote_count");
        int movieLength = jsonMovieObject.getInt("runtime");

        return new Movie(id, title, description, language, genres, releaseDate, posterPath, ratingAverage, ratingCount, movieLength);
    }
}
