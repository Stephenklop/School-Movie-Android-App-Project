package com.example.movieappschool.domain;

public class Ticket {

    private int mSeatNumber, mUserId, mTicketId, mShowId;
    private double mPrice;

    public Ticket(int mTicketId, int mUserId, int mSeatNumber, int mShowId, double mPrice) {
        this.mTicketId = mTicketId;
        this.mUserId = mUserId;
        this.mSeatNumber = mSeatNumber;
        this.mShowId = mShowId;
        this.mPrice = mPrice;
    }

    public int getSeatNumber() {
        return mSeatNumber;
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
