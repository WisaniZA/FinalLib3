package com.lib.bean;

import java.util.Objects;

public class Author{
  private int authId;
  private String name;
  private String surname;
  private boolean deleted;

  public Author(){
  }

  public Author(int authId){
    this(authId, "", "", false);
  }
  
  public Author(String name, String surname){
    this(0, name, surname, false);
  }

  public Author(int authId, String name, String surname){
    this(authId, name, surname, false);
  }

  public Author(int authId, String name, String surname, boolean deleted){
    this.authId = authId;
    this.name = name;
    this.surname = surname;
    this.deleted = deleted;
  }

  @Override
  public int hashCode(){
    int hash = 7;
    hash = 71 * hash + this.authId;
    hash = 71 * hash + Objects.hashCode(this.name);
    hash = 71 * hash + Objects.hashCode(this.surname);
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
    final Author other = (Author) obj;
    if(this.authId != other.authId){
      return false;
    }
    if( ! Objects.equals(this.name, other.name)){
      return false;
    }
    if( ! Objects.equals(this.surname, other.surname)){
      return false;
    }
    return true;
  }

  public int getAuthId(){
    return authId;
  }

  public void setAuthId(int authId){
    this.authId = authId;
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

  public boolean isDeleted(){
    return deleted;
  }

  public void setDeleted(boolean deleted){
    this.deleted = deleted;
  }

  @Override
  public String toString(){
    return "Author{" + "authId=" + authId + ", name=" + name + ", surname=" + surname + ", deleted=" + deleted + '}';
  }
}
