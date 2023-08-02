package com.lib.dao.impl;

import com.lib.bean.Loan;
import com.lib.dao.LoanDao;
import com.lib.dao.PriceDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class LoanDaoImpl implements LoanDao {

    private BasicDataSource bds = null;
    private Connection con = null;
    private Connection con1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private ResultSet rs = null;
    boolean bool;

    public LoanDaoImpl(BasicDataSource bds) {
        this.bds = bds;
    }

    @Override
    public boolean removeLoan(int loan_id) {
        return setLoanState(loan_id, true);
    }

    @Override
    public boolean setLoanState(int loan_id, boolean deleted) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("UPDATE loan AS L, book_item AS BI SET L.deleted=?, L.date_deleted=CURDATE(), BI.on_loan=? "
                        + " WHERE L.loan_id=? "
                        + " AND BI.book_id=L.book_id");
                ps.setBoolean(1, deleted);
                ps.setBoolean(2, !deleted);
                ps.setInt(3, loan_id);
                if (ps.executeUpdate() > 0) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return bool;
    }

//    @Override
//    public boolean updateLoan(Loan loan) {
//        bool = false;
//        if (bds != null) {
//            try {
//                con = bds.getConnection();
//                ps = con.prepareStatement("UPDATE loan SET book_id=? WHERE member_id=?");
//                ps.setInt(1, loan.getBook_id());
//                ps.setInt(2, loan.getMember_id());
//
//                if ((ps.executeUpdate() > 0)) {
//                    bool = true;
//                }
//            } catch (SQLException ex) {
//                System.out.println("Error: " + ex.getMessage());
//            } finally {
//                closePsCon(ps, con);
//                con = null;
//            }
//        }
//        return bool;
//    }
    // **************************************************************************************************
    @Override
    public boolean isBookAvailable(int book_id) {
        boolean retVal = true;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT on_loan FROM book_item WHERE book_id = ?");
                ps.setInt(1, book_id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    retVal = !(rs.getBoolean("on_loan"));
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return retVal;
    }

    // **************************************************************************************************
    @Override
    public boolean addLoan(Loan loan) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                con.setAutoCommit(false);
                ps = con.prepareStatement("INSERT INTO loan(loan_id, book_id, member_id, date_issued, deleted)"
                        + " VALUES(null,?,?,CURDATE(),false)");
                ps.setInt(1, loan.getBook_id());
                ps.setInt(2, loan.getMember_id());
                if (ps.executeUpdate() > 0) {
                    con1 = bds.getConnection();
                    con1.setAutoCommit(false);
                    ps1 = con.prepareStatement("UPDATE book_item SET on_loan=true WHERE book_id=?");
                    ps1.setInt(1, loan.getBook_id());
                    if (ps1.executeUpdate() > 0) {
                        con.commit();
                        con1.commit();
                        bool = true;
                    } else {
                        con.rollback();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                }
                try {
                    con1.setAutoCommit(true);
                } catch (SQLException ex) {
                }
            }
        }
        return bool;
    }

    @Override
    public Loan getLoan(int book_id, int member_id) {
        Loan loan = null;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT loan_id, book_id, member_id, deleted FROM loan WHERE loan_id=?");
                ps.setInt(1, book_id);
                ps.setInt(2, member_id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    loan = new Loan(rs.getInt("book_id"), rs.getInt("member_id"));
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return loan;
    }

    @Override
    public List<Loan> getLoans(int memberId) {
        List<Loan> loans = new ArrayList<>();
        PriceDao priceDao = new PriceDaoImpl(bds);

        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT loan_id, member_id, book_id, date_issued, date_deleted, deleted fine_amount FROM loan WHERE deleted=0 AND member_id LIKE ? ORDER BY loan_id ASC");
                if (memberId != 0) {
                    ps.setInt(1, memberId);
                } else {
                    ps.setString(1, "%%");
                }

                DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                rs = ps.executeQuery();
                while (rs.next()) {

                    LocalDate dateIssued = LocalDate.parse(rs.getString("date_issued"), DATEFORMATTER);
                    Long daysOnLoan = ChronoUnit.DAYS.between(dateIssued, LocalDate.now());
                    double fineAmount = 0;

                    if (daysOnLoan >= 10) {
                        fineAmount = priceDao.getPrice(3) * daysOnLoan;
                    } else if (daysOnLoan >= 5) {
                        fineAmount = priceDao.getPrice(2) * daysOnLoan;
                    } else if (daysOnLoan >= 1) {
                        fineAmount = priceDao.getPrice(1) * daysOnLoan;
                    }

                    loans.add(new Loan(rs.getInt("loan_id"), rs.getInt("book_id"), rs.getInt("member_id"), rs.getDate("date_issued"), rs.getDate("date_issued"), fineAmount));
                }
            } catch (SQLException ex) {

                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return loans;
    }

    private void closePsCon(PreparedStatement preStm, Connection con) {
        if (preStm != null) {
            try {
                preStm.close();
            } catch (SQLException ex) {
                System.out.println("Could not close: " + ex.getMessage());
            }
            try {
                con.close();
            } catch (SQLException ex) {
            }
        }

    }

}
