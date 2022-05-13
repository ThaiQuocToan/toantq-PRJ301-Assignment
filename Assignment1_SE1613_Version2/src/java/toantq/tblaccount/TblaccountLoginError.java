/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.tblaccount;

import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblaccountLoginError implements Serializable{
    private String loginError;

    public TblaccountLoginError() {
    }

    /**
     * @return the loginError
     */
    public String getLoginError() {
        return loginError;
    }

    /**
     * @param loginError the loginError to set
     */
    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }
    
    
}
