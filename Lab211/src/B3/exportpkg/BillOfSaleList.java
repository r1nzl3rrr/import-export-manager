/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.exportpkg;

import Tools.MyTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import B3.importpkg.*;
import java.util.Calendar;

/**
 *
 * @author khanh
 */
public class BillOfSaleList extends ArrayList<BillOfSale>{

    public BillOfSaleList() {
        super();
    }
    /**
     * @return max ID number found in the bill list
     */
    public int getMaxID(){
        int maxID = 0;
        for(BillOfSale b : this){
            //Get the last 6 digits from the ID string BSxxxxxx
            int id = Integer.parseInt(b.getBsID().substring(2));
            if(id > maxID){
                maxID = id;
            }
        }
        return maxID;
    }
    
    /**
     * Load import receipts information from exports.txt
     * Load product information from bsproducts.txt
     * @param fExport
     * @param fBsProduct 
     */
    public void loadFromFile(String fExport, String fBsProduct){
        try {
            //Open exports file, and fileReader to read file.
            File fe = new File(fExport);
            FileReader fre = new FileReader(fe);
            BufferedReader bfe = new BufferedReader(fre);
            String exportDetails;
            //Add bills information and create new fileReader for products file.
            while((exportDetails = bfe.readLine()) != null){
                exportDetails = exportDetails.trim();
                StringTokenizer stki = new StringTokenizer(exportDetails, ",");
                String bID = stki.nextToken().trim().toUpperCase();
                Date date = MyTools.parseDate(stki.nextToken(), "dd-MM-yyyy");
                BsProductList bList = new BsProductList();
                File fp = new File(fBsProduct);
                FileReader frp = new FileReader(fp);
                BufferedReader bfp = new BufferedReader(frp);
                String productDetails;
            //Add products information according to the bills ID.   
                while((productDetails = bfp.readLine()) != null){
                    productDetails = productDetails.trim();
                    StringTokenizer stk = new StringTokenizer(productDetails, ",");
                    String bsID = stk.nextToken().trim().toUpperCase();
                    if(bsID.equals(bID)){
                        String pID = stk.nextToken().trim().toUpperCase();
                        double price = Double.parseDouble(stk.nextToken().trim());
                        int quantity = Integer.parseInt(stk.nextToken().trim());
                        BsProduct product = new BsProduct(bsID, pID, price, quantity);   
                        //Add product to the product list.
                        bList.add(product);
                    }
                }
                //Add the export bills information and the product list to the receipt.
                BillOfSale b = new BillOfSale(bID, date, bList);
                //Add the bills to the receipt bill list.
                this.add(b);
                
                bfp.close();
                frp.close();
            }
            bfe.close();
            fre.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Take all the products from the ReceiptList taken from imports.txt
     * @param purList: The list that contains all products.
     * @return a list of Products.
     */
    public ProductList initializeProductList(PurchaseReceiptList purList){
        ProductList pList = new ProductList();
        for(PurchaseReceipt r : purList){
            for(Product p : r.getpList()){
                pList.add(p);
            }
        }
        return pList;
    }
    /**
     * ===============================================
     * FUNCTION 1: ADD EXPORT BILLS AND SOLD PRODUCTS.
     * ===============================================
     * @param list: A list of Products passed in to modify its quantity after selling
     */
    public void addBills(ProductList list){
        //Automatically generates bills ID based on the current highest ID number.
        String bID = MyTools.generateCode("BS", 6, this.getMaxID() + 1);
        System.out.println("New bill id generated: " + bID);
        int year;
        Date date;
        //Looping until the inputted year is greater than 1760.
        do{
            date = MyTools.readDateBefore("Enter creation date", "dd-MM-yyyy", new Date());
            year = MyTools.getPart(date, Calendar.YEAR);
            if(year < 1760) System.out.println("Inputted year is not valid!");
        }while(year < 1760);
        
        int n = Integer.parseInt(MyTools.readStr("Enter the number of products to sell", "\\d+"));
        //Create new BsProduct list to save sold products.
        BsProductList bList = new BsProductList();
        int i = 1;
        //Loop nth time (n is the number of products to export).
        while(i <= n){
            System.out.println("Product #" + i + ": ");
            String id = MyTools.readStr("Enter ID of the product you want to sell (Pxxxxxx)", ".+").toUpperCase();
            int index = list.indexOf(new Product(id));
            if(index >= 0 && list.get(index).isContinuous()){
                double price;
                int quantity;
                boolean validPrice;
                boolean validQuantity;
                do{
                    price = Double.parseDouble(MyTools.readStr("Enter selling price", "^([0]+\\.[0]*[1-9]+[\\d]*)|([0]*[1-9]+[\\d]*(\\.\\d+)?)$"));
                    //Checking if the inputted price is greater than the purchasing price
                    validPrice = price >= list.get(index).getPurchasePrice();
                    if(!validPrice) System.out.println("Please enter selling price greater than or equal to purchasing price!");
                }while(!validPrice);

                do{
                    quantity = Integer.parseInt(MyTools.readStr("Enter quantity of the product you want to sell", "^[0]*[1-9]+[\\d]*$"));
                    //Checking if the inputted quantity is less than or equal to the current quantity
                    validQuantity = quantity <= list.get(index).getCurQuantity();
                    if(!validQuantity) System.out.println("Please enter selling quantity less than or equal to the current quantity of the product!");
                }while(!validQuantity);
                //Subtracts the inputted quantity from the current quantity
                list.get(index).setCurQuantity(list.get(index).getCurQuantity() - quantity);
                //Create new sold product object with the data inputted above
                BsProduct bProduct = new BsProduct(bID, id, price, quantity);
                //Add the product to the sold product list
                bList.add(bProduct);
            }
            i++;
        }     
        //Create new bill with the above data, includeing the product list
        BillOfSale b = new BillOfSale(bID,date,bList);
        //Add the bill to the bill list
        this.add(b);
        System.out.println("Bill added!");
    }
    
    /**
     * ========================================================================
     * FUNCTION 2: OPTIONAL FUNCTION SO THE USER CAN KEEPTRACK OF THE PRODUCTS.
     * ========================================================================
     */
    
    /**
     * UI: drawTable to draw the header of the table
     *     printData to print the information of bills and products
     *     closeTable to print the final border of the table
     */
    public void drawTable() {
        System.out.printf("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
        System.out.printf("|                                                                     EXPORTING PRODUCTS                                                                       |%n");
        System.out.printf("+------------|------------|--------------------------------|-----------------|------------------|------------|-----------------|-----------------|-------------+%n");
        System.out.printf("| %-10s | %-10s | %-30s | %-15s | %-16s | %-10s | %-15s | %-15s | %-11s |%n", "BILL ID", "PRODUCT ID", "NAME", "SELLING PRICE", "SELLING QUANTITY", "CONTINUOUS", "PRODUCTION DATE", "EXPIRATION DATE", "EXPORT DATE");
        System.out.printf("+------------|------------|--------------------------------|-----------------|------------------|------------|-----------------|-----------------|-------------+%n");
    }
    //Print the data corresponding to the columns
    public void printData(BsProduct bs, Product p, BillOfSale b) {
            System.out.printf("| %-10s | %-10s | %-30s | %-15.2f | %-16d | %-10s | %-15s | %-15s | %-11s |%n", b.getBsID(), bs.getpID(), p.getName(), bs.getBsPrice(), bs.getBsQuantity(), p.isContinuous(), MyTools.toString(p.getProductionDate(), "dd-MM-yyyy"), MyTools.toString(p.getExpirationDate(), "dd-MM-yyyy"), MyTools.toString(b.getBsDate(), "dd-MM-yyyy"));
    }

    public void closeTable() {
        System.out.printf("+------------|------------|--------------------------------|-----------------|------------------|------------|-----------------|-----------------|-------------+%n");
    }
    /**
     * ==========================================
     * FUNCTION 3: DISPLAY ALL THE SOLD PRODUCTS.
     * ==========================================
     * @param pList: To get the additional attributes like name, date that doesn't exist in BsProduct.
     * @param bList: To get all sold products. 
     */
    public void displayData(ProductList pList, BillOfSaleList bList){
        drawTable();
        int index;
        //Checking for empty list
        if(bList.isEmpty()){
            System.out.printf("|%87s%70s%n","PRODUCT NOT FOUND!","|");
            closeTable();
            return;
        }
        for(BillOfSale b : bList){
            for(BsProduct bs : b.getPList()){
                //Find the sold product in the product list
                index = pList.indexOf(new Product(bs.getpID()));
                if(index >= 0){
                    Product p = pList.get(index);
                    //Display data using printData function.
                    printData(bs,p,b);
                }
            }
        }
        closeTable();
    }
    /**
     * ===================================================
     * FUNCTION 4: SAVE PRODUCTS AND EXPORT BILLS TO FILE.
     * ===================================================
     * @param fExport file that stored receipts information
     * @param fBsProduct file that stored products information
     */
    public void SaveToFile(String fExport, String fBsProduct){
        //Check for empty list
        if (this.isEmpty()) {
            System.out.println("Empty Product List");
            return;
        }
        try {
            //Create FileWriter to write export bills to file
            File fr = new File(fExport);
            FileWriter fwr = new FileWriter(fr);
            PrintWriter pwr = new PrintWriter(fwr);
            //Create FileWriter to write products to file
            File fp = new File(fBsProduct);
            FileWriter fwp = new FileWriter(fp);
            PrintWriter pwp = new PrintWriter(fwp);
            //Looping to write all the necessary data to exports.txt and bsproducts.txt
            for (BillOfSale b : this) {
                //Write export bill data to exports.txt
                pwr.println(b.toString());
                for(BsProduct bs : b.getPList()){
                    //Write sold products to bsproducts.txt
                    pwp.println(bs.toString());
                }
            }
            System.out.println("All bills saved!");
            pwp.close();
            fwp.close();
            pwr.close();
            fwr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
}
