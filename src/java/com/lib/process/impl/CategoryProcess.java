package com.lib.process.impl;

import com.lib.bean.Book;
import com.lib.bean.Category;
import com.lib.process.ProcessRequest;
import com.lib.service.BookService;
import com.lib.service.CategoryService;
import com.lib.service.impl.BookServiceImpl;
import com.lib.service.impl.CategoryServiceImpl;
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

public class CategoryProcess implements ProcessRequest {

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
        CategoryService categoryService = new CategoryServiceImpl(dbManager);
        BookService bookService = new BookServiceImpl(dbManager);
        RequestDispatcher view = null;
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        List<Category> categories;

        int catId;
        if (action != null) {

            switch (action.toLowerCase()) {
                case "add":
                    String genre = request.getParameter("genre");
                    if (genre == null) {
                        view = request.getRequestDispatcher("./acategory.jsp");
                        break;
                    }
                    ;
                    if (categoryService.addCategory(new Category(genre.toLowerCase()))) {
                        request.setAttribute("msg", "Succefully Added");
                        view = request.getRequestDispatcher("./acategory.jsp");
                    } else {
                        System.out.println("not added");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                case "update":

                    catId = Integer.parseInt(request.getParameter("catId"));
                    genre = request.getParameter("genre");
                    if ((genre == null) || genre.isEmpty() || catId < 0) {
                        System.out.println("Genre Error");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    if (categoryService.updateCategory(new Category(catId, genre))) {
                        request.setAttribute("msg", "Category successfully updated");
                        view = request.getRequestDispatcher("./acategory.jsp");
                    } else {
                        System.out.println("Category NOT successfully updated");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                case "delete":
                    catId = Integer.parseInt(request.getParameter("catId"));
                    if (categoryService.removeCategory(catId)) {
                        request.setAttribute("msg", "Category successfully removed");
                        view = request.getRequestDispatcher("./acategory.jsp");
                    } else {
                        System.out.println("Category NOT successfully removed");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                case "get":
                    catId = Integer.parseInt(request.getParameter("catId"));

                    Category category = categoryService.getCategory(catId);
                    if (category != null) {
                        request.setAttribute("list", "The category description is:");
                        request.setAttribute("category", category);
                        view = request.getRequestDispatcher("XXX.jsp");
                    } else {
                        request.setAttribute("msg", "The category could not be found");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************

                case "getall":

                    List<Integer> cartItems = new ArrayList<>();
                    session.setAttribute("cartItems", cartItems);
                    String searchedWord = request.getParameter("searchedWord");
                    List<Book> bookSearchResult = bookService.getBook(searchedWord);
                    categories = categoryService.getCategories();
                    if (!categories.isEmpty()) {
                        session.setAttribute("categories", categories);
                        session.setAttribute("books", bookSearchResult);
                        if (session.getAttribute("isMember") == null) {
                            view = request.getRequestDispatcher("acategory.jsp");
                        } else {
                            view = request.getRequestDispatcher("mshop.jsp");
                        }

                    } else {
                        request.setAttribute("msg", "No categories could not be found");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                // ***************
                default:
                    request.setAttribute("msg", "Invalid action requested");
                    // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    break;
            }
            categories = categoryService.getCategories();
            session.setAttribute("categories", categories);

            try {
                if (view != null) {
                    view.forward(request, reponse);
                }
            } catch (ServletException | IOException ex) {
            }
        }
    }
}
