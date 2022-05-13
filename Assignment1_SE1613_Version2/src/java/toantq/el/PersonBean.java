/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.el;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import toantq.cart.CartObject;
import toantq.tblproduct.TblproductDAO;
import toantq.tblproduct.TblproductDTO;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class PersonBean {

    public static List<TblproductDTO> getCartObject(Object cartObject, ServletContext context) {
        try {
            if (cartObject != null) {
                CartObject cart = (CartObject) cartObject;
                Map<String, Integer> items = cart.getItems();
                if (items != null) {
                    List<TblproductDTO> listItems = new ArrayList<>();
                    TblproductDAO dao = new TblproductDAO();
                    for (String idItems : items.keySet()) {
                        TblproductDTO dto = dao.getProductsById(idItems);
                        dto.setQuantity(items.get(idItems));
                        listItems.add(dto);
                    }
                    return listItems;
                }
            }
        } catch (SQLException ex) {
            context.log("SQL Exception occurs in process at PersonBean", ex.getCause());
        } catch (NamingException ex) {
            context.log("Naming Exception occurs in process at PersonBean", ex.getCause());
        }

        return null;
    }
    
    public static String getErrorQuantityOfEachItem (String idItem, String errorQuantity) {
        if (errorQuantity != null) {
            int lastIndex = errorQuantity.indexOf("_");
            String errorItemID = errorQuantity.substring(lastIndex + 1);
            String error = errorQuantity.substring(0, lastIndex);
            if (errorItemID.equals(idItem)) {
               return error;
            }
        }
        return null;
    }
}
