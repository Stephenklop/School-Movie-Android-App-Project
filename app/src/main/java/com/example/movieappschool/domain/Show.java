package com.example.movieappschool.domain;

public class Show {

    private int mShowId, mHallId, mMovieId;
    private String mFullDate;

    public Show(int mMovieId, String mFullDate, int mShowId, int mHallId) {
        this.mMovieId = mMovieId;
        this.mFullDate = mFullDate;
        this.mShowId = mShowId;
        this.mHallId = mHallId;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public String getFullDate() {
        return mFullDate;
    }

    public String getDate() {
        String dateTime[] = mFullDate.split(" ");
        return  dateTime[0];
    }

    public String getTime() {
        String dateTime[] = mFullDate.split(" ");
        String fullTime[] = dateTime[1].split(":");
        //2021-04-10 16:30:00.0
        return fullTime[0] + ":" + fullTime[1];
    }

    public int getShowId() {
        return mShowId;
    }

    public int getHallId() {
        return mHallId;
    }
}
