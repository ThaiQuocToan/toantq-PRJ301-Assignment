/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import toantq.tblproduct.TblProductAddError;
import toantq.tblproduct.TblproductDAO;
import toantq.tblproduct.TblproductDTO;

/**
 * Web application lifecycle listener.
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class AddItemToCartServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        request.removeAttribute("ERROR_ITEM_STATUS");
        request.removeAttribute("ERROR_ITEM_QUANTITY");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        //1. get ServletRequest
        ServletRequest request = sre.getServletRequest();
        //2. get servletContext
        ServletContext context = sre.getServletContext();
        //3 get list Items from form control 
        String[] listItem = request.getParameterValues("chooseItem");
        try {
            if (listItem != null) {
                // create list errors about status of items in the stock
                List<TblProductAddError> statusErrors = new ArrayList<>();
                // create list errors about quantity of items in the stock
                List<TblProductAddError> quanityErrors = new ArrayList<>();
                
                boolean foundStatusError = false;
                boolean foundQuantityError = false;
                
                TblproductDAO productDAO = new TblproductDAO();
                for (String idItem : listItem) {
                    TblproductDTO dto = productDAO.getProductsById(idItem);
                    if (dto.isStatus() == false) {
                        foundStatusError = true;
                        TblProductAddError itemError = new TblProductAddError();
                        itemError.setStatusItemError("The item " + dto.getName() 
                            + " does not sale at the current time." + "_" +dto.getIdItem());
                        statusErrors.add(itemError);
                    } else {
                        if (dto.getQuantity() == 0) {
                            foundQuantityError = true;
                            TblProductAddError itemError = new TblProductAddError();
                            itemError.setQuantityItemError("The item " + dto.getName() 
                                    + " is out of stock." + "_" + dto.getIdItem());
                            quanityErrors.add(itemError);
                        }
                    }
                }
                if (foundStatusError) {
                    request.setAttribute("ERROR_ITEM_STATUS", statusErrors);
                } else {
                    statusErrors = null;
                    request.setAttribute("ERROR_ITEM_STATUS", statusErrors);
                }
                
                if (foundQuantityError) {
                    request.setAttribute("ERROR_ITEM_QUANTITY", quanityErrors);
                } else {
                    quanityErrors = null;
                    request.setAttribute("ERROR_ITEM_QUANTITY", quanityErrors);
                }
            } //if list Items has at least one item
        } catch (SQLException ex) {
            context.log("SQL Exception occurs in process "
                    + "at AddItemToCartServletListener", ex.getCause());
        } catch (NamingException ex) {
            context.log("Naming Exception occurs in process "
                    + "at AddItemToCartServletListener", ex.getCause());
        }
    }
}
