package com.lib.service.impl;

import com.lib.bean.Loan;
import com.lib.dao.LoanDao;
import com.lib.dao.impl.LoanDaoImpl;
import com.lib.service.LoanService;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class LoanServiceImpl implements LoanService {

    private LoanDao loanDao;

    public LoanServiceImpl(BasicDataSource bds) {
        loanDao = new LoanDaoImpl(bds);
    }

    @Override
    public boolean addLoan(Loan loan) {
        if (loan == null) {
            return false;
        }
        return loanDao.addLoan(loan);
    }

    @Override
    public boolean isBookAvailable(int book_id) {
        return loanDao.isBookAvailable(book_id);
    }

    @Override
    public boolean removeLoan(int loan_id) {
        return loan_id < 0 ? false : loanDao.removeLoan(loan_id);
    }

//    @Override
//    public boolean updateLoan(Loan loan) {
//       return loan.getBook_id()>5 ? false : loanDao.updateLoan(loan);
//    }
    @Override
    public Loan getLoan(int book_id, int member_id) {
        return book_id < 0 || member_id < 0 ? null : loanDao.getLoan(book_id, member_id);
    }

    @Override
    public List<Loan> getLoans(int memberId) {
        return loanDao.getLoans(memberId);
    }

    @Override
    public boolean setLoanState(int loanId, boolean deleted) {
        return loanDao.setLoanState(loanId, deleted);
    }

}
