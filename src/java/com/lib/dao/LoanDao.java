package com.lib.dao;

import com.lib.bean.Loan;
import java.util.List;

public interface LoanDao {
    boolean removeLoan(int loan_id);
     boolean isBookAvailable(int book_id);
  //  boolean updateLoan(Loan loan);
    boolean addLoan(Loan loan);
    Loan getLoan(int book_id,int member_id );
    List<Loan> getLoans(int book_id);
    boolean setLoanState(int loan_id, boolean deleted);
}
