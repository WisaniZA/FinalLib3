/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.service.impl;



import com.lib.service.ReservationService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import com.lib.dao.ReservationDao;

/**
 *
 * @author TRAIN 79
 */
public class ReservationServiceImpl implements ReservationService{
    
    private ReservationDao reservationDao;
    private BasicDataSource dbManager;
    private Date reserveDate;

    @Override
    public boolean createReservation(int reserveId, int bookId, int memberId, Date reserveDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeReservation(int reserveId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse) {
         ServletContext sc = request.getServletContext();
        if (sc != null) {
            dbManager = (BasicDataSource) sc.getAttribute("dbman");
        }
        if (dbManager == null) {
            return;
        }
        String action = request.getParameter("action");

        String role = "";
        if (action != null) {
            int reserveId = 0;
            int memberId = 0;
            int bookId = 0;
            switch (action.toLowerCase()) {

                // ***************
                case "delete":
                    reserveId = 0;
                    try {
                        reserveId = Integer.parseInt(request.getParameter("reserveId"));
                    } catch (NumberFormatException nfe) {
                    }
                    if (removeReservation(reserveId)) {
                    }
                    break; 
                    
                    
                   //*************
                case "add":
                    reserveId = 0;
                    memberId = 0;
                    bookId = 0;
                    try {
                        reserveId = Integer.parseInt(request.getParameter("role_id"));
                    } catch (NumberFormatException nfe) {
                    }
                    role = request.getParameter("role");
                    if (createReservation(reserveId, bookId, memberId, reserveDate)) {
                        request.setAttribute("reserveId", reserveId);
                        request.setAttribute("bookId", bookId);
                        request.setAttribute("memberId", memberId);
                        request.setAttribute("reserveDate", reserveDate);

                        request.setAttribute("msg", "The reservation has been added");
                        RequestDispatcher view = request.getRequestDispatcher(".jsp");//////////////////
                        try {
                            view.forward(request, reponse);
                        } catch (ServletException | IOException ex) {
                        }
                    }
                    break;
                default:

            }
        }
    }

  
   
    
}