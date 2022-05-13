/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblorder;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblorderDTO implements Serializable{
    private String idOrder;
    private String username;
    private Date date;

    public TblorderDTO() {
    }

    public TblorderDTO(String idOrder, String username, Date date) {
        this.idOrder = idOrder;
        this.username = username;
        this.date = date;
    }

    /**
     * @return the idOrder
     */
    public String getIdOrder() {
        return idOrder;
    }

    /**
     * @param idOrder the idOrder to set
     */
    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
