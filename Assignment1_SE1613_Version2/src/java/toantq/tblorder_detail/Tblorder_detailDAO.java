/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblorder_detail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import toantq.tblorder.TblorderDTO;
import toantq.utils.DBHelper;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class Tblorder_detailDAO implements Serializable {

    public boolean addOrderDetail(Date dateCreateOrder,
            String idUser, Map<String, Integer> items)
            throws SQLException, NamingException {
        //check connection 
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblorderDTO dto = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                con.setAutoCommit(false);
                //create sql String creat order and order detail
                // 1. create Statement to process Creating order
                String sqlCreateOrder = "INSERT INTO tblOrder "
                        + "VALUES ('', ?, ?)";
                stm = con.prepareStatement(sqlCreateOrder);
                stm.setString(1, idUser);
                stm.setDate(2, dateCreateOrder);
                // 1.1 execute query
                int rowCreateOrder = stm.executeUpdate();
                
                // 2. create statement to process Getting information 
                String sqlGetOrderInformation = "SELECT TOP 1 idOrder, username, dateCreate "
                        + "FROM tblOrder "
                        + "ORDER BY idOrder DESC";
                stm = con.prepareStatement(sqlGetOrderInformation);
                // 2.1 execute query
                rs = stm.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("idOrder");
                    String userName = rs.getString("username");
                    Date date = rs.getDate("dateCreate");

                    dto = new TblorderDTO(id, userName, date);
                }
                
                // 3. create statement to process Creating Orderdetail  
                int rowCreateOrderDetail = 0;
                for (String idItem : items.keySet()) {
                    String sqlCreateOrderDetail = "INSERT INTO tblOrder_Detail (idOrder, idItem, quantity)"
                            + "VALUES (? , ? , ?)";
                    stm = con.prepareStatement(sqlCreateOrderDetail);
                    stm.setString(1, dto.getIdOrder());
                    stm.setString(2, idItem);
                    stm.setInt(3, items.get(idItem));
                    // 3.1 execute query
                    rowCreateOrderDetail = rowCreateOrderDetail + stm.executeUpdate();
                }
                con.commit();
                //4. process
                if (rowCreateOrder > 0 && rowCreateOrderDetail > 0) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
        return false;
    }

}
