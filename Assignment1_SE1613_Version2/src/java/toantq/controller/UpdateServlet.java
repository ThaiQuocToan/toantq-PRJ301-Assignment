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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import toantq.tblaccount.TblaccountDAO;
import toantq.tblaccount.TblaccountDTO;
import toantq.tblaccount.TblaccountUpdateError;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {
//    private final String LOGIN_PAGE = "login.html";

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
        String role = request.getParameter("txtIsAdmin");
        boolean isAdmin = false;
        if (role != null) {
            isAdmin = true;
        }
        String lastSearchValue = request.getParameter("lastSearchValue");
        String url = properties.getProperty(
                MyApplicationConstants.UpdateFeatures.LOGIN_PAGE);

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblaccountDTO admin = (TblaccountDTO) session.getAttribute("ADMIN_ROLE");
                boolean foundError = false;
                TblaccountDAO dao = new TblaccountDAO();
                TblaccountUpdateError errors = new TblaccountUpdateError();
                if (password.trim().length() < 2 || password.trim().length() > 20) {
                    foundError = true;
                    errors.setPasswordLengthError("Password is required 2-20 characters." + "_" + username);
                }
                if (foundError) {
                    url = properties.getProperty(MyApplicationConstants.UpdateFeatures.SEARCH_CONTROLLER)
                            + "?txtSearchValue=" + lastSearchValue;
                    request.setAttribute("ERROR_UPDATE_PASSWORD", errors);
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {

                    boolean adminRole = dao.checkLogin(admin.getUsername(), admin.getPassword());
                    if (adminRole) {
                        //CALL DAO
                        boolean result = dao.updateAccount(username, password, isAdmin);
                        if (result) {
                            url = MyApplicationConstants.UpdateFeatures.SEARCH_CONTROLLER
                                    + "?txtSearchValue=" + lastSearchValue;
                        }
                        response.sendRedirect(url);
                    }// end if role is admin
                }
            }// end if session also exist
            else {
                response.sendRedirect(url);
            }
        } catch (SQLException ex) {
            log("SQL Exception occurs in process at UpdateServlet", ex.getCause());
        } catch (NamingException ex) {
            log("Naming Exception occurs in process at UpdateServlet", ex.getCause());
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
