package com.example.movieappschool.domain;

public class Ticket {

    private int mSeatNumber, mRowNumber, mUserId, mTicketId;
    private String mTicketType;
    private double mPrice;
    private Show mShow;

    public Ticket(int mTicketId, int mUserId, int mSeatNumber, int mRowNumber, String ticketType, double mPrice) {
        this.mTicketId = mTicketId;
        this.mUserId = mUserId;
        this.mSeatNumber = mSeatNumber;
        this.mRowNumber = mRowNumber;
        this.mTicketType = ticketType;
        this.mPrice = mPrice;
    }

    public int getSeatNumber() {
        return mSeatNumber;
    }

    public int getRowNumber() {
        return mRowNumber;
    }

    public int getUserId() {
        return mUserId;
    }

    public String getTicketType() {
        return mTicketType;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getTicketId() {
        return mTicketId;
    }

    public Show getShow() {
        return mShow;
    }

    public void setShow(Show show) {
        this.mShow = show;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "mSeatNumber=" + mSeatNumber +
                ", mRowNumber=" + mRowNumber +
                ", mUserId=" + mUserId +
                ", mTicketId=" + mTicketId +
                ", mPrice=" + mPrice +
                ", mShow=" + mShow +
                '}';
    }
}