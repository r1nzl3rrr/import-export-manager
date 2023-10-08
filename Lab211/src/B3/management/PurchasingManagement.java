/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.management;

import B3.importpkg.PurchaseReceiptList;
import Tools.MyTools;

/**
 *
 * @author khanh
 */
public class PurchasingManagement {
    public static void main(String[] args) {
        PurchaseReceiptList list = new PurchaseReceiptList();
        String importFile = "./data/imports.txt";
        String productFile = "./data/products.txt";
        list.loadFromFile(importFile, productFile);
        int choice;
        boolean changed = false;
        
        do{
            System.out.println();
            choice = Menu.getChoiceInt(
                "Import products",
                "Display Storage",
                "Display Products Near Expiration",
                "Display Available Products By Name",
                "Display Out Of Stock Products",
                "Display Product With Quantity Less Than A Given Quantity",
                "Update Product Name, Price, Quantity", "Save Products To File",
                "Other-Quit"
            );
            
            switch(choice){
                case 1:{
                    list.addReceipt();
                    changed = true;
                    break;
                }
                case 2:{
                    list.displayStorage();
                    break;
                }
                case 3:{
                    list.displayNearExpiration();
                    break;
                }
                case 4:{
                    String name = MyTools.readStr("Enter name", ".+");
                    list.displayName(name);
                    break;
                }
                case 5:{
                    list.displayOutOfStock();
                    break;
                }
                case 6:{
                    int quantity = Integer.parseInt(MyTools.readStr("Enter Quantity", "\\d+"));
                    list.displayQuantity(quantity);
                    break;
                }
                case 7:{
                    String pID = MyTools.readStr("Enter ID of the product you want to update (Pxxxxxx)", "^[pP][\\d]{6}$");
                    list.updateProduct(pID.toUpperCase());
                    changed = true;
                    break;
                }
                case 8:{
                    list.SaveReceiptsToFile(importFile);
                    list.SaveProductsToFile(productFile);
                    changed = false;
                    break;
                }
                default:{
                    if(changed){
                        boolean b = MyTools.readBoolean("Data changed, do you want to save to file? (Y/N)");
                        if(b == true) {
                            list.SaveReceiptsToFile(importFile);
                            list.SaveProductsToFile(productFile);
                        }
                    }
                    System.out.println("Program exited!");
                }
            }
        }while((choice >= 1 && choice <= 8));
    }
}
