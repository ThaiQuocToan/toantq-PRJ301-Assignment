/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
import toantq.tblproduct.TblproductDAO;
import toantq.tblproduct.TblproductDTO;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "SearchItemServlet", urlPatterns = {"/SearchItemServlet"})
public class SearchItemServlet extends HttpServlet {
//    private final String LOGIN_PAGE = "login.html";
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
        
        //1. get servletContext 
        ServletContext context = this.getServletContext();
        //2. get properties
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String searchItem = request.getParameter("txtSearchItem");
        
        String url = properties.getProperty(
                MyApplicationConstants.SearchItemFeatures.LOGIN_PAGE);
        
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblaccountDTO user = (TblaccountDTO) session.getAttribute("USER_ROLE");
                TblaccountDAO dao = new TblaccountDAO();
                boolean isUser = dao.checkLogin(user.getUsername(), user.getPassword());
                if (isUser) {
                    //CALL DAO
                    TblproductDAO productDAO = new TblproductDAO();
                    productDAO.searchItems(searchItem);
                    
                    List<TblproductDTO> result = productDAO.getProducts();
                    request.setAttribute("RESULT_SEARCH_ITEM", result);
                    url = properties.getProperty(
                            MyApplicationConstants.SearchItemFeatures.SHOPPING_PAGE);
                }
            }
        } catch (SQLException ex) {
            log("SQL Exception occurs in process at SearchItemServlet", ex.getCause());
        } catch(NamingException ex) {
            log("Naming Exception occurs in process at SearchItemServlet", ex.getCause());
        } finally {
            RequestDispatcher rd= request.getRequestDispatcher(url);
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
