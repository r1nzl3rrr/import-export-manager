/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.importpkg;

import java.util.ArrayList;
import java.util.Date;
import Tools.MyTools;

/**
 *
 * @author khanh
 */
public class PurchaseReceipt implements Comparable<PurchaseReceipt>{
    private String prID;
    private Date date;
    private ProductList pList;
    
    public PurchaseReceipt(String prID){
        this.prID = prID;
    }

    public PurchaseReceipt(String prID, Date date) {
        this.prID = prID;
        this.date = date;
    }
    
    public PurchaseReceipt(String prID, Date date, ProductList pList) {
        this.prID = prID;
        this.date = date;
        this.pList = pList;
    }
    
    @Override
    public boolean equals(Object obj){
        PurchaseReceipt receipt = (PurchaseReceipt) obj;
        return this.prID.equals(receipt.prID);
    }

    public String getPrID() {
        return prID;
    }

    public void setPrID(String prID) {
        this.prID = prID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProductList getpList() {
        return pList;
    }

    public void setpList(ProductList pList) {
        this.pList = pList;
    }

    @Override
    public int compareTo(PurchaseReceipt o) {
        return this.getPrID().compareToIgnoreCase(o.getPrID());
    }

    @Override
    public String toString() {
        return prID + "," + MyTools.toString(date, "dd-MM-yyyy");
    }
    
}
