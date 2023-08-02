package com.lib.dao;

import com.lib.bean.Author;
import java.util.List;

public interface AuthorDao{
  boolean removeAuthor(int authId);

  boolean updateAuthor(Author author);

  boolean addAuthor(Author author);

  Author getAuthor(int authId);

  List<Author> getAuthors(boolean deleted);

  boolean setAuthorState(int authId, boolean enabled);
}
