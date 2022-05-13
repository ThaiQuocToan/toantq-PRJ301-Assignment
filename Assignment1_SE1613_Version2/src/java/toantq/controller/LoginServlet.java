/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import toantq.tblaccount.TblaccountDAO;
import toantq.tblaccount.TblaccountDTO;
import toantq.tblaccount.TblaccountLoginError;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
//    private final String SEARCH_PAGE = "search.jsp";
//    private final String LOGIN_PAGE = "login.jsp";
//    private final String SHOPPING_PAGE = "shopping.jsp";
//    private final String ERROR_PAGE = "login.html";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //1. get servletContext 
        ServletContext context = this.getServletContext();
        //2. get properties
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String rememberMe = request.getParameter("RememberMe");
        String url;
        TblaccountLoginError errors = new TblaccountLoginError();
        try {
            //CALL DAO
            TblaccountDAO dao = new TblaccountDAO();
            boolean result = dao.checkLogin(username, password);
            if (result) {
                HttpSession session = request.getSession();
                TblaccountDTO role = (TblaccountDTO) dao.getAccount(username);
                if (role.isRole()) {
                    url = MyApplicationConstants.LoginFeatures.SEARCH_PAGE;
                    session.setAttribute("ADMIN_ROLE", role);
                } //end if role is admin 
                else {
                    session.setAttribute("USER_ROLE", role);
                    url = MyApplicationConstants.LoginFeatures.SHOPPING_PAGE;
                } //end if role is user
                if (rememberMe != null) {
                    Cookie cookie = new Cookie(username, password);
                    cookie.setMaxAge(60*1);
                    response.addCookie(cookie);
                } //end if login has remember username and password
                response.sendRedirect(url);
            } //end if login successful
            else  {
                url = properties.getProperty(
                        MyApplicationConstants.LoginFeatures.ERROR_PAGE);
                errors.setLoginError("Username or Password is invalid.");
                request.setAttribute("ERROR_LOGIN", errors);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }// end if login fail
        } catch(SQLException ex) {
            log("SQL Exception occurs in process at LoginServlet", ex.getCause());
        } catch(NamingException ex){
            log("Naming Exception occurs in process at LoginServlet", ex.getCause());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
