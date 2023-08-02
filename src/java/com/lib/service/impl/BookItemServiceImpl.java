 
package com.lib.service.impl;

import com.lib.bean.BookItem;
import com.lib.dao.BookItemDao;
import com.lib.dao.impl.BookItemDaoImpl;

import com.lib.service.BookItemService;
import java.io.IOException;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


public class BookItemServiceImpl implements BookItemService{

     private BookItemDao bookitemDao;
   
    
    
    @Override
    public boolean removeBookItem(int bookitemId) {
        return bookitemDao.removeBookItem(bookitemId);
    }

    @Override
    public boolean updateBookItem(int bookId, boolean onLoan) {
     return bookitemDao.updateBookItem(bookId, onLoan);
    }

    @Override
    public boolean addBookItem(BookItem bookitem) {
        return bookitemDao.addBookItem(bookitem);
    }

    @Override
    public String getBookItem(int bookitemId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getBookItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse) {
        BasicDataSource dbManager = null;
        ServletContext sc = request.getServletContext();
        if (sc != null) {
            dbManager = (BasicDataSource) sc.getAttribute("dbman");
        }
        if (dbManager == null) {
            return;
        }
        bookitemDao = new BookItemDaoImpl(dbManager);
        String action = request.getParameter("action");
  
    String category = "";
    if(action != null){
      int bookitemId = 0;
      switch(action.toLowerCase()){
          
        case "add":
          bookitemId = 0;
          try{
            bookitemId = Integer.parseInt(request.getParameter("bookitemId"));
          }catch(NumberFormatException nfe){
          }
          if(addBookItem(bookitemId)){
            request.setAttribute("category", category);
            request.setAttribute("catId", bookitemId);
            request.setAttribute("msg", "The catagory has been added");
            RequestDispatcher view = request.getRequestDispatcher(".jsp");//////////////////
            try{
              view.forward(request, reponse);
            }catch(ServletException | IOException ex){
            }
          }
          break;
        // ***************
        case "update":
            bookitemId = 0;
          try{
            bookitemId = Integer.parseInt(request.getParameter("bookitemId"));
          }catch(NumberFormatException nfe){
          }
          if(updateBookItem(bookitemId, request.getParameter("bookitem"))){
          }
          break;
        // ***************
        case "delete":
          bookitemId = 0;
          try{
            bookitemId = Integer.parseInt(request.getParameter("catId"));
          }catch(NumberFormatException nfe){
          }
          if(removeBookItem(bookitemId)){
          }
          
          break;
        default:
      }
    }
  }
    
    
    
  
    private boolean addBookItem(int bookitemId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean updateBookItem(int bookitemId, String parameter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
