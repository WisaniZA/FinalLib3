package com.lib.service.impl;

import com.lib.bean.Book;
import com.lib.dao.BookDao;
import com.lib.dao.impl.BookDaoImpl;
import com.lib.service.BookService;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class BookServiceImpl implements BookService {

    private final String regex = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
    private final BookDao bookDao;

    public BookServiceImpl(BasicDataSource bds) {
        this.bookDao = new BookDaoImpl(bds);
    }

    @Override
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        if (!(book.getIsbn().matches(regex))) {
            return false;
        }
        return bookDao.addBook(book);
    }

    @Override
    public boolean removeBook(String bookId) {
        return bookDao.removeBook(bookId);
    }

    @Override
    public boolean updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public List<Book> getBook(String searchedWord) {
        return bookDao.getBook(searchedWord);
    }

    @Override
    public boolean addBookItem(String isbn, int numberOfBooks) {
        return bookDao.addBookItem(isbn, numberOfBooks);
    }

    @Override
    public boolean removeBookItem(int bookId) {
        return bookId < 0 ? false : bookDao.removeBookItem(bookId);
    }

    @Override
    public boolean setBookItemStatus(int bookId, boolean status) {
        return bookDao.setBookItemStatus(bookId, status);
    }

    @Override
    public boolean setBookStatus(String isbn, boolean status) {
        return bookDao.setBookStatus(isbn, status);
    }

    @Override
    public Book getActualBook(String isbn) {
        return bookDao.getActualBook(isbn);
    }

    @Override
    public List<Book> getBook() {
        return bookDao.getBook();
    }
}
