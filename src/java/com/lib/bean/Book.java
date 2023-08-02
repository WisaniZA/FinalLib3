package com.lib.bean;

import java.util.List;
import java.util.Objects;

public class Book{
  private String isbn;
  private int catId;
  private String title;
  private String pubDate;
  private String rackNum;
  private boolean deleted;
  private List<Author> authors;

  public Book(String isbn, int catId, String title, String pubDate, String rackNum, boolean deleted){
    this.isbn = isbn;
    this.catId = catId;
    this.title = title;
    this.pubDate = pubDate;
    this.rackNum = rackNum;
    this.deleted = deleted;
  }

  public Book(String isbn, int catId, String title, String pubDate, String rackNum, List<Author> authors){
    this(isbn, catId, title, pubDate, rackNum, false);
  }

    public Book(String isbn, int catId, String title, String pubDate, String rackNum) {
        this.isbn = isbn;
        this.catId = catId;
        this.title = title;
        this.pubDate = pubDate;
        this.rackNum = rackNum;
    }

  
  
  public Book(){
  }

  public boolean isDeleted(){
    return deleted;
  }

  public void setDeleted(boolean deleted){
    this.deleted = deleted;
  }

  public String getIsbn(){
    return isbn;
  }

  public void setIsbn(String isbn){
    this.isbn = isbn;
  }

  public int getCatId(){
    return catId;
  }

  public void setCatId(int catId){
    this.catId = catId;
  }

  public String getTitle(){
    return title;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public String getPubDate(){
    return pubDate;
  }

  public void setPubDate(String pubDate){
    this.pubDate = pubDate;
  }

  public String getRackNum(){
    return rackNum;
  }

  public void setRackNum(String rackNum){
    this.rackNum = rackNum;
  }

  public List<Author> getAuthors(){
    return authors;
  }

  public void setAuthors(List<Author> authors){
    this.authors = authors;
  }

  @Override
  public String toString(){
    return "Book{" + "isbn=" + isbn + ", catId=" + catId + ", title=" + title + ", pubDate=" + pubDate + ", rackNum=" + rackNum + ", deleted=" + deleted + ", authors=" + authors + '}';
  }

 
  @Override
  public int hashCode(){
    int hash = 5;
    hash = 31 * hash + Objects.hashCode(this.isbn);
    hash = 31 * hash + Objects.hashCode(this.catId);
    hash = 31 * hash + Objects.hashCode(this.title);
    hash = 31 * hash + Objects.hashCode(this.pubDate);
    hash = 31 * hash + Objects.hashCode(this.rackNum);
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
    final Book other = (Book) obj;
    if(this.catId != other.catId){
      return false;
    }
    if( ! Objects.equals(this.isbn, other.isbn)){
      return false;
    }
    if( ! Objects.equals(this.title, other.title)){
      return false;
    }
    if( ! Objects.equals(this.pubDate, other.pubDate)){
      return false;
    }
    if( ! Objects.equals(this.rackNum, other.rackNum)){
      return false;
    }
    return true;
  }
}
