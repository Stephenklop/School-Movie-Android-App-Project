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
        ArrayList<Integer> genreIds = new ArrayList<>();
        JSONArray jsonGenres = jsonMovieObject.getJSONArray("genre_ids");
        for (int j = 0; j < jsonGenres.length(); j++) {
            genreIds.add((Integer) jsonGenres.get(j));
        }

        String releaseDate = jsonMovieObject.getString("release_date");
        String posterPath = jsonMovieObject.getString("poster_path");
        double ratingAverage = jsonMovieObject.getDouble("vote_average");
        int ratingCount = jsonMovieObject.getInt("vote_count");

        return new Movie(id, title, description, language, genreIds, releaseDate, posterPath, ratingAverage, ratingCount);
    }
}
