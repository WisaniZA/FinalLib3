package com.lib.service;

import com.lib.bean.Member;
import com.lib.dao.MemberDao;
import java.util.List;

public interface MemberService {

    boolean addMember(Member member);

    boolean removeMember(String email);

    boolean updateMemberRole(String email, int role);

    boolean updateMember(Member member);

    boolean updateLoginDetails(int memberId, String email, String password);

    Member login(String email, String password);

    boolean setMemberStatus(String email, boolean deleted);

    Member getMember(String email, boolean deleted);

    List<Member> getMembers();
    //Member getMember(int memberId, boolean deleted);
    //Member getMember(int memberId);
}
