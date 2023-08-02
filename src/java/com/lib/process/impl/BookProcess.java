package com.lib.process.impl;

import com.lib.bean.Author;
import com.lib.bean.Book;
import com.lib.process.ProcessRequest;
import com.lib.service.BookService;
import com.lib.service.impl.BookServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class BookProcess implements ProcessRequest {

    BasicDataSource dbManager = null;

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse) {
        ServletContext sc = request.getServletContext();
        if (sc != null) {
            dbManager = (BasicDataSource) sc.getAttribute("dbman");
        }
        if (dbManager == null) {
            return;
        }
        BookService bookService = new BookServiceImpl(dbManager);
        RequestDispatcher view = null;
        HttpSession session = request.getSession();
        List<Book> books;

        String action = request.getParameter("action");
        if (action != null) {
            switch (action.toLowerCase()) {
                
                case "add":
                    List<Author> authors = new ArrayList<>();
                    String[] authorIds = request.getParameterValues("authorId");
                    int aId;
                    for (String id : authorIds) {
                        aId = -1;
                        try {
                            aId = Integer.parseInt(id);
                            authors.add(new Author(aId));
                        } catch (NumberFormatException iox) {
                            System.out.println("Cannot convert author Id: " + iox.getMessage());
                        }
                    }
                    int catId = -1;
                    try {
                        catId = Integer.parseInt(request.getParameter("catId"));
                    } catch (NumberFormatException nfe) {
                    }
                    Book book = new Book(request.getParameter("isbn"),
                            catId,
                            request.getParameter("title"),
                            request.getParameter("pubDate"),
                            request.getParameter("rackNum"),
                            authors);
                    if (bookService.addBook(book)) {
                        System.out.println("Book added");
                        view = request.getRequestDispatcher("./login.jsp");
                    } else {
                        System.out.println("unsuccessful in adding the book");
                        view = request.getRequestDispatcher("./pages/create-account.jsp");
                    }
                    break;
                // ***************
                case "delete":
                    String isbn = "";
                    try {
                        isbn = request.getParameter("isbn");
                    } catch (NumberFormatException nfe) {
                    }
                    if (bookService.removeBook(isbn)) {
                       request.setAttribute("msg", "Book successfully deleted");
                        view = request.getRequestDispatcher("./abook.jsp");
                    } else {
                        System.out.println("unsuccessful in removing the BookItem");
                        view = request.getRequestDispatcher("./pages/create-account.jsp");
                    }
                    break;
                // ***************  
                case "update":
                    catId = -1;
                    try {
                        catId = Integer.parseInt(request.getParameter("catId"));
                    } catch (NumberFormatException nfe) {
                    }
//                    authors = new ArrayList<>();
//                    authorIds = request.getParameterValues("authorId");
//                    for (String id : authorIds) {
//                        aId = -1;
//                        try {
//                            aId = Integer.parseInt(id);
//                            authors.add(new Author(aId));
//                        } catch (NumberFormatException iox) {
//                            System.out.println("Cannot convert author Id: " + iox.getMessage());
//                        }
//                    }
                    
                    book = new Book(request.getParameter("isbn"),
                            catId,
                            request.getParameter("title"),
                            request.getParameter("pubDate"),
                            request.getParameter("rackNum"));
                    if (bookService.updateBook(book)) {
                        request.setAttribute("msg", "Book successfully updated");
                        view = request.getRequestDispatcher("./abook.jsp");
                    } else {
                        System.out.println("unsuccessful updating of the book");
                        view = request.getRequestDispatcher("./pages/create-account.jsp");
                    }
                    break;
                // ***************
                case "get":
                    String searchedWord = request.getParameter("bookId");
                    List<Book> bookSearchResult = bookService.getBook(searchedWord);
                    if (bookSearchResult.isEmpty()) {
                        System.out.println("No results found");
                        request.setAttribute("msg", "No results found");
                    } else {
                        System.out.println("Some results found");
                        request.setAttribute("msg", "Following results found");
                    }
                    request.setAttribute("bookSearchResult", bookSearchResult);
                    view = request.getRequestDispatcher("./pages/create-account.jsp");
                    break;

                case "getall":

                    books = bookService.getBook();
                    

                    if (books.isEmpty()) {
                        System.out.println("No results found");
                        request.setAttribute("msg", "No results found");
                    } else {
                        System.out.println("Some results found");
                        request.setAttribute("msg", "Following results found");

                    }
                    
                    view = request.getRequestDispatcher("./abook.jsp");
                    break;
                default:
                    System.out.println("Invalid action specified");
                    //  view = request.getRequestDispatcher("ERRORXXXX.jsp");
                    break;
            }
            books = bookService.getBook();
            session.setAttribute("book", books);
        } else {
            System.out.println("No action specified");
            //  view = request.getRequestDispatcher("ERRORXXXX.jsp");
        }
        try {
            if (view != null) {
                view.forward(request, reponse);
            }
        } catch (ServletException | IOException ex) {
        }
    }
    // ******************************************************************* 
}
