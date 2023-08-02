/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.dao;

import com.lib.bean.BookItem;
import java.util.List;


public interface BookItemDao {
    
    boolean removeBookItem(int bookitemId);
    boolean updateBookItem(int bookId, boolean onLoan);
    boolean addBookItem(BookItem bookitem);
    List<BookItem> getBookItems(boolean deleted);
}
