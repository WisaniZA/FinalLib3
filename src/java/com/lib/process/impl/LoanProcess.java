package com.lib.process.impl;

import com.lib.bean.Loan;
import com.lib.bean.Member;
import com.lib.dao.LoanDao;
import com.lib.process.ProcessRequest;
import com.lib.service.LoanService;
import com.lib.service.impl.LoanServiceImpl;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class LoanProcess implements ProcessRequest {

    private BasicDataSource dbManager;

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse) {

        ServletContext sc = request.getServletContext();
        HttpSession session = request.getSession();
        List<Loan> loans;

        if (sc != null) {
            dbManager = (BasicDataSource) sc.getAttribute("dbman");

        }
        if (dbManager == null) {
            return;
        }

        LoanService loanService = new LoanServiceImpl(dbManager);

        RequestDispatcher view = null;
        String action = request.getParameter("action");

        if (action != null) {
            int book_id = 0;
            int member_id = 0;
            int loan_id = 0;

            switch (action.toLowerCase()) {
                case "add":
                try {
                    book_id = Integer.parseInt(request.getParameter("bookId"));
                } catch (NumberFormatException e) {
                    System.out.println("Error :" + e.getMessage());
                }

                Member member = (Member) session.getAttribute("member");
                Loan loan = new Loan(member.getMemberId(), book_id);
                // Loan loan = new Loan(651234, book_id);
//---------------------------------------------------------------------------------

                if (loanService.isBookAvailable(book_id)) {
                    if (loanService.addLoan(loan)) {
                        System.out.println("Loan added");
                        view = request.getRequestDispatcher("Loan.jsp");
                    } else {
                        System.out.println("Unable to add a Loan");
                        view = request.getRequestDispatcher("404.jsp");
                    }
                } else {
                    System.out.println("Book is not available for loaning. Consider reserving it.");
                    view = request.getRequestDispatcher("Loan.jsp");
                }
                break;

                case "delete":
                    loan_id = -1;
                    try {
                        loan_id = Integer.parseInt(request.getParameter("loanId"));
                    } catch (NumberFormatException e) {
                        System.out.println("Error :" + e.getMessage());
                    }
                    if (loanService.removeLoan(loan_id)) {
                        request.setAttribute("msg", "The loan has been removed");
                        view = request.getRequestDispatcher("./aloans.jsp");
                    } else {
                        System.out.println("Unable to remove a loan");
                        view = request.getRequestDispatcher("404.jsp");
                    }
                    break;

                case "get":
                    
                try {
                    book_id = Integer.parseInt(request.getParameter("book_id"));

                } catch (NumberFormatException e) {
                    System.out.println("Error :" + e.getMessage());
                }

                loan = loanService.getLoan(book_id, member_id);
                if (loan != null) {
                    view = request.getRequestDispatcher("Loan.jsp");
                } else {
                    view = request.getRequestDispatcher("404.jsp");
                }
                break;

                case "getall":
                    if (session.getAttribute("isMember") != null) {
                        member_id = (Integer) session.getAttribute("memberId");
                    }

                    loans = loanService.getLoans(member_id);
                    if (!loans.isEmpty()) {
                        if (session.getAttribute("isMember") != null) {
                            view = request.getRequestDispatcher("./mloans.jsp");
                        } else {
                            view = request.getRequestDispatcher("./aloans.jsp");
                        }
                    } else {
                        request.setAttribute("msg", "No loans available");
                        view = request.getRequestDispatcher("./mloans.jsp");
                    }
                    break;

                case "checkout":

                    List<Integer> bookIds = (List<Integer>) session.getAttribute("cartItems");
                    int memberId = (int) session.getAttribute("memberId");

                    bookIds.stream().map(b -> new Loan(memberId, b)).forEachOrdered(loanss -> {
                        loanService.addLoan(loanss);
                    });

                    request.setAttribute("msg", "Checkout successful");
                    request.getRequestDispatcher("./mloans.jsp");
                    break;

                // ***************
                default:

                    view = request.getRequestDispatcher("404.jsp");
                    break;
            }
            loans = loanService.getLoans(member_id);
            session.setAttribute("loans", loans);
            try {
                if (view != null) {
                    view.forward(request, reponse);
                }
            } catch (ServletException | IOException ex) {
            }
        }
    }
}
