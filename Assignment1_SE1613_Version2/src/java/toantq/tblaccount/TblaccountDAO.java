/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblaccount;

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
public class TblaccountDAO implements Serializable{
    private List<TblaccountDTO> accounts;

    public List<TblaccountDTO> getAccounts() {
        return accounts;
    }
    
    public boolean checkLogin(String username, String password) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //1. check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql String
                String sql ="SELECT username " 
                        + "FROM tblACCOUNT "
                        + "WHERE username LIKE ? AND password LIKE ?";
                //3. create statement
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. execute query
                rs = stm.executeQuery();
                //5. process
                if (rs.next()) {
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
                con.close();
            }
        }
        return false;
    }
    
    public TblaccountDTO getAccount(String username) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //1. check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql string
                String sql = "SELECT username, password, lastname, isAdmin "
                        + "FROM tblACCOUNT "
                        + "WHERE username LIKE ?";
                //3. create statement
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4. execute query
                rs = stm.executeQuery();
                //5. process
                if (rs.next()) {
                    String id = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getNString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    
                    TblaccountDTO dto = new TblaccountDTO(id, password, lastname, role);
                    
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
    
    public boolean registerAccount(TblaccountDTO dto) 
            throws SQLException, NamingException {
        if (dto == null) {
            return false;
        }
        Connection con = null;
        PreparedStatement stm = null;
        //1. check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql String
                String sql = "INSERT INTO tblACCOUNT "
                        + "VALUES (?, ?, ?, ?)";
                //3. create statement
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setNString(3, dto.getLastName());
                stm.setBoolean(4, dto.isRole());
                //4. execute query
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
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
        return false;
    }
    
    public void getAccountsByLastName(String lastNameValue) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //1. check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql string
                String sql = "SELECT username, password, lastname, isAdmin "
                        + "FROM TblACCOUNT "
                        + "WHERE lastname LIKE ? ";
                //3. create statement
                stm = con.prepareStatement(sql);
                stm.setNString(1, "%" + lastNameValue + "%");
                //4. execute query
                rs = stm.executeQuery();
                //5. process
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getNString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    if (role == false) {
                        TblaccountDTO dto = new TblaccountDTO(username, password,
                                lastname, role);

                        if (this.accounts == null) {
                            this.accounts = new ArrayList<>();
                        }

                        this.accounts.add(dto);
                    }
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
        
    }
    
    public boolean deleteAccount(String username) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        //1. check connection 
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql String 
                String sql = "DELETE FROM tblACCOUNT "
                        + "WHERE username LIKE ?";
                //3. create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4. execute query
                int row = stm.executeUpdate();
                //5. process
                if (row  > 0) {
                    return true;
                }
            } // end connect successfully
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public boolean updateAccount(String username, 
            String password, boolean role) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        //1. check connection
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql String
                String sql = "UPDATE tblACCOUNT "
                        + "SET password = ?, isAdmin = ? "
                        + "WHERE username LIKE ?";
                //3. create statement
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, role);
                stm.setString(3, username);
                //4. execute Query
                int row = stm.executeUpdate();
                //5. process
                if (row > 0) {
                    return true;
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
        return false;
    }
    
    public boolean checkSameOldPassword(String username, String newPassword) 
            throws SQLException, NamingException {
        //1. check connection 
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs= null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create sql String 
                String sql = "SELECT password "
                        + "FROM tblACCOUNT "
                        + "WHERE username = ? ";
                //3. create statement
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4. execute query
                rs = stm.executeQuery();
                //5. process
                if (rs.next()) {
                    String oldPassword = rs.getString("password");
                    if (oldPassword.equals(newPassword)) {
                        return true;
                    }
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
        return false;
    }
}
