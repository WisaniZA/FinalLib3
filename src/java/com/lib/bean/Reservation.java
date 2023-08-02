package com.lib.bean;

public class Reservation {
    private int reserveId;
    private int bookId;
    private int memberId;

    public Reservation() {
    }

    public Reservation(int reserveId, int bookId, int memberId) {
        this.reserveId = reserveId;
        this.bookId = bookId;
        this.memberId = memberId;
    }
    
    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
}
