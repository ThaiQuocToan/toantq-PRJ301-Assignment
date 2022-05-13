/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblproduct;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import toantq.utils.DBHelper;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblproductDAO implements Serializable{
    private List<TblproductDTO> products;

    public List<TblproductDTO> getProducts() {
        return products;
    }
    
    
    public void searchItems(String itemName) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //1. check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql string
                String sql = "SELECT idItem, name, price, description, quantity, status "
                        + "FROM tblProduct "
                        + "WHERE name LIKE ?";
                //3. create statement 
                stm = con.prepareStatement(sql);
                stm.setNString(1, "%" + itemName + "%");
                //4. execute query
                rs = stm.executeQuery();
                //5. process
                while (rs.next()) {
                    String idItem = rs.getString("idItem");
                    String name = rs.getNString("name");
                    int price = rs.getInt("price");
                    String description = rs.getNString("description");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");
                    
                    TblproductDTO dto = new TblproductDTO(idItem, name, price, description, quantity, status);
                    
                    if (this.products == null) {
                        this.products = new ArrayList<>();
                    }
                    
                    this.products.add(dto);
                }
            }// end connec successfully
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con. close();
            }
        }
    }
    
    public TblproductDTO getProductsById(String idProduct) 
            throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //1.check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql string
                String sql = "SELECT idItem, name, price, "
                        + "description, quantity, status "
                        + "FROM tblProduct "
                        + "WHERE idItem LIKE ? ";
                //3. create statement 
                stm = con.prepareStatement(sql);
                stm.setNString(1, idProduct);
                //4. execute query
                rs = stm.executeQuery();
                //5. process
                while (rs.next()) {
                    String id = rs.getString("idItem");
                    String name = rs.getNString("name");
                    int price = rs.getInt("price");
                    String description = rs.getNString("description");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");
                    
                    TblproductDTO dto = new TblproductDTO(id, name, price, 
                            description, quantity, status);
                    
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null){
                stm.close();
            }
             if (con != null) {
                con.close();
            }
        }
        return null;
    }
    
//    public int checkQuantityItem (String idItem) throws SQLException, NamingException {
//        Connection con = null;
//        //1. check connection 
//        try {
//            con = DBHelper.makeConnection();
//            if (con != null) {
//                //2. create sql String 
//                String sql = "SELECT quantity "
//                        + "FROM tblProduct "
//                        + "WHERE idItem = ?";
//            }
//        } finally {
//            if (con != null) {
//                con.close();
//            }
//        }
//        return -1;
//    } 
}
