/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.importpkg;

import Tools.MyTools;
import java.util.Date;

/**
 *
 * @author khanh
 */
public class Product implements Comparable<Product>{
    private String pID;
    private String name;
    private double purchasePrice;
    private int initialQuantity;
    private int curQuantity;
    private Date productionDate;
    private Date expirationDate;
    private boolean continuous;

    public Product(String pID) {
        this.pID = pID;
    }

    public Product(String pID, String name, double purchasePrice, int initialQuantity, int curQuantity, Date productionDate, Date expirationDate) {
        this.pID = pID;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.initialQuantity = initialQuantity;
        this.curQuantity = curQuantity;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        if(this.curQuantity == 0){
            this.continuous = false;
        }
        else this.continuous = true;
    }

    @Override
    public boolean equals(Object obj){
        Product p = (Product) obj;
        return this.pID.equals(p.pID);
    }

    @Override
    public String toString() {
        return pID + "," + name + "," + purchasePrice + "," + curQuantity + "," + MyTools.toString(productionDate, "dd-MM-yyyy") + "," + MyTools.toString(expirationDate, "dd-MM-yyyy");
    }
    
    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public int getCurQuantity() {
        return curQuantity;
    }

    public void setCurQuantity(int curQuantity) {
        this.curQuantity = curQuantity;
        if(curQuantity == 0){
            this.continuous = false;
        }
        else this.continuous = true;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    @Override
    public int compareTo(Product o) {
       return this.pID.compareToIgnoreCase(o.pID);
    }
    
}
