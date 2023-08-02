package com.lib.process.impl;

import com.lib.bean.Author;
import com.lib.bean.Category;
import com.lib.process.ProcessRequest;
import com.lib.service.AuthorService;
import com.lib.service.impl.AuthorServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class AuthorProcess implements ProcessRequest {

    private BasicDataSource dbManager;

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse) {
        ServletContext sc = request.getServletContext();
        if (sc != null) {
            dbManager = (BasicDataSource) sc.getAttribute("dbman");
        }
        if (dbManager == null) {
            return;
        }
        AuthorService authorService = new AuthorServiceImpl(dbManager);
        RequestDispatcher view = null;
        HttpSession session = request.getSession();
        List<Author> authors;
        int deleted = 0;
        
        String action = request.getParameter("action");
        int authId;
        if (action != null) {
            switch (action.toLowerCase()) {
                case "add":
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    if (name == null || surname == null) {
                        System.out.println("Invalid names");
                        view = request.getRequestDispatcher("./pages/errorpage.jsp");
                        break;
                    }
                    if (authorService.addAuthor(new Author(name, surname))) {
                        request.setAttribute("msg", "Succefully Added");
                        view = request.getRequestDispatcher("./adauthor.jsp");
                    } else {
                        System.out.println("Author NOT added");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                case "update":
                    authId = Integer.parseInt(request.getParameter("authId"));
                    name = request.getParameter("name");
                    surname = request.getParameter("surname");
                    if (name == null || surname == null) {
                        System.out.println("Invalid names");
                        view = request.getRequestDispatcher("./pages/errorpage.jsp");
                        break;
                    }
                    if (authorService.updateAuthor(new Author(authId, name, surname))) {
                       request.setAttribute("msg", "Succefully updated");
                        view = request.getRequestDispatcher("./adauthor.jsp");
                    } else {
                        System.out.println("Category NOT successfully updated");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                case "delete":
                    authId = Integer.parseInt(request.getParameter("authId"));
                    
                    if (authorService.removeAuthor(authId)) {
                        request.setAttribute("msg", "Succefully deleted");
                        view = request.getRequestDispatcher("./adauthor.jsp");
                    } else {
                        System.out.println("Category NOT successfully removed");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                case "get":
                    authId = -1;
                    try {
                        authId = Integer.parseInt(request.getParameter("authId"));
                    } catch (NumberFormatException nfe) {
                        System.out.println("Error: " + nfe.getMessage());
                    }
                    Author author = authorService.getAuthor(authId);
                    if (author != null) {
//            request.setAttribute("msg", "The author's description is:");
//            request.setAttribute("author", author);
                        view = request.getRequestDispatcher("XXX.jsp");
                    } else {
//            request.setAttribute("msg", "The author could not be found");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                case "getall":
                    
                    
                    try {
                        deleted = Integer.parseInt(request.getParameter("deleted"));
                    } catch (NumberFormatException nfe) {
                    }
                    authors = authorService.getAuthors((deleted == 0));
                    if (!authors.isEmpty()) {
                        session.setAttribute("authors", authors);
                        view = request.getRequestDispatcher("./adauthor.jsp");
                    } else {
                        request.setAttribute("msg", "No authors could not be found");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                default:
                    request.setAttribute("msg", "Invalid action requested");
                    // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    break;
            }
            authors = authorService.getAuthors((deleted == 0));
            session.setAttribute("authors", authors);
            try {
                if (view != null) {
                    view.forward(request, reponse);
                }
            } catch (ServletException | IOException ex) {
            }
        }
    }
}
