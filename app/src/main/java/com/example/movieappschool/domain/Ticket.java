package com.example.movieappschool.domain;

public class Ticket {

    private int mSeatNumber, mRowNumber, mUserId, mTicketId, mShowId;
    private double mPrice;

    public Ticket(int mTicketId, int mUserId, int mSeatNumber, int mRowNumber, int mShowId, double mPrice) {
        this.mTicketId = mTicketId;
        this.mUserId = mUserId;
        this.mSeatNumber = mSeatNumber;
        this.mRowNumber = mRowNumber;
        this.mShowId = mShowId;
        this.mPrice = mPrice;
    }

    public int getSeatNumber() {
        return mSeatNumber;
    }

    public int getmRowNumber() {
        return mRowNumber;
    }

    public int getUserId() {
        return mUserId;
    }

    public int getShow() {
        return mShowId;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getTicketId() {
        return mTicketId;
    }
}
