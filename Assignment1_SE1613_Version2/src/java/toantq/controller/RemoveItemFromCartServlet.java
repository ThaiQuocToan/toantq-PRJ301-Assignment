/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
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
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "RemoveItemFromCartServlet", urlPatterns = {"/RemoveItemFromCartServlet"})
public class RemoveItemFromCartServlet extends HttpServlet {

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

        String lastSearchItemValue = request.getParameter("txtSearchItem");
        String url = MyApplicationConstants.RemoveItemFromCartFeatures.LOGIN_PAGE;
        try {

            //1. Customer goes to his/her place 
            HttpSession session = request.getSession(false);
            // đang thấy giỏ hàng đang phía client mà session ở phía server nên session sẽ hết time
            if (session != null) {
                TblaccountDTO user = (TblaccountDTO) session.getAttribute("USER_ROLE");
                TblaccountDAO dao = new TblaccountDAO();
                boolean isUser = dao.checkLogin(user.getUsername(), user.getPassword());
                if (isUser) {
                    //2.Customers take his/her cart
                    CartObject cart = (CartObject) session.getAttribute("CART");
                    if (cart != null) {
                        //3. Customer gets all items
                        Map<String, Integer> items = cart.getItems();
                        if (items != null) {
                            //4. get all items that customer choosen
                            String[] products = request.getParameterValues("checkItem");
                            if (products != null) {
                                for (String product : products) {
                                    cart.removeItemFromCart(product);
                                }
                                session.setAttribute("CART", cart);
                                url = MyApplicationConstants.RemoveItemFromCartFeatures.VIEW_CART_PAGE
                                        + "?txtSearchItem=" + lastSearchItemValue;
                            }
                        } // end items has at least item.
                    } // end cart existed
                }
                //5. call view Cart again.
            }
        } catch (SQLException ex) {
            log("SQL Exception occurs in process at RemoveItemFromCartServlet", ex.getCause());
        } catch (NamingException ex) {
            log("Naming Exception occurs in process at RemoveItemFromCartServlet", ex.getCause());
        }finally {
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
