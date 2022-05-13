/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblorder_detail;

import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class Tblorder_detailDTO implements Serializable{
    private String idOrder;
    private String idItem;
    private int quantity;

    public Tblorder_detailDTO() {
    }

    public Tblorder_detailDTO(String idOrder, String idItem, int quantity) {
        this.idOrder = idOrder;
        this.idItem = idItem;
        this.quantity = quantity;
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
     * @return the idItem
     */
    public String getIdItem() {
        return idItem;
    }

    /**
     * @param idItem the idItem to set
     */
    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
