/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class CartObject implements Serializable{
    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }
    
    public void addItemtoCart(String idItem) {
        //1. check cart is existed ?
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        
        int quantity = 1;
        //2. check idItem has existed in cart
        if (this.items.containsKey(idItem)) {
            quantity = this.items.get(idItem) + 1;
        } 
        
        this.items.put(idItem, quantity);
    }
    
    
    public void removeItemFromCart(String idItem) {
        //1. check cart is existed?
        if (this.items == null) {
            return;
        }
        
        
        //2. check idtem has existed in cart
        if (this.items.containsKey(idItem)) {
            this.items.remove(idItem);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }
    
    public void updateItemsQuantity(String idItem, int quantity) {
        if (this.items == null) {
            return;
        }
        
        for (String id : this.items.keySet()) {
            if (id.equals(idItem)) {
                this.items.put(id, quantity);
                if (this.items.get(id) == 0) {
                    this.items.remove(id);
                }
            }
            if (this.items.isEmpty()) {
                this.items = null;
            }
            
        }
    }
    
}
