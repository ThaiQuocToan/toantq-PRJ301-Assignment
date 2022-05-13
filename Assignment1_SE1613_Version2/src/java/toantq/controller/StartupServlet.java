/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import toantq.tblaccount.TblaccountDAO;
import toantq.tblaccount.TblaccountDTO;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {
//    private final String LOGIN_PAGE = "login.html";
//    private final String SEARCH_PAGE = "search.jsp";
//    private final String SHOPPING_PAGE = "shopping.jsp";
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
                
        String url = MyApplicationConstants.StartupFeatures.LOGIN_PAGE;
        
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Cookie lastCookie = cookies[cookies.length - 1];
                
                if (lastCookie.getName().equals("JSESSIONID") 
                        && cookies.length >= 2) {
                    lastCookie = cookies[cookies.length - 2];
                }
                String username = lastCookie.getName();
                String password = lastCookie.getValue();
                TblaccountDAO dao = new TblaccountDAO();
                boolean result = dao.checkLogin(username, password);
                if(result) {
                    HttpSession session = request.getSession();
                    TblaccountDTO role = dao.getAccount(username);
                    if (role.isRole()) {
                        url = MyApplicationConstants.StartupFeatures.SEARCH_PAGE;
                        session.setAttribute("ADMIN_ROLE", role);
                    } //end if role is admin
                    else {
                        url = MyApplicationConstants.StartupFeatures.SHOPPING_PAGE;
                        session.setAttribute("USER_ROLE", role);
                    } // end if role is user
                }
            }
        } catch(SQLException ex) {
            log("SQL Exception occurs in process at StartupServlet", ex.getCause());
        } catch(NamingException ex) {
            log("Naming Exception occurs in process at StartupServlet", ex.getCause());
        } finally {
            response.sendRedirect(url);
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
