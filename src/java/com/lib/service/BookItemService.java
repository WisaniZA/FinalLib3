/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.service;

import com.lib.bean.BookItem;
import com.lib.process.ProcessRequest;
import java.util.List;

/**
 *
 * @author TRAIN 79
 */
public interface BookItemService extends ProcessRequest{
    boolean removeBookItem(int bookitemId);
    boolean updateBookItem(int bookId, boolean onLoan);
    boolean addBookItem(BookItem bookitem);
    String getBookItem(int bookitemId);
    List<String> getBookItems();
}
