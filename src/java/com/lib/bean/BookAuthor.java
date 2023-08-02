package com.lib.bean;

import java.util.Objects;

public class BookAuthor {
    String isbn;
    int authId;

    public BookAuthor() {
    }

    public BookAuthor(String isbn, int authId) {
        this.isbn = isbn;
        this.authId = authId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.isbn);
        hash = 19 * hash + this.authId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookAuthor other = (BookAuthor) obj;
        if (this.authId != other.authId) {
            return false;
        }
        return Objects.equals(this.isbn, other.isbn);
    }
}
