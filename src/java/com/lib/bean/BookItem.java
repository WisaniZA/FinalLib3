package com.lib.bean;

public class BookItem {
    private int bookId;
    private String isbn;
    private boolean onLoan;

    public BookItem() {
    }

    public BookItem(int bookId, String isbn, boolean onLoan) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.onLoan = onLoan;
    }
    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }
  
}
