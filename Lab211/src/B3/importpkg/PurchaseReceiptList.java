/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.importpkg;

import B2.Vehicle;
import java.util.ArrayList;
import Tools.MyTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.function.Predicate;

/**
 *
 * @author khanh
 */
public class PurchaseReceiptList extends ArrayList<PurchaseReceipt> {
    public PurchaseReceiptList() {
        super();
    }  
    /**
     * @return max ID number found in the receipt list
     */
    public int getMaxID(){
        int maxID = 1;
        for(PurchaseReceipt receipt : this){
            //Get the last 7 digits number from the ID string IMxxxxxxx
            int id = Integer.parseInt(receipt.getPrID().substring(2));
            if(id > maxID){
                maxID = id;
            }
        }
        return maxID;
    }
    /**
     * @return max ID number found in the product list
     */
    public int getMaxPID(){
        int maxID = 1;
        for(PurchaseReceipt r : this){
            for(Product product : r.getpList()){
                //Get the last 6 digits from the ID string Pxxxxxx
                int id = Integer.parseInt(product.getpID().substring(1));
                if(id > maxID){
                    maxID = id;
                }
            }
        }
        return maxID;
    }
    
    /**
     * Load import receipts information from imports.txt
     * Load product information from products.txt
     * @param fImport
     * @param fProduct 
     */
    public void loadFromFile(String fImport, String fProduct){
        try {
            //Open import file, and fileReader to read file.
            File fi = new File(fImport);
            FileReader fri = new FileReader(fi);
            BufferedReader bfi = new BufferedReader(fri);
            String importDetails;
            //Add receipts information and create new fileReader for products file.
            while((importDetails = bfi.readLine()) != null){
                importDetails = importDetails.trim();
                StringTokenizer stki = new StringTokenizer(importDetails, ",");
                String rID = stki.nextToken().trim().toUpperCase();
                Date date = MyTools.parseDate(stki.nextToken(), "dd-MM-yyyy");
                ProductList pList = new ProductList();
                File fp = new File(fProduct);
                FileReader frp = new FileReader(fp);
                BufferedReader bfp = new BufferedReader(frp);
                String productDetails;
            //Add products information according to the receipt ID.   
                while((productDetails = bfp.readLine()) != null){
                    productDetails = productDetails.trim();
                    StringTokenizer stk = new StringTokenizer(productDetails, ",");
                    String prID = stk.nextToken().trim().toUpperCase();
                    if(prID.equals(rID)){
                        String pID = stk.nextToken().trim().toUpperCase();
                        String name = MyTools.capWords(stk.nextToken());
                        double price = Double.parseDouble(stk.nextToken().trim());
                        int quantity = Integer.parseInt(stk.nextToken().trim());
                        Date productionDate = MyTools.parseDate(stk.nextToken(), "dd-MM-yyyy");
                        Date ExpirationDate = MyTools.parseDate(stk.nextToken(), "dd-MM-yyyy");
                        Product product = new Product(pID, name, price, quantity, quantity, productionDate, ExpirationDate);
                        //Add product to the product list.
                        pList.addProduct(product);
                    }
                }
                //Add the receipt information and the product list to the receipt.
                PurchaseReceipt receipt = new PurchaseReceipt(rID, date, pList);
                //Add the receipt to the receipt list.
                this.add(receipt);
                bfp.close();
                frp.close();
            }

            bfi.close();
            fri.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * UI: drawTable to draw the header of the table
     *     printData to print the information of receipts and products
     *     closeTable to print the final border of the table
     */
    public void drawTable() {
        System.out.printf("+------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
        System.out.printf("|                                                                      PRODUCTS                                                                        |%n");
        System.out.printf("+------------|------------|--------------------------------|-----------------|----------|------------|-----------------|-----------------|-------------+%n");
        System.out.printf("| %-10s | %-10s | %-30s | %-15s | %-8s | %-10s | %-15s | %-15s | %-11s |%n", "RECEIPT ID", "PRODUCT ID", "NAME", "PRICE", "QUANTITY", "CONTINUOUS", "PRODUCTION DATE", "EXPIRATION DATE", "IMPORT DATE");
        System.out.printf("+------------|------------|--------------------------------|-----------------|----------|------------|-----------------|-----------------|-------------+%n");
    }
    
    /**
     * Print all of the product attributes in the corresponding column
     * @param r 
     */
    public void printData(PurchaseReceipt r) {
        for(Product p : r.getpList()){
            System.out.printf("| %-10s | %-10s | %-30s | %-15.2f | %-8d | %-10s | %-15s | %-15s | %-11s |%n", r.getPrID(), p.getpID(), p.getName(), p.getPurchasePrice(), p.getCurQuantity(), p.isContinuous(), MyTools.toString(p.getProductionDate(), "dd-MM-yyyy"), MyTools.toString(p.getExpirationDate(), "dd-MM-yyyy"), MyTools.toString(r.getDate(), "dd-MM-yyyy"));
        }
    }

    public void closeTable() {
        System.out.printf("+------------|------------|--------------------------------|-----------------|----------|------------|-----------------|-----------------|-------------+%n");
    }
    /**
     * To display all of the receipts and products in the current list using this function
     */
    public void displayProduct(){
        drawTable();
        if(this.isEmpty()) {
            System.out.printf("|%87s%61s%n","PRODUCT NOT FOUND!","|");
            closeTable();
            return;
        }
        else{
            for(PurchaseReceipt r : this){
            printData(r);
            }
        }
        closeTable();
        
    }
    /**
     * =======================================================================================
     * FUNCTION 1: INPUT RECEIPTS AND PRODUCTS INFORMATION IN THE RECEIPT AND ADD TO THE LIST.
     * =======================================================================================
     */
    public void addReceipt(){
        //Automatically generates receipt ID based on the current highest ID number.
        String prID = MyTools.generateCode("IM", 7, this.getMaxID() + 1);
        System.out.println("New receipt id generated: " + prID);
        int year;
        Date date;
        //Loop until the user inputted the year greater than 1760 as it is the start of the industrial revolution, it seems relevant.
        do{
            date = MyTools.readDateBefore("Enter creation date", "dd-MM-yyyy", new Date());
            year = MyTools.getPart(date, Calendar.YEAR);
            if(year < 1760) System.out.println("Inputted year is not valid");
        }while(year < 1760);
        
        int n = Integer.parseInt(MyTools.readStr("Enter the number of products", "\\d+"));
        int i = 1;
        ProductList pList = new ProductList();
        //Loop nth time (n is the number of products to import).
        while(i <= n){
            System.out.println("Product #"+i+": ");
            //Automatically generates product ID based on the current highest ID number.
            String pID = MyTools.generateCode("P", 6, getMaxPID() + i);
            System.out.println("New code generated: "+pID);
            String name = MyTools.capWords(MyTools.readStr("Enter name", "^[a-zA-Z]+(\\s+[a-zA-Z]+)*$"));
            double price = Double.parseDouble(MyTools.readStr("Enter price", "^([0]+\\.[0]*[1-9]+[\\d]*)|([0]*[1-9]+[\\d]*(\\.\\d+)?)$"));
            int initialQuantity = Integer.parseInt(MyTools.readStr("Enter quantity", "^[0]*[1-9]+[\\d]*$"));
            //"^([0]+\\d+[\\d]*)|([0]+\\d*\\.[0]*[1-9]+[\\d]*)|([1-9][\\d]*(\\.\\d+)?)$"
            int productionYear;
            Date productionDate;
            //Loop until the user inputted the year greater than 1760.
            do{
                productionDate = MyTools.readDateBefore("Enter manufacturing date", "dd-MM-yyyy", date);
                productionYear = MyTools.getPart(productionDate, Calendar.YEAR);
                if(productionYear < 1760) System.out.println("Inputted year is not valid");
            }while(productionYear < 1760);
            
            Date expirationDate = MyTools.readDateAfter("Enter expiration date", "dd-MM-yyyy", productionDate);
            Product product = new Product(pID, name, price, initialQuantity, initialQuantity, productionDate, expirationDate);
            pList.addProduct(product);
            i++;
        }
        PurchaseReceipt receipt = new PurchaseReceipt(prID, date, pList);
        this.add(receipt);
        System.out.println("Receipt added!");
    }
    /**
     * Returns a new PurchaseReceiptList containing only the PurchaseReceipt objects that have at least one Product object
     * that satisfies the given condition.
     * @param condition a Predicate that defines the condition that a Product object must satisfy to be included in the
     * filtered list.
     * @return a new PurchaseReceiptList containing only the PurchaseReceipt objects that have at least one Product object
     * that satisfies the given condition.
     */
    public PurchaseReceiptList filterProductList(Predicate<Product> condition){
        PurchaseReceiptList list = new PurchaseReceiptList();
        for(PurchaseReceipt r : this){
            ProductList pList = new ProductList();
            for(Product p : r.getpList()){
                //Testing the condition if true then add product to the product list.
                if(condition.test(p)){
                    pList.add(p);
                }
            }
            if(!pList.isEmpty()){
                PurchaseReceipt receipt = new PurchaseReceipt(r.getPrID(), r.getDate(), pList);
                list.add(receipt);
            }
        }
        return list;
    }
    /**
     * =======================================
     * FUNCTION 2: DISPLAY AVAILABLE PRODUCTS.
     * =======================================
     * products with quantity > 0
     * and sort them by receiptID ascending if receiptID are the same
     * sort productID ascending.
     */
    public void displayStorage(){
        //Create a comparator to sort Receipts ID ascendingly
        Comparator<PurchaseReceipt> cRIdAsc = new Comparator<PurchaseReceipt>() {
            @Override
            public int compare(PurchaseReceipt o1, PurchaseReceipt o2) {
                int rID = o1.getPrID().compareTo(o2.getPrID());
                return rID;
            }
        };
        //Create a comparator to sort Products ID ascendingly
        Comparator<Product> cPIdAsc = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getpID().compareTo(o2.getpID());
            }
        };
        //Condition to be able to be displayed
        Predicate<Product> condition = p -> p.getCurQuantity() > 0;
        PurchaseReceiptList list = filterProductList(condition);
        //Sorting the list by Receipt ID
        Collections.sort(list, cRIdAsc);
        for(PurchaseReceipt r : list){
            //Sorting the list by Product ID
            Collections.sort(r.getpList(), cPIdAsc);
        }
        list.displayProduct();
    }
    /**
     * =========================================================
     * FUNCTION 3: DISPLAY PRODUCT THE IS 10 DAYS TO EXPIRATION.
     * =========================================================
     */
    public void displayNearExpiration(){
        //Get the current date.
        LocalDate today = LocalDate.now();
        Predicate<Product> condition = p -> (
           //Convert Date to LocalDate to be able to use isAfter function.
            today.plusDays(11).isAfter(LocalDate.parse(MyTools.toString(p.getExpirationDate(), "yyyy-MM-dd"))) 
            && 
            today.isBefore(LocalDate.parse(MyTools.toString(p.getExpirationDate(), "yyyy-MM-dd")))
            );
        
        PurchaseReceiptList list = filterProductList(condition);
        list.displayProduct();
    }
    /**
     * =================================================
     * FUNCTION 4: DISPLAY PRODUCT THAT IS OUT OF STOCK.
     * =================================================
     */
    public void displayOutOfStock(){
        Predicate<Product> condition = p -> !p.isContinuous();
        PurchaseReceiptList list = filterProductList(condition);
        list.displayProduct();
    }
    /**
     * ====================================
     * FUNCTION 5: DISPLAY PRODUCT BY NAME.
     * ====================================
     * @param name: Displayed products will contain the inputted string
     */
    public void displayName(String name){
        Predicate<Product> condition = p -> p.isContinuous() && p.getName().toLowerCase().contains(name.toLowerCase());
        PurchaseReceiptList list = filterProductList(condition);
        list.displayProduct();
    }
    /**
     * ===================================================================
     * FUNCTION 6: DISPLAY PRODUCT WITH QUANTITY LESS THAN GIVEN QUANTITY.
     * ===================================================================
     * @param quantity: Input quantity that will be greater than the quantity of products going to be displayed
     */
    public void displayQuantity(int quantity){
        Predicate<Product> condition = p -> p.getCurQuantity() < quantity;
        PurchaseReceiptList list = filterProductList(condition);
        list.displayProduct();
    }
    /**
     * =====================================================
     * FUNCTION 7: UPDATE PRODUCT NAME, PRICE, AND QUANTITY.
     * =====================================================
     * @param pID: Product with this ID will be updated
     */
    public void updateProduct(String pID){
        String newName;
        double newPrice;
        int newQuantity;
        boolean found = false;
        for(PurchaseReceipt r : this){
            ProductList pList = r.getpList();
            int index = r.getpList().indexOf(new Product(pID));
            if(index >= 0){
                //Input new data to update
                newName = MyTools.readStr("Enter new name", "^[a-zA-Z]+(\\s+[a-zA-Z]+)*$");
                newPrice = Double.parseDouble(MyTools.readStr("Enter new price", "^([0]+\\.[0]*[1-9]+[\\d]*)|([0]*[1-9]+[\\d]*(\\.\\d+)?)$"));
                newQuantity = Integer.parseInt(MyTools.readStr("Enter new Quantity", "\\d+"));
                //Update data by setting
                pList.get(index).setName(MyTools.capWords(newName));
                pList.get(index).setPurchasePrice(newPrice);
                pList.get(index).setCurQuantity(newQuantity);
                found = true;
                System.out.println("Product updated!");
                break;
            }
        }
        if(found == false) System.out.println("No product with ID: " + pID);
    }
    /**
     * ===============================================
     * FUNCTION 8: SAVE PRODUCTS AND RECEIPTS TO FILE.
     * ===============================================
     * @param fProduct file that stored products information
     */
    public void SaveProductsToFile(String fProduct){
        //Check for empty list
        if (this.isEmpty()) {
            System.out.println("Empty Product List");
            return;
        }
        try {
            //Create FileWriter to write products to file
            File fp = new File(fProduct);
            FileWriter fwp = new FileWriter(fp);
            PrintWriter pwp = new PrintWriter(fwp);
            //Looping to write all the product information along with the receipt ID to products.txt
            for (PurchaseReceipt r : this) {
                for(Product p : r.getpList()){
                    pwp.print(r.getPrID()+",");
                    pwp.println(p.toString());
                }
            }
            System.out.println("All products saved!");
            pwp.close();
            fwp.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //Writing all the receipts information to file
    /**
     * @param fImport file that stored receipts information
     */
    public void SaveReceiptsToFile(String fImport){
        if (this.isEmpty()) {
            System.out.println("Empty Product List");
            return;
        }
        try {
            //Create FileWriter to write receipts to file
            File fr = new File(fImport);
            FileWriter fwr = new FileWriter(fr);
            PrintWriter pwr = new PrintWriter(fwr);
            //Write all Receipt information to imports.txt
            for (PurchaseReceipt r : this) {
                pwr.println(r.toString());
            }
            System.out.println("All import receipts saved!");
            pwr.close();
            fwr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    
}
