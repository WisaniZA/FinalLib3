package com.lib.dao.impl;

import com.lib.bean.Author;
import com.lib.dao.AuthorDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class AuthorDaoImpl implements AuthorDao{
  private BasicDataSource bds = null;
  private Connection con = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  boolean bool;

  public AuthorDaoImpl(BasicDataSource bds){
    this.bds = bds;
  }

 // ************************************************************************* 
  @Override
  public boolean removeAuthor(int authId){
    return setAuthorState(authId, true);
  }

// *************************************************************************
  @Override
  public boolean setAuthorState(int authId, boolean enabled){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE author SET deleted=? WHERE auth_id=?");
        ps.setBoolean(1, enabled);
        ps.setInt(2, authId);
        if(ps.executeUpdate() > 0){
          bool = true;
        }
      }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return bool;
  }

// *************************************************************************  
  @Override
  public boolean updateAuthor(Author author){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE author SET name=?,surname=? WHERE auth_id=?");
        ps.setString(1, author.getName());
        ps.setString(2, author.getSurname());
        ps.setInt(3, author.getAuthId());
        if((ps.executeUpdate() > 0)){
          bool = true;
        }
      }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return bool;
  }

// *************************************************************************  
  @Override
  public boolean addAuthor(Author author){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("INSERT INTO author(auth_id, name, surname, deleted) VALUES(null,?,?,?)");
        ps.setString(1, author.getName());
        ps.setString(2, author.getSurname());
        ps.setBoolean(3, author.isDeleted());
        if((ps.executeUpdate() > 0)){
          bool = true;
        }
      }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return bool;
  }

// *************************************************************************  
  @Override
  public Author getAuthor(int authId){
    Author author=null;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT auth_id, name, surname, deleted FROM author WHERE auth_id=? AND deleted=false");
        ps.setInt(1, authId);
        rs = ps.executeQuery();
        if(rs.next()){
          author = new Author(rs.getInt("auth_id") ,rs.getString("name"), rs.getString("surname"), rs.getBoolean("deleted"));
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return author;
  }

// *************************************************************************  
  @Override
  public List<Author> getAuthors(boolean deleted){
    List<Author> authors = new ArrayList<Author>();
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT auth_id, name, surname FROM author WHERE deleted=?");
        ps.setBoolean(1, false);
        rs = ps.executeQuery();
        while(rs.next()){
          authors.add(new Author(rs.getInt("auth_id"), rs.getString("name"), rs.getString("surname"),deleted));
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return authors;
  }

// *************************************************************************  
  public void closePsCon(PreparedStatement preStm, Connection con){
    if(preStm != null){
      try{
        preStm.close();
      }catch(SQLException ex){
        System.out.println("Could not close: " + ex.getMessage());
      }
      try{
        con.close();
      }catch(SQLException ex){
      }
    }
  }
}
