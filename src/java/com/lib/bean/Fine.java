package com.lib.bean;

public class Fine {
    private int fineId;
    private double fineAmount;
    private int loanId;
    private int priceId;

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public Fine(int fineId, double fineAmount, int loanId, int priceId) {
        this.fineId = fineId;
        this.fineAmount = fineAmount;
        this.loanId = loanId;
        this.priceId = priceId;
    }
    
    public Fine() {
    }

    public int getFineId() {
        return fineId;
    }

    public void setFineId(int fineId) {
        this.fineId = fineId;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.fineId;
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.fineAmount) ^ (Double.doubleToLongBits(this.fineAmount) >>> 32));
        hash = 73 * hash + this.loanId;
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
        final Fine other = (Fine) obj;
        if (this.fineId != other.fineId) {
            return false;
        }
        if (Double.doubleToLongBits(this.fineAmount) != Double.doubleToLongBits(other.fineAmount)) {
            return false;
        }
        return this.loanId == other.loanId;
    }
    
    
}
