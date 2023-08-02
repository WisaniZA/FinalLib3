package com.lib.bean;

import java.util.Objects;

public class Member{
  private int memberId;
  private int roleId;
  private String name;
  private String surname;
  private String address;
  private String workTel;
  private String mobileTel;
  private String email;
  private String password;
  private boolean deleted;

  public Member(){
  }
  
  public Member(String name, String surname, String address, String workTel, String mobileTel, String email, String password){
    this(0,2,name,surname,address,workTel,mobileTel,email,password,false);
  }

  public Member(int memberId, int roleId, String name, String surname, String address, String workTel, String mobileTel, String email, String password, boolean deleted){
    this.memberId = memberId;
    this.roleId = roleId;
    this.name = name;
    this.surname = surname;
    this.address = address;
    this.workTel = workTel;
    this.mobileTel = mobileTel;
    this.email = email;
    this.password = password;
    this.deleted = deleted;
  }

  public boolean isDeleted(){
    return deleted;
  }

  public void setDeleted(boolean deleted){
    this.deleted = deleted;
  }

  public int getMemberId(){
    return memberId;
  }

  public void setMemberId(int memberId){
    this.memberId = memberId;
  }

  public int getRoleId(){
    return roleId;
  }

  public void setRoleId(int roleId){
    this.roleId = roleId;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getSurname(){
    return surname;
  }

  public void setSurname(String surname){
    this.surname = surname;
  }

  public String getAddress(){
    return address;
  }

  public void setAddress(String address){
    this.address = address;
  }

  public String getWorkTel(){
    return workTel;
  }

  public void setWorkTel(String workTel){
    this.workTel = workTel;
  }

  public String getMobileTel(){
    return mobileTel;
  }

  public void setMobileTel(String mobileTel){
    this.mobileTel = mobileTel;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String password){
    this.password = password;
  }

  @Override
  public String toString(){
    return "Member{" + "memberId=" + memberId + ", roleId=" + roleId + ", name=" + name + ", surname=" + surname + ", address=" + address + ", workTel=" + workTel + ", mobileTel=" + mobileTel + ", email=" + email + ", password=" + password + '}';
  }

  @Override
  public int hashCode(){
    int hash = 3;
    return hash;
  }

  @Override
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }
    if(obj == null){
      return false;
    }
    if(getClass() != obj.getClass()){
      return false;
    }
    final Member other = (Member) obj;
    if(this.memberId != other.memberId){
      return false;
    }
    if(this.roleId != other.roleId){
      return false;
    }
    if(this.workTel != other.workTel){
      return false;
    }
    if(this.mobileTel != other.mobileTel){
      return false;
    }
    if( ! Objects.equals(this.name, other.name)){
      return false;
    }
    if( ! Objects.equals(this.surname, other.surname)){
      return false;
    }
    if( ! Objects.equals(this.address, other.address)){
      return false;
    }
    if( ! Objects.equals(this.email, other.email)){
      return false;
    }
    return Objects.equals(this.password, other.password);
  }
}
