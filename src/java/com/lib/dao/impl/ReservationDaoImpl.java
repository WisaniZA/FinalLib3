/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.dao.impl;

import com.lib.service.ReservationService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 *
 * @author TRAIN 79
 */
public class ReservationDaoImpl implements ReservationService{
    
    private BasicDataSource bds = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    boolean bool;
    
    public ReservationDaoImpl(BasicDataSource bds) {
        this.bds = bds;
    }
    
    
    

    @Override
    public boolean createReservation(int reserveId, int bookId, int memberId, Date reserveDate) {
        
         bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("INSERT INTO category VALUES(?,?)");
                ps.setInt(1, reserveId);
                ps.setInt(2, bookId);
                ps.setInt(3, memberId);
                ps.setDate(4, (java.sql.Date) reserveDate);
                

                if ((ps.executeUpdate() > 0)) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                 closePsCon(ps,con);
                con = null;
            }
        }
        return bool;
    }

    
    
    @Override
    public boolean removeReservation(int reserveId) {
         bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("DELETE FROM reservation WHERE reserve_id=?");
                ps.setInt(1, reserveId);
                if (ps.executeUpdate() > 0) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps,con);
                con = null;
            }
        }
        return bool;
        
    }

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void closePsCon(PreparedStatement preStm, Connection con) {
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
