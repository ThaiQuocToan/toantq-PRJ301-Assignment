/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import toantq.cart.CartObject;
import toantq.tblproduct.TblProductCheckoutError;
import toantq.tblproduct.TblproductDAO;
import toantq.tblproduct.TblproductDTO;

/**
 * Web application lifecycle listener.
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class CheckoutServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        request.removeAttribute("ERROR_QUANTITY_ITEM_STOCK");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        //get ServletRequest
        ServletRequest request = sre.getServletRequest();
        //get servletContext
        ServletContext context = sre.getServletContext();
        //get request object
        HttpServletRequest requestObject = (HttpServletRequest) request;
        //goes to cart place
        HttpSession session = requestObject.getSession(false);
        
        String button = request.getParameter("btnAction");
        
        try {
            if ("Check out".equals(button)) {
                if (session != null) {
                    //get his/ her cart
                    CartObject cart = (CartObject) session.getAttribute("CART");
                    if (cart != null) {
                        //get items from his/ her cart
                        Map<String, Integer> items = cart.getItems();
                        if (items != null) {
                            TblproductDAO productDAO = new TblproductDAO();
                            boolean foundError = false;
                            //create list errors about quantity of items in the stock
                            List<TblProductCheckoutError> errors = new ArrayList<>();
                            for (String idItem : items.keySet()) {
                                TblproductDTO dto = productDAO.getProductsById(idItem);
                                //if quantity of bought items of customer greater than
                                  //quantity of items in the stock
                                if (dto.getQuantity() < items.get(idItem)) {
                                    foundError = true;
                                    TblProductCheckoutError error = new TblProductCheckoutError();
                                    error.setQuantityItemError("The item " + dto.getName()
                                            + " does not enough quantity. " + dto.getQuantity()
                                            + " courses left in the stock" + "_" + dto.getIdItem());
                                    //add error items in lisrt error
                                    errors.add(error);
                                }
                            }
                            if (foundError) {
                                request.setAttribute("ERROR_QUANTITY_ITEM_STOCK", errors);
                            } else {
                                errors = null;
                                request.setAttribute("ERROR_QUANTITY_ITEM_STOCK", errors);
                            }
                        } //if his/her cart has items
                    } //if his/her cart is existed
                } //if cart place is existed
            } //if user chooses checkout functions
        } catch (SQLException ex) {
            context.log("SQL Exception occurs in process at"
                    + " CheckoutServletListener", ex.getCause());
        } catch (NamingException ex) {
            context.log("Naming Exception occurs in process at"
                    + " CheckoutServletListener", ex.getCause());
        }
    }
}
