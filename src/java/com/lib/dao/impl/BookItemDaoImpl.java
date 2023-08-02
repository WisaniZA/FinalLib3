/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.dao.impl;

import com.lib.bean.Author;
import com.lib.bean.BookItem;
import com.lib.dao.BookItemDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class BookItemDaoImpl implements BookItemDao {

    private BasicDataSource bds = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    boolean bool;

    public BookItemDaoImpl(BasicDataSource bds) {
        this.bds = bds;
    }

    @Override
    public boolean removeBookItem(int bookitemId) {

        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("DELETE FROM book_item WHERE book_id=?");
                ps.setInt(1, bookitemId);
                if (ps.executeUpdate() > 0) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return bool;

    }

    @Override
    public boolean updateBookItem(int bookId, boolean onLoan) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("UPDATE book_item SET on_loan=? WHERE book_id=?");
                ps.setInt(1, onLoan ? 1 : 0);
                ps.setInt(2, bookId);

                if ((ps.executeUpdate() > 0)) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return bool;
    }

    @Override
    public boolean addBookItem(BookItem bookitem) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("INSERT INTO book_item VALUES(?,?)");

                ps.setString(1, bookitem.getIsbn());
                ps.setBoolean(2, bookitem.isOnLoan());

                if ((ps.executeUpdate() > 0)) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return bool;
    }

    @Override
    public List<BookItem> getBookItems(boolean deleted) {

        List<BookItem> bookItems = new ArrayList<>();
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT auth_id, name, surname FROM author WHERE deleted=?");
                ps.setBoolean(1, false);
                rs = ps.executeQuery();
                while (rs.next()) {
                   // bookItems.add(new (rs.getInt("auth_id"), rs.getString("name"), rs.getString("surname"), deleted));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return bookItems;
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
