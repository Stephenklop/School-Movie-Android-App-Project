package com.example.movieappschool.domain;

public class Seat {

    private int mSeatNumber, mRowNumber;

    public Seat(int mSeatNumber, int mRowNumber) {
        this.mSeatNumber = mSeatNumber;
        this.mRowNumber = mRowNumber;
    }

    public int getSeatNumber() {
        return mSeatNumber;
    }

    public int getRowNumber() {
        return mRowNumber;
    }


}
