
package com.lib.service;

import com.lib.bean.Loan;
import java.util.List;

public interface LoanService {
    boolean removeLoan(int loanId);
     boolean isBookAvailable(int book_id);
  //  boolean updateLoan(Loan loan);
    boolean addLoan(Loan loan);
    Loan getLoan(int book_id,int member_id);
    List<Loan> getLoans(int book_id);
    boolean setLoanState(int loanId,  boolean deleted );
}
