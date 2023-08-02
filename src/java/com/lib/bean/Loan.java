package com.lib.bean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    
    private int loan_id;
    private int book_id;
    private int member_id;
    private Date dateIssued;
    private Date dateDeleted;
    private double amount;

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Loan() {
    }

    public Loan(int loan_id, int book_id, int member_id, Date dateIssued, Date dateDeleted, double amount) {
        this.loan_id = loan_id;
        this.book_id = book_id;
        this.member_id = member_id;
        this.dateIssued = dateIssued;
        this.dateDeleted = dateDeleted;
        this.amount = amount;
    }

    public Loan(int loan_id, int book_id, int member_id, Date dateIssued) {
        this.loan_id = loan_id;
        this.book_id = book_id;
        this.member_id = member_id;
        this.dateIssued = dateIssued;
    }

    public Loan(int member_id, int book_id) {
         this.book_id = book_id;
         this.member_id = member_id;    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.loan_id;
        hash = 97 * hash + this.book_id;
        hash = 97 * hash + this.member_id;
        hash = 97 * hash + Objects.hashCode(this.dateIssued);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }        if (getClass() != obj.getClass()) {
            return false;
        }
        final Loan other = (Loan) obj;
        if (this.loan_id != other.loan_id) {
            return false;
        }
        if (this.book_id != other.book_id) {
            return false;
        }
        if (this.member_id != other.member_id) {
            return false;
        }
        if (!Objects.equals(this.dateIssued, other.dateIssued)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
