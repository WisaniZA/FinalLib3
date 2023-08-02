package com.lib.process.impl;

import com.lib.bean.Role;
import com.lib.process.ProcessRequest;
import com.lib.service.RoleService;
import com.lib.service.impl.RoleServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class RoleProcess implements ProcessRequest{
  private BasicDataSource dbManager;

  @Override
  public void processTheRequest(HttpServletRequest request, HttpServletResponse reponse){
    ServletContext sc = request.getServletContext();
    if(sc != null){
      dbManager = (BasicDataSource) sc.getAttribute("dbman");
    }
    if(dbManager == null){
      return;
    }
    RoleService roleService = new RoleServiceImpl(dbManager);
    RequestDispatcher view = null;
    String action = request.getParameter("action");
    if(action != null){
      switch(action.toLowerCase()){
        case "add":
          int role_id = -1;
          try{
            role_id = Integer.parseInt(request.getParameter("role_id"));
          }catch(NumberFormatException nfe){
          }
          String roleDes = request.getParameter("role");
          if(roleDes == null || roleDes.isEmpty()){
            System.out.println("Invalid roleDes Parameter");
            view = request.getRequestDispatcher("Error.jsp");////////////////////
            break;
          }
          Role role = new Role(role_id, roleDes.toLowerCase());
          if(roleService.addRole(role)){
            System.out.println("Role added");
            request.setAttribute("role", role);
            request.setAttribute("msg", "The Role has been added");
            view = request.getRequestDispatcher(".jsp");//////////////////
          }else{
            System.out.println("Unable to add a Role");
            view = request.getRequestDispatcher("Error.jsp");////////////////////
          }
          break;
        // ***************
        case "update":
          role_id = -1;
          try{
            role_id = Integer.parseInt(request.getParameter("role_id"));
          }catch(NumberFormatException nfe){
          }
          roleDes = request.getParameter("role");
          if(roleDes == null || roleDes.isEmpty()){
            System.out.println("Invalid roleDes Parameter");
            view = request.getRequestDispatcher("Error.jsp");////////////////////
            break;
          }
          role = new Role(role_id, roleDes.toLowerCase());
          if(roleService.updateRole(role)){
            System.out.println("Role updated");
            request.setAttribute("role", role);
            request.setAttribute("msg", "The Role has been updated");
            view = request.getRequestDispatcher(".jsp");//////////////////
          }else{
            System.out.println("failed to update");
            view = request.getRequestDispatcher("Error.jsp");////////////////////
          }
          break;
        // ***************
        case "delete":
          role_id = -1;
          try{
            role_id = Integer.parseInt(request.getParameter("role_id"));
          }catch(NumberFormatException nfe){
          }
          if(roleService.removeRole(role_id)){
            System.out.println("Role removed");
            request.setAttribute("msg", "The Role has been removed");
            view = request.getRequestDispatcher(".jsp");//////////////////
          }else{
            System.out.println("Unable to remove a Role");
            view = request.getRequestDispatcher("Error.jsp");////////////////////
          }
          break;
        // ***************
        case "get":
          role_id = -1;
          try{
            role_id = Integer.parseInt(request.getParameter("role_id"));
          }catch(NumberFormatException nfe){
            System.out.println("Error: " + nfe.getMessage());
          }
          role = roleService.getRole(role_id, true);
          if(role != null){
            request.setAttribute("msg", "The role's description is:");
            request.setAttribute("role", role);
            view = request.getRequestDispatcher("XXX.jsp");
          }else{
            request.setAttribute("msg", "The role could not be found");
            // view = request.getRequestDispatcher("./pages/errorpage.jsp");
          }
          break;
        // ***************
        case "getall":
          List<Role> roles = roleService.getRoles(true);
          if( ! roles.isEmpty()){
            request.setAttribute("msg", "The Roles are:");
            request.setAttribute("roles", roles);
            view = request.getRequestDispatcher("XXX.jsp");
          }else{
            request.setAttribute("msg", "No roles could not be found");
            // view = request.getRequestDispatcher("./pages/errorpage.jsp");
          }
          break;
        // ***************
        default:
          request.setAttribute("msg", "Invalid action requested");
          // view = request.getRequestDispatcher("./pages/errorpage.jsp");
          break;
      }
      try{
        if(view != null){
          view.forward(request, reponse);
        }
      }catch(ServletException | IOException ex){
      }
    }
  }
}
