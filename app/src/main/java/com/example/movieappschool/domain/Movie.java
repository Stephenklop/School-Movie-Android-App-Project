package com.example.movieappschool.domain;

import java.util.Date;
import java.util.List;

public class Movie {
    private int mId, mRatingCount, mLength;
    private String mTitle, mDescription, mLanguage, mReleaseDate, mPosterURL;
    private List<String> mGenreIds;
    private double mRatingAverage;

    public Movie(int id, String title, String description, String language, List<String> genreIds, String releaseDate, String posterURL, double ratingAverage, int ratingCount, int movieLength) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mLanguage = language;
        mGenreIds = genreIds;
        mReleaseDate = releaseDate;
        mPosterURL = posterURL;
        mRatingAverage = ratingAverage;
        mRatingCount = ratingCount;
        mLength = movieLength;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public List<String> getGenreIds() {
        return mGenreIds;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPosterURL() {
        return mPosterURL;
    }

    public double getRatingAverage() {
        return mRatingAverage;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public int getMovieLength() { return mLength; };

    @Override
    public String toString() {
        return "Movie{" +
                "mId=" + mId +
                ", mRatingCount=" + mRatingCount +
                ", mLength=" + mLength +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mLanguage='" + mLanguage + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mPosterURL='" + mPosterURL + '\'' +
                ", mGenreIds=" + mGenreIds +
                ", mRatingAverage=" + mRatingAverage +
                '}';
    }
}
