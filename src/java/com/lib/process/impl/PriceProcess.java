package com.lib.process.impl;

import com.lib.bean.Price;
import com.lib.process.ProcessRequest;
import com.lib.service.PriceService;
import com.lib.service.impl.PriceServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class PriceProcess implements ProcessRequest {

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
        PriceService priceService = new PriceServiceImpl(dbManager);
        RequestDispatcher view = null;
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        List<Price> prices;
        double amount;
        String priceDesc;

        int priceId;
        if (action != null) {

            switch (action.toLowerCase()) {
                case "update":

                    priceId = Integer.parseInt(request.getParameter("priceId"));
                    amount = Double.parseDouble(request.getParameter("amount"));
                    priceDesc = request.getParameter("priceDesc");

                    if (priceService.updatePrice(new Price(priceId, priceDesc, amount))) {
                        request.setAttribute("msg", "Amount successfully updated");
                        view = request.getRequestDispatcher("./aprice.jsp");
                    } else {
                        System.out.println("Category NOT successfully updated");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;

                case "get":
                    priceId = Integer.parseInt(request.getParameter("priceId"));

                    double price = priceService.getPrice(priceId);
                    if (price != 0) {
                        //request.setAttribute("list", "The category description is:");
                        session.setAttribute("price", price);
                        //view = request.getRequestDispatcher("XXX.jsp");
                    } else {
                        request.setAttribute("msg", "The category could not be found");
                        // view = request.getRequestDispatcher("./pages/errorpage.jsp");
                    }
                    break;
                case "getall":

                    prices = priceService.getPrices();
                    if (!prices.isEmpty()) {
                        session.setAttribute("prices", prices);
                            view = request.getRequestDispatcher("./aprice.jsp");
           
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
            prices = priceService.getPrices();
            session.setAttribute("prices", prices);
            try {
                if (view != null) {
                    view.forward(request, reponse);
                }
            } catch (ServletException | IOException ex) {
            }
        }
    }
}
