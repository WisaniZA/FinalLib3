package com.lib.process.impl;

import com.lib.bean.Member;
import com.lib.process.ProcessRequest;
import com.lib.service.MemberService;
import com.lib.service.impl.MemberServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class MemberProcess implements ProcessRequest {

    BasicDataSource dbManager = null;

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse) {
        ServletContext sc = request.getServletContext();

        if (sc != null) {
            dbManager = (BasicDataSource) sc.getAttribute("dbman");
        }
        if (dbManager == null) {
            return;
        }
        MemberService memberService = new MemberServiceImpl(dbManager);
        RequestDispatcher view = null;
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        List<Member> m;
        if (action != null) {

            switch (action.toLowerCase()) {
                case "add":
                    Member member = new Member(request.getParameter("name"),
                            request.getParameter("surname"),
                            request.getParameter("address"),
                            request.getParameter("workTel"),
                            request.getParameter("mobileTel"),
                            request.getParameter("email"),
                            request.getParameter("password"));

                    if (memberService.addMember(member)) {
                        System.out.println("Member added");
                        request.setAttribute("message", "succefully created account");
                        if (request.getAttribute("m") == null) {
                            request.setAttribute("msg", "Succefully Added");
                            view = request.getRequestDispatcher("./amember.jsp");
                        } else {
                            view = request.getRequestDispatcher("./pages/login.jsp");
                        }
                    } else {
                        System.out.println("unsuccessful add");
                        request.setAttribute("msg", "member already exist");
                        view = request.getRequestDispatcher("./pages/create-account.jsp");
                    }

                    break;
                // ***************
                case "delete":
                    if (memberService.removeMember(request.getParameter("email"))) {
                        System.out.println("Deleted");
                        request.setAttribute("msg", "Succefully deleted");
                        view = request.getRequestDispatcher("./amember.jsp");
                    } else {
                        System.out.println("Not deleted");
                        //  view = request.getRequestDispatcher("ERRORXXXX.jsp");
                    }
                    break;
                // ***************  
                case "login":
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    member = memberService.login(email, password);

                    if (member != null) {

                        session.setAttribute("mem", member);

                        switch (member.getRoleId()) {
                            case 1:
                                view = request.getRequestDispatcher("./amember.jsp");
                                break;
                            case 2:
                                session.setAttribute("isMember", "true");

                                view = request.getRequestDispatcher("./mhome.jsp");
                                break;
                            default:
                                view = request.getRequestDispatcher("./pages/login.jsp");
                                break;
                        }

                        session.setAttribute("memberId", member.getMemberId());
                    } else {
                        System.out.println("Invalid UN or PW");
                        view = request.getRequestDispatcher("./pages/login.jsp");
                    }
                    break;
                // *************** 

                case "logout":
                    //session.invalidate();
                    view = request.getRequestDispatcher("index.jsp");
                    break;

                // *************** 
                case "update":
                    member = new Member(Integer.parseInt(request.getParameter("memberId")),
                            Integer.parseInt(request.getParameter("roleId")),
                            request.getParameter("name"),
                            request.getParameter("surname"),
                            request.getParameter("address"),
                            request.getParameter("workTel"),
                            request.getParameter("mobileTel"),
                            request.getParameter("email"),
                            request.getParameter("password"),
                            Boolean.parseBoolean(request.getParameter("deleted")));
                    if (memberService.updateMember(member)) {
                        session.setAttribute("mem", member);
                        request.setAttribute("msg", "Succefully updated");
                        view = request.getRequestDispatcher("./amember.jsp");
                    } else {
                        System.out.println("Not Updateds");
                        //  view = request.getRequestDispatcher("ERRORXXXX.jsp");
                    }
                    break;
                // *************** 
                case "updaterole":
                    int roleId = -1;
                    try {
                        roleId = Integer.parseInt(request.getParameter("roleId"));
                    } catch (NumberFormatException nfe) {
                    }
                    if (memberService.updateMemberRole(request.getParameter("email"), roleId)) {
                        System.out.println("successfully updated role");
                        view = request.getRequestDispatcher("./memberDashboard.jsp");
                    } else {
                        System.out.println("Not Updateds");
                        //  view = request.getRequestDispatcher("ERRORXXXX.jsp");
                    }
                    break;

                // ***************
                //cartitems
                case "cart":

                    List<Integer> cartItems = (List<Integer>) session.getAttribute("cartItems");
                    cartItems.add(Integer.parseInt((String) request.getAttribute("bookId")));

                    request.setAttribute("msg", "added cart");
                    request.getRequestDispatcher("./mshop.jsp");

                    break;

                // ***************   
                default:
                    System.out.println("Invalid action specified");
                    //  view = request.getRequestDispatcher("ERRORXXXX.jsp");
                    break;
            }
            m = memberService.getMembers();
            session.setAttribute("members", m);
        } else {
            System.out.println("No action specified");
            //  view = request.getRequestDispatcher("ERRORXXXX.jsp");
        }
        try {
            if (view != null) {
                view.forward(request, reponse);
            }
        } catch (ServletException | IOException ex) {
        }
    }
    // ******************************************************************* 
}
