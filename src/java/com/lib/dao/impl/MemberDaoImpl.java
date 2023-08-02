package com.lib.dao.impl;

import com.lib.bean.Member;
import com.lib.dao.MemberDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class MemberDaoImpl implements MemberDao{
  private BasicDataSource bds = null;
  private Connection con = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  boolean bool;

  public MemberDaoImpl(BasicDataSource bds){
    this.bds = bds;
  }

// *******************************************************************************************  
  @Override
  public boolean addMember(Member member){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("INSERT INTO member(member_id, role_id, name, surname, address, work_tel, mobile_tel, email, password, deleted) VALUES(null,2,?,?,?,?,?,?,?,?)");
        ps.setString(1, member.getName());
        ps.setString(2, member.getSurname());
        ps.setString(3, member.getAddress());
        ps.setString(4, member.getWorkTel());
        ps.setString(5, member.getMobileTel());
        ps.setString(6, member.getEmail());
        ps.setString(7, member.getPassword());
        ps.setBoolean(8, member.isDeleted());
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
  public boolean removeMember(String email){
    return setMemberStatus(email, true);
  }

// *******************************************************************************************  
  @Override
  public boolean setMemberStatus(String email, boolean deleted){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE member SET deleted=? WHERE email=?");
        ps.setBoolean(1, deleted);
        ps.setString(2, email);
        if(ps.executeUpdate() > 0){
          bool = true;
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return bool;
  }

// *******************************************************************************************  
  @Override
  public Member getMember(String email){
      
    return getMember(email, false);
  }
// *******************************************************************************************  

  @Override
  public Member getMember(String email, boolean deleted){
    Member member = null;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT member_id, role_id, name, surname, address, work_tel, mobile_tel, deleted FROM member WHERE email=? AND deleted = ?");
        ps.setString(1, email);
        ps.setBoolean(2, false);
        rs = ps.executeQuery();
        if(rs.next()){
          member = new Member();
          member.setMemberId(rs.getInt("member_id"));
          member.setRoleId(rs.getInt("role_id"));
          member.setName(rs.getString("name"));
          member.setSurname(rs.getString("surname"));
          member.setAddress(rs.getString("address"));
          member.setWorkTel(rs.getString("work_tel"));
          member.setMobileTel(rs.getString("mobile_tel"));
          member.setDeleted(rs.getBoolean("deleted"));
          member.setEmail(email);
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return member;
  }

// *******************************************************************************************  
  @Override
  public boolean updateMember(Member member){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE member SET role_id=?,name=?,surname=?,address=?,work_tel=?,mobile_tel=?,email=? WHERE member_id=?");
        ps.setInt(1, member.getRoleId());
        ps.setString(2, member.getName());
        ps.setString(3, member.getSurname());
        ps.setString(4, member.getAddress());
        ps.setString(5, member.getWorkTel());
        ps.setString(6, member.getMobileTel());
        ps.setString(7, member.getEmail());
        ps.setInt(8, member.getMemberId());
        
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
  public boolean updateLoginDetails(int memberId, String email, String password){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE member SET password=?, email=? WHERE member_id=?");
        ps.setString(1, password);
        ps.setString(2, email);
        ps.setInt(3, memberId);
        if(ps.executeUpdate() > 0){
          bool = true;
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return bool;
  }

  // *******************************************************************************************  
  @Override
  public int isMemberValid(String username, String password){
    int retVal = -1;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("SELECT member_id FROM member WHERE email=? AND password=?");
        ps.setString(1, username);
        ps.setString(2, password);
        rs = ps.executeQuery();
        if(rs.next()){
          retVal = rs.getInt("member_id");
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return retVal;
  }
  // ******************************************************************************************* 

  @Override
  public boolean updateMemberRole(String email, int role){
    bool = false;
    if(bds != null){
      try{
        con = bds.getConnection();
        ps = con.prepareStatement("UPDATE member SET role_id=?  WHERE email=?");
        ps.setInt(1, role);
        ps.setString(2, email);
        if(ps.executeUpdate() > 0){
          bool = true;
        }
      }catch(SQLException ex){
        System.out.println("Error!!: " + ex.getMessage());
      }finally{
        closePsCon(ps, con);
      }
    }
    return bool;
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

    @Override
    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT member_id, role_id, name, surname, address, work_tel, mobile_tel, email, password, deleted FROM member WHERE deleted=false");

                rs = ps.executeQuery();
                while (rs.next()) {
                    members.add(new Member(rs.getInt("member_id"),rs.getInt("role_id"), rs.getString("name"), rs.getString("surname"), rs.getString("address"), rs.getString("work_tel"),rs.getString("mobile_tel"), rs.getString("email"), rs.getString("password"),rs.getBoolean("deleted")));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return members;
    }
}
