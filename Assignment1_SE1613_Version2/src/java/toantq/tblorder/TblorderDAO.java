/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import toantq.utils.DBHelper;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblorderDAO implements Serializable{
    
    public TblorderDTO addOrderForUser(String username, Date date) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        //1. check connection 
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2 create sql String
                String sql = "INSERT INTO tblOrder "
                        + "VALUES ('', ?, ?)";
                //3. create statement 
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setDate(2, date);
                //4. execute query
                int row = stm.executeUpdate();
                //5. process
                if (row > 0) {
                    TblorderDTO order = getNewOrderInformation();
                    return order;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
        
    }
    
    public TblorderDTO getNewOrderInformation ()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //1. check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create SQL string
                String sql ="SELECT TOP 1 idOrder, username, dateCreate "
                        + "FROM tblOrder "
                        + "ORDER BY idOrder DESC";
                //3. create statement
                stm = con.prepareStatement(sql);
                //4. execute query
                rs = stm.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("idOrder");
                    String userName =rs.getString("username");
                    Date date = rs.getDate("dateCreate");
                    
                    TblorderDTO dto = new TblorderDTO(id, userName, date);
                    
                    return dto;
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
                con.close();
            }
        }
        return null;
    }
    
}
