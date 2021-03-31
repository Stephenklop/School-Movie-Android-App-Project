package com.example.movieappschool.domain;

import java.util.Date;

public class Show {

    private int mShowId, mHallId, mMovieId;
    private Date mDate;

    public Show(int mMovieId, Date mDate, int mShowId, int mHallId) {
        this.mMovieId = mMovieId;
        this.mDate = mDate;
        this.mShowId = mShowId;
        this.mHallId = mHallId;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public Date getDate() {
        return mDate;
    }

    public int getShowId() {
        return mShowId;
    }

    public int getHallId() {
        return mHallId;
    }
}
