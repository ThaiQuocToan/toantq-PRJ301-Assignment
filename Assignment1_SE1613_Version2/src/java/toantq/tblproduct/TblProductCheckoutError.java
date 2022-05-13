/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblproduct;

import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblProductCheckoutError implements Serializable{
    private String quantityItemError;

    public TblProductCheckoutError() {
    }

    /**
     * @return the quantityItemError
     */
    public String getQuantityItemError() {
        return quantityItemError;
    }

    /**
     * @param quantityItemError the quantityItemError to set
     */
    public void setQuantityItemError(String quantityItemError) {
        this.quantityItemError = quantityItemError;
    }
    
    
}
