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
import toantq.cart.CartObject;
import toantq.tblaccount.TblaccountDAO;
import toantq.tblaccount.TblaccountDTO;
import toantq.tblproduct.TblproductUpdateError;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "UpdateItemQuantityServlet", urlPatterns = {"/UpdateItemQuantityServlet"})
public class UpdateItemQuantityServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String searchItemValue = request.getParameter("txtSearchItem");
        TblproductUpdateError errors = new TblproductUpdateError();
        boolean foundError = false;
        try {
            if (session != null) {
                TblaccountDTO user = (TblaccountDTO) session.getAttribute("USER_ROLE");
                TblaccountDAO dao = new TblaccountDAO();
                boolean isUser = dao.checkLogin(user.getUsername(), user.getPassword());
                if (isUser) {
                    String errorQuantity = (String) request.getAttribute("ERROR_QUANTITY_ITEM");
                    if (errorQuantity != null) {
                        foundError = true;
                        errors.setUpdateQuantityOverItemError(errorQuantity);
                        request.setAttribute("ERROR_QUANTITY_OVER_ITEM", errors);
                    } else {
                        boolean errorNumberFormat = (boolean) request.getAttribute("ERROR_NUMBER_FORMAT_EXCEPTION");
                        String idItem = (String) request.getAttribute("ID_ITEM_UPDATE_QUANTITY_ITEM");

                        if (errorNumberFormat == false) {
                            foundError = true;
                            errors.setUpdateQuantityItemError("The quantity of items must be a number." 
                                    + "_" + idItem);
                            request.setAttribute("ERROR_NUMBER_FORMAT", errors);
                        } else {
                            int quantity = (int) request.getAttribute("QUANTITY_ITEM_UPDATE");
                            CartObject cart = (CartObject) session.getAttribute("CART");
                            if (cart != null) {
                                cart.updateItemsQuantity(idItem, quantity);
                                session.setAttribute("CART", cart);
                            }
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            log("SQL Exception occurs in process at UpdateItemQuantityServlet", ex.getCause());
        } catch (NamingException ex) {
            log("Naming Exception occurs in process at UpdateItemQuantityServlet", ex.getCause());
        } finally {
            if (foundError) {
                String urlRewriting = properties.getProperty(
                        MyApplicationConstants.UpdateItemQuantityFeatures.VIEW_CART_PAGE)
                        + "?txtSearchItem=" + searchItemValue;
                RequestDispatcher rd = request.getRequestDispatcher(urlRewriting);
                rd.forward(request, response);
            } else {
                String urlRewriting = MyApplicationConstants.UpdateItemQuantityFeatures.VIEW_CART_PAGE
                        + "?txtSearchItem=" + searchItemValue;
                response.sendRedirect(urlRewriting);
            }
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
