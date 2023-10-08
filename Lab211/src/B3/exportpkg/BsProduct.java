/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.exportpkg;

/**
 *
 * @author khanh
 */
public class BsProduct {
    String bsID;
    String pID;
    double bsPrice;
    int bsQuantity;
    
    public BsProduct(String bsID, String pID, double bsPrice, int bsQuantity) {
        this.bsID = bsID;
        this.pID = pID;
        this.bsPrice = bsPrice;
        this.bsQuantity = bsQuantity;
    }

    public String getBsID() {
        return bsID;
    }

    public void setBsID(String bsID) {
        this.bsID = bsID;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public double getBsPrice() {
        return bsPrice;
    }

    public void setBsPrice(double bsPrice) {
        this.bsPrice = bsPrice;
    }

    public int getBsQuantity() {
        return bsQuantity;
    }

    public void setBsQuantity(int bsQuantity) {
        this.bsQuantity = bsQuantity;
    }

    @Override
    public String toString() {
        return bsID + "," + pID + "," + bsPrice + "," + bsQuantity;
    }
    
    
    
}
