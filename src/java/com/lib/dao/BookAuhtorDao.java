package com.lib.dao;

import com.lib.bean.BookAuthor;

public interface BookAuhtorDao {
    boolean addBookAuthor(BookAuthor bookAuthor);
    boolean removeByBook(String isbn);
    boolean removeByAuthor(int auth_id);
}
