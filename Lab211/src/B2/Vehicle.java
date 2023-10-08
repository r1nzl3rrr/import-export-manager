/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B2;

/**
 *
 * @author khanh
 */
public class Vehicle implements Comparable<Vehicle>{
    private String ID;
    private String name;
    private String color;
    private double price;
    private String brand;
    private String type;
    private int productYear;

    public Vehicle(String ID) {
        this.ID = ID;
    }

    public Vehicle(String ID, String name, String color, double price, String brand, String type, int productYear) {
        if(ID.isEmpty()){
            this.ID = "unknown";
        }
        else{
            this.ID = ID;
        }
        
        if(name.isEmpty()){
            this.name = "unknown";
        }
        else{
            this.name = name;
        }
        
        if(color.isEmpty()){
            this.color = "unknown";
        }
        else{
            this.color = color;
        }
        
        if(brand.isEmpty()){
            this.brand = "unknown";
        }
        else{
            this.brand = brand;
        }
        
        if(type.isEmpty()){
            this.type = "unknown";
        }
        else{
            this.type = type;
        }

        this.price = price;
        this.productYear = productYear;
    }

    @Override
    public boolean equals(Object obj) {
         Vehicle v = (Vehicle) obj;
         return this.ID.equals(v.ID);
    }

    @Override
    public String toString() {
        return ID + "," + name + "," + color + "," + price + "," + brand + "," + type + "," + productYear;
    }

    
    
    @Override
    public int compareTo(Vehicle o) {
        return this.ID.compareTo(o.ID);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }
    
    
    
}
