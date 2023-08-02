package com.lib.bean;

import java.util.Objects;

public class Role{
  private int roleId;
  private String role;
  private boolean active;

  public Role(){
    this(0, "", true);
  }

  public Role(int roleId, String role){
    this(roleId, role, true);
  }

  public Role(int roleId, String role, boolean active){
    this.roleId = roleId;
    this.role = role;
    this.active = active;
  }

  public int getRoleId(){
    return roleId;
  }

  public void setRoleId(int roleId){
    this.roleId = roleId;
  }

  public String getRole(){
    return role;
  }

  public void setRole(String role){
    this.role = role;
  }

  public boolean isActive(){
    return active;
  }

  public void setActive(boolean active){
    this.active = active;
  }

  @Override
  public String toString(){
    return "Role{" + "roleId=" + roleId + ", role=" + role + ", active=" + active + '}';
  }

  @Override
  public int hashCode(){
    int hash = 7;
    hash = 97 * hash + this.roleId;
    hash = 97 * hash + Objects.hashCode(this.role);
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
    final Role other = (Role) obj;
    if(this.roleId != other.roleId){
      return false;
    }
    return Objects.equals(this.role, other.role);
  }
}
