/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.management;

import B3.exportpkg.*;
import B3.importpkg.*;
import Tools.MyTools;
/**
 *
 * @author khanh
 */
public class SaleManagement {
    public static void main(String[] args) {
        BillOfSaleList list = new BillOfSaleList();
        PurchaseReceiptList purList = new PurchaseReceiptList();
        String importFile = "./data/imports.txt";
        String exportFile = "./data/exports.txt";
        String productFile = "./data/products.txt";
        String bsProductFile = "./data/bsproducts.txt";
        list.loadFromFile(exportFile, bsProductFile);
        purList.loadFromFile(importFile, productFile);
        int choice;
        boolean changed = false;
        ProductList pList = list.initializeProductList(purList);
        
        
        do{
            System.out.println();
            choice = Menu.getChoiceInt(
                "Export products",
                "Display Available Products",
                "Display Selling Products",
                "Save Bills And Sold Products To File",
                "Other-Quit"
            );
            
            switch(choice){
                case 1:{
                    list.addBills(pList);
                    int index;
                    //Save the modified quantity to the products in the pList
                    for(PurchaseReceipt r : purList){
                        for(Product p : r.getpList()){
                            index = pList.indexOf(p.getpID());
                            if(index >= 0){
                                p.setCurQuantity(pList.get(index).getCurQuantity());
                            }
                        }
                    }
                    changed = true;
                    break;
                }
                
                case 2:{
                    //Additional menu option to help user keep track of the available products using displayStorage from B3.importpkg.PurchaseReceiptList
                    purList.displayStorage();
                    break;
                }
                
                case 3:{
                    list.displayData(pList, list);
                    break;
                }
                case 4:{
                    //Save the new quantity and status was potentially modified in function 1 to products.txt
                    purList.SaveProductsToFile(productFile);
                    list.SaveToFile(exportFile, bsProductFile);
                    changed = false;
                    break;
                }
                default:{
                    if(changed){
                        boolean b = MyTools.readBoolean("Data changed, do you want to save to file? (Y/N)");
                        if(b == true) {
                            purList.SaveProductsToFile(productFile);
                            list.SaveToFile(exportFile, bsProductFile);
                        }
                    }
                    System.out.println("Program exited!");
                }
            }
        }while((choice >= 1 && choice <= 4));
    }
    
}
