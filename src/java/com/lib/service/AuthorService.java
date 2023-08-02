package com.lib.service;

import com.lib.bean.Author;
import java.util.List;

public interface AuthorService{
  boolean removeAuthor(int authId);

  boolean updateAuthor(Author author);

  boolean addAuthor(Author author);

  Author getAuthor(int authId);

  List<Author> getAuthors(boolean deleted);
}
