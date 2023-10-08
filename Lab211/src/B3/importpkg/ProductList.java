/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.importpkg;

import java.util.ArrayList;

/**
 *
 * @author khanh
 */
public class ProductList extends ArrayList<Product>{
    public ProductList(){
        super();
    }
    
    public void addProduct(Product product){
        this.add(product);
    }
}
