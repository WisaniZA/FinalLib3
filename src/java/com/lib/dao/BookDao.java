package com.lib.dao;

import com.lib.bean.Book;
import java.util.List;

public interface BookDao {

    boolean addBook(Book book);

    boolean addBookItem(String isbn, int numberOfBooks);

    boolean removeBook(String isbn);

    boolean removeBookItem(int bookId);

    boolean setBookItemStatus(int bookId, boolean status);

    boolean setBookStatus(String isbn, boolean status);

    List<Book> getBook(String searchedWord);

    Book getActualBook(String isbn);

    boolean updateBook(Book book);

    List<Book> getBook();

}
