package com.lib.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lib.factory.RequestActionFactory;
import com.lib.process.ProcessRequest;

/**
 *
 * @author StuartFripp
 */
@WebServlet(name = "LibraryController", urlPatterns = {"/lib"})
public class LibraryController extends HttpServlet{

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException{
    String process = request.getParameter("pro");
    if(process!=null){
      ProcessRequest pr = RequestActionFactory.creaGIteProcess(process);
      if(pr!=null){
        pr.processTheRequest(request,response);
      }
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException{
    processRequest(request, response);
  }


  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException{
    processRequest(request, response);
  }

  @Override
  public String getServletInfo(){
    return "Short description";
  }// </editor-fold>

}
