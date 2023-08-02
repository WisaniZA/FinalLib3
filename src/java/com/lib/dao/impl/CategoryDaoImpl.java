package com.lib.dao.impl;

import com.lib.bean.Category;
import com.lib.dao.CategoryDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class CategoryDaoImpl implements CategoryDao{
  
  private BasicDataSource bds = null;
  private Connection con = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  boolean bool;
  
  public CategoryDaoImpl(BasicDataSource bds){
    this.bds = bds;
  }
  
  public CategoryDaoImpl(){
  }
  
  @Override
  public boolean removeCategory(int catId){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE category SET isActive = 0 WHERE cat_id=?");
        ps.setInt(1, catId);
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
  
  @Override
  public boolean updateCategory(Category category){
    
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE category SET genre=? WHERE cat_id=?");
        ps.setString(1, category.getGenre());
        ps.setInt(2, category.getCat_id());
        
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
  
  @Override
  public boolean addCategory(Category category){
    
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("INSERT INTO category(cat_id, genre, isActive) VALUES(null,?,1)");
        ps.setString(1, category.getGenre());
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
  
  @Override
  public Category getCategory(int catId){
    Category category = null;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT genre, isActive FROM category WHERE cat_id=?");
        ps.setInt(1, catId);
        rs = ps.executeQuery();
        if(rs.next()){
          category = new Category(catId, rs.getString("genre"), rs.getBoolean("isActive"));
        }
      }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return category;
  }
  
  @Override
  public List<Category> getCategories(){
    List<Category> categories = new ArrayList<>();
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT cat_id, genre, isActive FROM category WHERE isActive=true");
        rs = ps.executeQuery();
        while(rs.next()){
          categories.add(new Category(rs.getInt("cat_id"), rs.getString("genre"), rs.getBoolean("isActive")));
        }
      }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return categories;
  }
  
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
        
      }finally{
        con = null;
      }
    }
  }
  
}
