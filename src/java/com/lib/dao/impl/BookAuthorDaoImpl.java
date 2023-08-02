package com.lib.dao.impl;

import com.lib.bean.BookAuthor;
import com.lib.dao.BookAuhtorDao;
import com.lib.dao.BookDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class BookAuthorDaoImpl implements BookAuhtorDao{

    private BasicDataSource bds = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    boolean bool;
    
    public BookAuthorDaoImpl(BasicDataSource bds) {
        this.bds = bds;
    }
    
    @Override
    public boolean addBookAuthor(BookAuthor bookAuthor) {
       
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("INSERT INTO book_author VALUES(?,?)");
                ps.setString(1, bookAuthor.getIsbn());
                ps.setInt(2, bookAuthor.getAuthId());

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
    public boolean removeByBook(String isbn) {
        
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("DELETE FROM book_author WHERE isbn=?");
                ps.setString(1, isbn);
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
    public boolean removeByAuthor(int authId) {
        
        
     bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("DELETE FROM book_author WHERE auth_id=?");
                ps.setInt(1, authId);
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
