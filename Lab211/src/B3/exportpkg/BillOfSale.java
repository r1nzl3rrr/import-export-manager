/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.exportpkg;

import Tools.MyTools;
import java.util.Date;

/**
 *
 * @author khanh
 */
public class BillOfSale {
    private String bsID;
    private Date bsDate;
    private BsProductList bList = new BsProductList();

    public BillOfSale(String bsID, Date bsDate) {
        this.bsID = bsID;
        this.bsDate = bsDate;
    }

    public BillOfSale(String bsID, Date bsDate, BsProductList bList) {
        this.bsID = bsID;
        this.bsDate = bsDate;
        this.bList = bList;
    }
    
    public String getBsID() {
        return bsID;
    }

    public void setBsID(String bsID) {
        this.bsID = bsID;
    }

    public Date getBsDate() {
        return bsDate;
    }

    public void setBsDate(Date bsDate) {
        this.bsDate = bsDate;
    }

    public BsProductList getPList(){
        return this.bList;
    }
    
    @Override
    public String toString() {
        return bsID + "," + MyTools.toString(bsDate, "dd-MM-yyyy");
    }
    
    
}
