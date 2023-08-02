package com.lib.dao.impl;

import com.lib.bean.Role;
import com.lib.dao.RoleDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class RoleDaoImpl implements RoleDao{
  private BasicDataSource bds = null;
  private Connection con = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  boolean bool;

  public RoleDaoImpl(BasicDataSource bds){
    this.bds = bds;
  }

// *******************************************************************************************
  public boolean removeRole(int roleId){
    return setRole(roleId, false);
  }
  
// *******************************************************************************************
  @Override
  public boolean setRole(int roleId, boolean active){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE role SET active = ? WHERE role_id=?");
        ps.setBoolean(1, active);
        ps.setInt(2, roleId);
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

  // *******************************************************************************************
  @Override
  public boolean updateRole(Role role){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE role SET role=? WHERE role_id=?");
        ps.setString(1, role.getRole());
        ps.setInt(2, role.getRoleId());
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

  // *******************************************************************************************
  @Override
  public boolean addRole(Role role){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("INSERT INTO role(role_id,role,active) VALUES(?,?,?)");
        ps.setInt(1, role.getRoleId());
        ps.setString(2, role.getRole());
        ps.setBoolean(3, role.isActive());
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

  // *******************************************************************************************
  @Override
  public Role getRole(int roleId, boolean active){
    Role role = null;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT role_id, role, active FROM role WHERE role_id=? AND active = ?");
        ps.setInt(1, roleId);
        ps.setBoolean(2, active);
        rs = ps.executeQuery();
        if(rs.next()){
          role = new Role(rs.getInt("role_id"),rs.getString("role"),rs.getBoolean("active"));
        }
      }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return role;
  }

  // *******************************************************************************************
  @Override
  public List<Role> getRoles(boolean active){
    List<Role> roles = new ArrayList<>();
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT role_id, role, active FROM role WHERE active = ? ORDER BY role ASC");
        ps.setBoolean(1, active);
        rs = ps.executeQuery();
        while(rs.next()){
          roles.add(new Role(rs.getInt("role_id"),rs.getString("role"),rs.getBoolean("active")));
        }
      }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return roles;
  }

  // *******************************************************************************************
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
  // *******************************************************************************************
}
