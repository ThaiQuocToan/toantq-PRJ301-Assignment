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
public class TblproductUpdateError implements Serializable{
    private String updateQuantityItemError;
    private String updateQuantityOverItemError;

    public TblproductUpdateError() {
    }
    
    

    /**
     * @return the updateQuantityItemError
     */
    public String getUpdateQuantityItemError() {
        return updateQuantityItemError;
    }

    /**
     * @param updateQuantityItemError the updateQuantityItemError to set
     */
    public void setUpdateQuantityItemError(String updateQuantityItemError) {
        this.updateQuantityItemError = updateQuantityItemError;
    }

    /**
     * @return the updateQuantityOverItemError
     */
    public String getUpdateQuantityOverItemError() {
        return updateQuantityOverItemError;
    }

    /**
     * @param updateQuantityOverItemError the updateQuantityOverItemError to set
     */
    public void setUpdateQuantityOverItemError(String updateQuantityOverItemError) {
        this.updateQuantityOverItemError = updateQuantityOverItemError;
    }
    
    
}
