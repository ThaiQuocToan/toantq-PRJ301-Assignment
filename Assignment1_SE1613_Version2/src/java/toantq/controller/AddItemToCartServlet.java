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
import javax.servlet.jsp.PageContext;
import toantq.cart.CartObject;
import toantq.tblaccount.TblaccountDAO;
import toantq.tblaccount.TblaccountDTO;
import toantq.tblproduct.TblProductAddError;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCartServlet"})
public class AddItemToCartServlet extends HttpServlet {
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
        String searchValue = request.getParameter("txtSearchItem");
        
        String url = MyApplicationConstants.AddItemToCartFeatures.LOGIN_PAGE;

        try {
            HttpSession session = request.getSession(false);
            
            if (session != null) {
                TblaccountDTO user = (TblaccountDTO) session.getAttribute("USER_ROLE");
                TblaccountDAO dao = new TblaccountDAO();
                boolean isUser = dao.checkLogin(user.getUsername(), user.getPassword());
                if (isUser) {
                    boolean foundErrors = false;
                    // get id item form control input 
                    String[] idItems = request.getParameterValues("chooseItem");
                    // create list error about status of items in the stock
                    List<TblProductAddError> statusErrors = (List<TblProductAddError>)
                            request.getAttribute("ERROR_ITEM_STATUS");
                    // create list error about quantity of items in the stock
                    List<TblProductAddError> quantityErrors = (List<TblProductAddError>)
                            request.getAttribute("ERROR_ITEM_QUANTITY");
                    
                    url = MyApplicationConstants.AddItemToCartFeatures.SEARCH_ITEM_CONTROLLER
                            + "?txtSearchItem=" + searchValue;
                    
                    if (statusErrors != null) {
                        foundErrors = true;
                    } // if error about status of items is existed
                    if (quantityErrors != null)  {
                        foundErrors = true;
                    }// if error about quantity of items is existed
                    
                    if (foundErrors) {
                        url = properties.getProperty(
                            MyApplicationConstants.AddItemToCartFeatures.SEARCH_ITEM_CONTROLLER)
                            + "?txtSearchItem=" + searchValue;
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        if (idItems != null) {
                            //2. customer takes his/her cart
                            CartObject cart = (CartObject) session.getAttribute("CART");
                            if (cart == null) {
                                cart = new CartObject();
                            }
                            //3.customer drops item into cart
                            for (String idItem : idItems) {
                                cart.addItemtoCart(idItem);
                            }
                            session.setAttribute("CART", cart);
                        }
                        response.sendRedirect(url);
                    }
                } // if user is user role, then they must be use this function
                else {
                    response.sendRedirect(url);
                }// end if user is admin role, then they will be back to login page
                
            } //end if session is not existed
        } catch (SQLException ex) {
            log("SQL Exception occurs in process at AddItemToCartServlet", ex.getCause());
        } catch (NamingException ex) {
            log("Naimg Exception occurs in process at AddItemToCartServlet", ex.getCause());
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
