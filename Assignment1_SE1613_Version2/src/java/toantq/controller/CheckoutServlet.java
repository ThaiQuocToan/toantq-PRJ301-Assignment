/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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
import toantq.tblorder_detail.Tblorder_detailDAO;
import toantq.tblproduct.TblProductCheckoutError;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {
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

        //1.get servletContext
        ServletContext context = this.getServletContext();
        //2. get properties
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url = properties.getProperty(
                MyApplicationConstants.CheckoutFeatures.LOGIN_PAGE);

        try {
            //1. Customer goes to cart place
            HttpSession session = request.getSession(false);
            if (session != null) {
                
                TblaccountDTO user = (TblaccountDTO) session.getAttribute("USER_ROLE");
                TblaccountDAO dao = new TblaccountDAO();
                boolean isUser = dao.checkLogin(user.getUsername(), user.getPassword());
                
                if (isUser) {
                    //get list error about quantity of items in the stock 
                    List<TblProductCheckoutError> errors = (List<TblProductCheckoutError>)
                            request.getAttribute("ERROR_QUANTITY_ITEM_STOCK");
                    if (errors != null) {
                        url = properties.getProperty(MyApplicationConstants.CheckoutFeatures.VIEW_CART_PAGE);
                        RequestDispatcher rd = request.getRequestDispatcher(url);
                        rd.forward(request, response);
                    } else {
                        //2. Customer takes his/her cart
                        CartObject cart = (CartObject) session.getAttribute("CART");
                        if (cart != null) {
                            // get items from his/her cart
                            Map<String, Integer> items = cart.getItems();
                            if (items != null) {
                                //1. get today which day is customer buys items
                                LocalDate today = LocalDate.now();
                                // convert today to the following format yyyy-MM-dd to add into DB
                                DateTimeFormatter dayFormat
                                        = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                // convert String to date type
                                Date date = Date.valueOf(today.format(dayFormat));
                                //CALL DAO + CALL method to addOrder
                                Tblorder_detailDAO orderDetailDAO = new Tblorder_detailDAO();
                                boolean isResult = orderDetailDAO.addOrderDetail(
                                                    date, user.getUsername(), items);
                                if (isResult) {
                                    session.removeAttribute("CART");
                                    url = MyApplicationConstants.CheckoutFeatures.SHOPPING_PAGE;
                                    response.sendRedirect(url);
                                } else {
                                    url = properties.getProperty(MyApplicationConstants.CheckoutFeatures.VIEW_CART_PAGE);
                                    RequestDispatcher rd = request.getRequestDispatcher(url);
                                    rd.forward(request, response);
                                }
                            }
                        }
                    } // if none errors
                } //if user is user role, then they must be use this function
                else {
                    response.sendRedirect(url);
                } //if user is admin role, then they must be back to login page
            } //if cart is existed.
            else {
                response.sendRedirect(url);
            } // if cart is not existed
        } catch (SQLException ex) {
            log("SQL Exception occurs in process at CheckoutServlet", ex.getCause());
        } catch (NamingException ex) {
            log("Naming Exception occurs in process at CheckoutServlet", ex.getCause());
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
