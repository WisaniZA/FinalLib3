package com.lib.service.impl;

import com.lib.bean.Author;
import com.lib.dao.AuthorDao;
import com.lib.dao.impl.AuthorDaoImpl;
import com.lib.service.AuthorService;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class AuthorServiceImpl implements AuthorService{
  private final AuthorDao authorDao;

  public AuthorServiceImpl(BasicDataSource bds){
    authorDao = new AuthorDaoImpl(bds);
  }

  @Override
  public boolean removeAuthor(int authId){
    return authId < 0 ? false : authorDao.removeAuthor(authId);
  }

  @Override
  public boolean updateAuthor(Author author){
    return author == null ? false : authorDao.updateAuthor(author);
  }

  @Override
  public boolean addAuthor(Author author){
    return author == null ? false : authorDao.addAuthor(author);
  }

  @Override
  public Author getAuthor(int authId){
    return authId < 0 ? null : authorDao.getAuthor(authId);
  }

  @Override
  public List<Author> getAuthors(boolean deleted){
    return authorDao.getAuthors(deleted);
  }
}
