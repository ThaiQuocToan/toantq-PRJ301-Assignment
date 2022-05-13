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
import toantq.tblaccount.TblaccountCreateError;
import toantq.tblaccount.TblaccountDAO;
import toantq.tblaccount.TblaccountDTO;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
//    private final String REGISTER_PAGE = "register.jsp";
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
        String confirmPassword = request.getParameter("txtConfirmPassword");
        String fullName = request.getParameter("txtFullname");
        
        String url = properties.getProperty(MyApplicationConstants.RegisterFeatures.ERROR_PAGE);
        TblaccountCreateError errors = new TblaccountCreateError();
        boolean foundError = false;
        try {
            if (username.trim().length() < 2 || username.trim().length() > 20) {
                foundError = true;
                errors.setUsernameLengthError("Username is required with 6 to 20 characters");
            }
            if (password.trim().length() < 2 || password.trim().length() > 20) {
                foundError = true;
                errors.setPasswordLengthError("Password is required with 6 to 20 characters");
            } else if (password.trim().equals(confirmPassword.trim()) == false) {
                foundError = true;
                errors.setConfirmPasswordError("Confirm Password is not matched with your password");
            } 
            if (fullName.trim().length() < 2 || fullName.trim().length() > 50) {
                foundError = true;
                errors.setFullNameLengthError("Full Name is required with 6 to 50 characters");
            }
            if (foundError) {
                request.setAttribute("ERRORS", errors);
            } else {
                //CALL DAO
                TblaccountDAO dao = new TblaccountDAO();
                TblaccountDTO newAccount = new TblaccountDTO(username, password, fullName, false);
                boolean result = dao.registerAccount(newAccount);
                if (result) {
                    url = properties.getProperty(
                            MyApplicationConstants.RegisterFeatures.LOGIN_PAGE);
                }
            }

        } catch(SQLException ex) {
            log("SQL Exception occurs in process at RegisterServlet", ex.getCause());
            String msg = ex.getMessage();
            if (msg.contains("duplicate")) {
                errors.setUsernameExistedError("Username is existed");
                request.setAttribute("ERRORS", errors);
            }
        } catch(NamingException ex) {
            log("Naming Exception occurs in process at RegisterServlet", ex.getCause());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
