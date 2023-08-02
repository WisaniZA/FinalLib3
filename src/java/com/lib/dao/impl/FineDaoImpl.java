
package com.lib.dao.impl;

import com.lib.bean.Fine;
import com.lib.dao.FineDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class FineDaoImpl implements FineDao {
    
  private BasicDataSource bds = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    boolean bool;
    
     public FineDaoImpl(BasicDataSource bds) {
        this.bds = bds;
    }
    
    @Override
    public boolean removeFine(int fineId) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("DELETE FROM fine WHERE fine_id=?");
                ps.setInt(1, fineId);
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
    public boolean updateFine(Fine fine) {
        
          bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("UPDATE fine SET fineAmount=? WHERE fineId=?");
                ps.setInt(1,fine.getFineId() );
                ps.setInt(2, fine.getLoanId());
                ps.setDouble(3, fine.getFineAmount());

                if (ps.executeUpdate() > 0) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps,con);
                
                con = null;
            }
        }
        return bool;
    }

    @Override
    public boolean addFine(Fine fine) {
       
            bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("INSERT INTO fine VALUES(?,?,?,?)");
                ps.setInt(1,fine.getFineId() );
                ps.setInt(2, fine.getLoanId());
                ps.setDouble(3, fine.getFineAmount());
                ps.setDouble(4, fine.getPriceId());

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
    public String getFine(int fineId) {
  String memberName = "";
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT fine_amount FROM fine WHERE fine_id=?");
                ps.setInt(1, fineId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    memberName = rs.getString("name");
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                 closePsCon(ps,con);
                con = null;
            }
        }
        return memberName;
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

    @Override
    public boolean updateFine(int fineId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
