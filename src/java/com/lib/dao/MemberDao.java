package com.lib.dao;

import com.lib.bean.Member;
import java.util.List;

public interface MemberDao{
  boolean addMember(Member member);

  boolean removeMember(String email);

  Member getMember(String email);

  boolean updateMember(Member member);

  boolean updateMemberRole(String email, int role);

  boolean updateLoginDetails(int memberId, String email, String password);

  int isMemberValid(String username, String password);

  boolean setMemberStatus(String email, boolean deleted);

  Member getMember(String email, boolean deleted);
  
  List<Member> getMembers();
    
}
