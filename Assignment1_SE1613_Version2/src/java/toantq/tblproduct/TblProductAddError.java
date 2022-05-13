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
public class TblProductAddError implements Serializable{
    private String statusItemError;
    private String quantityItemError;

    public TblProductAddError() {
    }

    
    /**
     * @return the StatusItemError
     */
    public String getStatusItemError() {
        return statusItemError;
    }

    /**
     * @param StatusItemError the StatusItemError to set
     */
    public void setStatusItemError(String StatusItemError) {
        this.statusItemError = StatusItemError;
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
