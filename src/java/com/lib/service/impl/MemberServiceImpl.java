package com.lib.service.impl;

import com.lib.bean.Member;
import com.lib.dao.MemberDao;
import com.lib.dao.impl.MemberDaoImpl;
import com.lib.service.MemberService;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class MemberServiceImpl implements MemberService{
  private final MemberDao memberDao;

  public MemberServiceImpl(BasicDataSource bds){
    memberDao = new MemberDaoImpl(bds);
  }

  // *******************************************************************************************   
  @Override
  public boolean removeMember(String email){
    return email==null ? false : memberDao.removeMember(email);
  }
// *******************************************************************************************  

  @Override
  public boolean addMember(Member member){
    if((member == null) || (memberDao.getMember(member.getEmail()) != null)){
      return false; //user exists
    }
    //otherwisse add member
    return memberDao.addMember(member);
  }

  // *******************************************************************************************   
  @Override
  public boolean updateMember(Member member){
    return member == null ? false : memberDao.updateMember(member);
  }

  // *******************************************************************************************  
  @Override
  public boolean updateLoginDetails(int memberId, String email, String password){
    return memberDao.updateLoginDetails(memberId, email, password);
  }

  // *******************************************************************************************  
  @Override
  public Member login(String email, String password){
    if(email == null || email.isEmpty() || password == null){
      return null;
    }
    int member_id = memberDao.isMemberValid(email, password);
    if(member_id > 0){
      return memberDao.getMember(email);
    }
    return null;
  }

  // *******************************************************************************************   
  @Override
  public boolean setMemberStatus(String email, boolean deleted){
    return email ==null ? false : memberDao.setMemberStatus(email, deleted);
  }
  
  // *******************************************************************************************   
  @Override
  public boolean updateMemberRole(String email, int role){
    return email ==null ? false : memberDao.updateMemberRole(email, role);
  }
  // *******************************************************************************************  

    @Override
    public Member getMember(String email, boolean deleted) {
        return memberDao.getMember(email,false);
    }

    @Override
    public List<Member> getMembers() {
        return memberDao.getMembers();
    }
}
