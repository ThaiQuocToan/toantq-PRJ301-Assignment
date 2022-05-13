/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.listener;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import toantq.tblproduct.TblproductDAO;
import toantq.tblproduct.TblproductDTO;

/**
 * Web application lifecycle listener.
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class UpdateItemQuantityServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        request.removeAttribute("ERROR_NUMBER_FORMAT_EXCEPTION");
        request.removeAttribute("QUANTITY_ITEM_UPDATE");
        request.removeAttribute("ID_ITEM_UPDATE_QUANTITY_ITEM");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // muốn lấy parameter khi user gửi request thì 
        // phải lấy được request(ServletRequest) từ ServletRequestEvent bằng getServletRequest() Method
        ServletRequest request = sre.getServletRequest();
        ServletContext context = sre.getServletContext();
        String button = request.getParameter("btnAction");
        if (button == null) {
            // lấy danh sách tên các tham số có tên bắt đầu bằng btnAction_
            Enumeration param  =  request.getParameterNames();
            boolean next = true;
            while (param.hasMoreElements() && next) {
                String pramName = (String) param.nextElement();
                if (pramName.startsWith("btnAction_")) {
                    next = false;
                    // tách chuỗi trước và sau _
                    StringTokenizer stk = new StringTokenizer(pramName, "_");
                    stk.nextToken(); //phần trước _
                    String suffix = stk.nextToken(); // phần sau _
                    String idItem = request.getParameter("txtTitle_" + suffix);
                    String quantity = request.getParameter("txtQuantity_" + suffix);
                    int checkQuantity = -1;
                    try {
                        checkQuantity = Integer.parseInt(quantity);
                        TblproductDAO dao = new TblproductDAO();
                        TblproductDTO dto = dao.getProductsById(idItem);
                        if (dto.getQuantity() < checkQuantity) {
                            request.setAttribute("ERROR_QUANTITY_ITEM", 
                                    "The item " + dto.getName() + " has "
                                            + dto.getQuantity() + " left in stock." + "_" + dto.getIdItem());
                        }
                    } catch (NumberFormatException ex) {
                        context.log("Number Format Exception occurs in process"
                                + " at UpdateItemQuantityServletListener", 
                                ex.getCause());
                        checkQuantity = -1;
                    } catch (SQLException ex) {
                        context.log("SQL Exception occurs in process"
                                + " at UpdateItemQuantityServletListener" , ex.getCause());
                    } catch (NamingException ex) {
                        context.log("Naming Exception occurs in process"
                                + " at UpdateItemQuantityServletListener" , ex.getCause());
                    }
                    
                    if (checkQuantity < 0) {
                        request.setAttribute("ID_ITEM_UPDATE_QUANTITY_ITEM", idItem);
                        request.setAttribute("ERROR_NUMBER_FORMAT_EXCEPTION", false);
                    } else {
                        request.setAttribute("QUANTITY_ITEM_UPDATE", checkQuantity);
                        request.setAttribute("ID_ITEM_UPDATE_QUANTITY_ITEM", idItem);
                        request.setAttribute("ERROR_NUMBER_FORMAT_EXCEPTION", true);
                    }
                    
                }
            }
        }// end if button is null. button != null is passed to DispatchController
        
    }
}
