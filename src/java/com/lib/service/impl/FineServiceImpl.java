
package com.lib.service.impl;

import com.lib.bean.Fine;
import com.lib.dao.FineDao;
import com.lib.dao.impl.FineDaoImpl;
import com.lib.service.FineService;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class FineServiceImpl implements FineService  {

    private FineDao finedao;
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
     String action = request.getParameter("action");
        if (action != null) {
         
            switch (action.toLowerCase()) {
                case "add":
                {
                    int fineId = Integer.parseInt(request.getParameter("fineId"));
                    String fineAmount = request.getParameter("fineAmount");
                    int loanId = Integer.parseInt(request.getParameter("loanId"));
                   
                    Fine f = new Fine(fineId, Double.parseDouble(fineAmount),loanId,1);
                    finedao = new FineDaoImpl(dbManager);
                      if (addFine( f)) {
                        System.out.println("Added");
                    } else {
                        System.out.println("Not Added");
                    }
                    
                }
                case "delete":
                {
                    finedao = new FineDaoImpl(dbManager);
                    if (removeFine(Integer.parseInt(request.getParameter("fineId")))) {
                        System.out.println("Deleted");
                    } else {
                        System.out.println("Not deleted");
                    }
                    break;
                }
                
                case "update":
                {
                     finedao = new FineDaoImpl(dbManager);
                     int fineId = Integer.parseInt(request.getParameter("fine_id"));
                    if (updateFine(fineId)) {
                        System.out.println("updated");
                    } else {
                        System.out.println("Not updated");
                    }
                    break;
                }
                default:
                }
    }    }

 


    private boolean addFine(Fine f) {
        return finedao.addFine(f);
    }

    private boolean removeFine(int parseInt) {
return finedao.removeFine(parseInt);    }
    @Override
    public String getFine(int fineId) {
     return finedao.getFine(fineId);    }

    @Override
    public boolean AddFine(Fine fine) {
       return finedao.addFine(fine);
    }

    @Override
    public boolean updateFine(int fineId) {
       return finedao.updateFine(fineId);
     }
}