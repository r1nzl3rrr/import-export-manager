/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B2;

import static B2.Menu.getChoiceInt;
import Tools.MyTools;
import java.util.ArrayList;
import java.util.Comparator;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import static Tools.MyTools.parseBoolean;
import java.time.Year;
import java.util.Collections;

/**
 *
 * @author khanh
 */
public class VehicleList extends ArrayList<Vehicle> {

    Comparator<Vehicle> cNameDesc = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return -o1.getName().compareTo(o2.getName());
        }
    };

    //Add all the vehicle to this list with the return value from the function loadVehicleFromFile
    public VehicleList() {
        super();
    }

    //Load the vehicles from the file "vehicle.dat"
    public void LoadVehicleFromFile(String fName) {
        try {
            File f = new File(fName);
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String vehicleDetails;

            while ((vehicleDetails = bf.readLine()) != null) {
                vehicleDetails = vehicleDetails.trim();
                //Splitting details into elements with delimiter ","
                StringTokenizer stk = new StringTokenizer(vehicleDetails, ",");
                String ID = stk.nextToken().trim().toUpperCase();
                String name = MyTools.capWords(stk.nextToken());
                String color = MyTools.capWords(stk.nextToken());
                double price = Double.parseDouble(stk.nextToken().trim());
                String brand = stk.nextToken().trim().toUpperCase();
                String type = MyTools.capWords(stk.nextToken());
                int productYear = Integer.parseInt(stk.nextToken().trim());
                //Create a new vehicle
                Vehicle vehicle = new Vehicle(ID, name, color, price, brand, type, productYear);
                this.add(vehicle);
            }

            bf.close();
            fr.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //Creating UI when printing out data
    public void drawTable() {
        System.out.printf("+-----------------------------------------------------------------------------------------------------------------------+%n");
        System.out.printf("|                                                   VEHICLE MANAGEMENT                                                  |%n");
        System.out.printf("+------------|--------------------------------|------------|--------------|-----------------|------------|--------------+%n");
        System.out.printf("| %-10s | %-30s | %-10s | %-12s | %-15s | %-10s | %s |%n", "ID", "NAME", "COLOR", "PRICE", "BRAND", "TYPE", "PRODUCT YEAR");
        System.out.printf("+------------|--------------------------------|------------|--------------|-----------------|------------|--------------+%n");
    }

    public void printData(Vehicle v) {
        System.out.printf("| %-10s | %-30s | %-10s | %-12.2f | %-15s | %-10s | %-13d|%n", v.getID(), v.getName(), v.getColor(), v.getPrice(), v.getBrand(), v.getType(), v.getProductYear());
    }

    public void closeTable() {
        System.out.printf("+------------|--------------------------------|------------|--------------|-----------------|------------|--------------+%n");
    }
    //Function 1: Add new vehicle to the list 

    public void addVehicle() {
        boolean choice;
        String id;
        String name;
        String color;
        String brand;
        String type;
        double price;
        int productYear;
        int pos;
        do {
            System.out.println("Add new vehicle: ");
            //ID must match pattern: Vxxxx
            //Using do while loop to ask user to input again if the id is duplicated
            do {
                id = MyTools.readStr("Enter ID (V1234)", "^[vV][\\d]{4}$").toUpperCase();
                pos = this.indexOf(new Vehicle(id));
                if (pos >= 0) {
                    System.out.println("ID duplicate!");
                }
            } while (pos >= 0);
            //Using regex to validate the inputted string in the following categories
            name = MyTools.capWords(MyTools.readStr("Enter name", "^[a-zA-Z]+(\\s+[a-zA-Z]+)*$"));
            color = MyTools.capWords(MyTools.readStr("Enter color", "^[a-zA-Z]+(\\s+[a-zA-Z]+)*$"));
            price = Double.parseDouble(MyTools.readStr("Enter price", "^[\\d]+(\\.\\d+)?$"));
            brand = MyTools.readStr("Enter brand", "^[a-zA-Z]+(\\s+[a-zA-Z]+)*$").toUpperCase();
            type = MyTools.readStr("Enter type", "[sSa-fA-F]").toUpperCase();
            productYear = Integer.parseInt(MyTools.readStr("Enter product year", "^[1-9][\\d]{3}$"));

            Vehicle v = new Vehicle(id, name, color, price, brand, type, productYear);
            this.add(v);
            System.out.println("New vehicle added successfully");
            //Ask the user if he wants to add more vehicles
            choice = MyTools.readBoolean("Do you want to add more vehicles?");

        } while (choice);
    }

    //Function 2: Check for existing vehicle stored in the vehicle.dat file
    public void checkExist() {
        //Allow user to enter anything, no restriction
        String id = MyTools.readStr("Enter ID you want to check for existing vehicle", ".*");
        //Check for ID that was found in the file only not the current list
        for (Vehicle v : this) {
            if (v.getID().equalsIgnoreCase(id)) {
                System.out.println("Existed Vehicle");
                return;
            }

        }
        System.out.println("No Vehicle Found!");
    }

    //Function 3: Update vehicle with ID inputted
    public void updateVehicle() {
        String newName;
        String newColor;
        String newBrand;
        String newType;
        double newPrice;
        int newProductYear;
        boolean validYear;
        boolean validPrice;
        String id;
        int index;

        //Enter ID to update specific vehicle
        do {
            id = MyTools.readStr("Enter ID", ".+").toUpperCase();
            index = this.indexOf(new Vehicle(id));
            if (index == -1) {
                System.out.println("Vehicle does not exist");
            }
        } while (index == -1);

        Vehicle v = this.get(index);
        //If found then update
        //All inputs allow user to enter anything even empty string
        //If nothing was entered do not update the data
        newName = MyTools.capWords(MyTools.readStr("Enter name", ".*"));
        if (!newName.isEmpty()) {
            v.setName(newName);
        }

        newColor = MyTools.capWords(MyTools.readStr("Enter color", ".*"));
        if (!newColor.isEmpty()) {
            v.setColor(newColor);
        }

        String tempPrice = MyTools.readStr("Enter price", ".*");
        if (!tempPrice.isEmpty()) {
            do {
                //Validate data for price which is double
                validPrice = tempPrice.matches("^[\\d]+(\\.\\d+)?$");
                if (validPrice) {
                    newPrice = Double.parseDouble(tempPrice);
                    v.setPrice(newPrice);
                } else {
                    tempPrice = MyTools.readStr("Enter price", ".*");
                }
            } while (!validPrice);
        }

        newBrand = MyTools.readStr("Enter brand", ".*").toUpperCase();
        if (!newBrand.isEmpty()) {
            v.setBrand(newBrand);
        }

        newType = MyTools.capWords(MyTools.readStr("Enter type", "[sSa-fA-F]?"));
        if (!newType.isEmpty()) {
            v.setType(newType);
        }

        String tempYear = MyTools.readStr("Enter year", ".*");
        if (!tempYear.isEmpty()) {
            do {
                //Validate data for year which is integer
                validYear = tempYear.matches("^[1-9][\\d]{3}$");
                if (validYear) {
                    newProductYear = Integer.parseInt(tempYear);
                    v.setProductYear(newProductYear);
                }
                else{
                    tempYear = MyTools.readStr("Enter year", ".*");
                }
            } while (!validYear);
        }
        System.out.println("Vehicle updated!");
    }

    //Function 4: Delete vehicle from the list
    public void deleteVehicle() {
        String id = MyTools.readStr("Enter id of the vehicle you want to delete", ".+").toUpperCase();
        boolean choice;
        int index = this.indexOf(new Vehicle(id));
        if (index >= 0) {
            //Ask user for confirmation
            choice = MyTools.readBoolean("Are you sure you want to delete vehicle with ID " + "\"" + this.get(index).getID() + "\"?");
            if (choice) {
                this.remove(index);
                System.out.println("Vehicle removed successfully");
            } else {
                System.out.println("Vehicle is not removed");
            }
        } else {
            System.out.println("Vehicle does not exist");
        }
    }

    //Function 5: Search vehicle (Sub Menu)
    public void searchMenu() {
        int choice;
        //Check for valid input
        System.out.println();
        do {
            choice = getChoiceInt("Search vehicle by ID", "Search vehicles by Name", "Return");
            switch (choice) {
                case 1: {
                    String id = MyTools.readStr("Enter ID", ".+").toUpperCase();
                    searchById(id);
                    break;
                }
                case 2: {
                    String name = MyTools.readStr("Enter Name", ".+");
                    VehicleList L = searchByName(name);
                    L.displayAll();
                    break;
                }
                default: {
                    System.out.println("Sub Menu exited!");
                }
            }
        } while (choice == 1 || choice == 2);
    }

    //Function to put in the SearchMenu
    public void searchById(String id) {
        drawTable();
        int pos = indexOf(new Vehicle(id));
        if (pos >= 0) {
            Vehicle v = this.get(pos);
            printData(v);
        } else {
            System.out.printf("|%70s%50s%n", "VEHICLE NOT FOUND!", "|");
        }
        closeTable();
    }

    //Function to put in the SearchMenu
    public VehicleList searchByName(String name) {
        VehicleList L = new VehicleList();
        for (Vehicle v : this) {
            if (v.getName().toLowerCase().contains(name.toLowerCase())) {
                L.add(v);
            }
        }
        //Perform sorting the name descendingly with the comparator cNameDesc
        Collections.sort(L, cNameDesc);
        return L;
    }

    //Function 6: Display vehicle list (Sub Menu)
    public void displayMenu() {
        int choice;
        System.out.println();
        VehicleList L;
        do {
            choice = getChoiceInt("Show all vehicles", "Show vehicles by price", "Show vehicles by year", "Quit");
            switch (choice) {
                case 1: {
                    this.displayAll();
                    break;
                }
                case 2: {
                    double price = Double.parseDouble(MyTools.readStr("Enter Max Price", "^[\\d]+(\\.\\d+)?$"));
                    L = this.filterPrice(price);
                    L.displayAll();
                    break;
                }
                case 3: {
                    int year = Integer.parseInt(MyTools.readStr("Enter Min Year", "^[1-9][\\d]{3}$"));
                    L = this.filterYear(year);
                    L.displayAll();
                    break;
                }
                default: {
                    System.out.println("Sub Menu exited!");
                }
            }
        } while (choice >= 1 && choice <= 3);

    }

    //Function to put in displayMenu to print all
    public void displayAll() {
        drawTable();
        if (this.isEmpty()) {
            System.out.printf("|%70s%50s%n", "VEHICLE NOT FOUND!", "|");
            closeTable();
            return;
        }
        for (Vehicle v : this) {
            printData(v);
        }
        closeTable();
    }

    //Function to put in displayMenu to filter price
    public VehicleList filterPrice(double price) {
        VehicleList L = new VehicleList();
        Comparator<Vehicle> cPriceDesc = new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return -Double.compare(o1.getPrice(), o2.getPrice());
            }
        };
        for (Vehicle v : this) {
            if (v.getPrice() < price) {
                L.add(v);
            }
        }
        //Sort the price descendingly with comparator cPriceDesc
        Collections.sort(L, cPriceDesc);
        return L;

    }

    //Function to use in displayMenu to filter year
    public VehicleList filterYear(int year) {
        VehicleList L = new VehicleList();
        Comparator<Vehicle> cYearDesc = new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                int d = o1.getProductYear() - o2.getProductYear();
                return (d > 0) ? -1 : (d < 0) ? 1 : 0;
            }
        };
        for (Vehicle v : this) {
            if (v.getProductYear() >= year) {
                L.add(v);
            }
        }
        //Sort the year descensingly by using the comparator cYearDesc
        Collections.sort(L, cYearDesc);
        return L;
    }

    //Function 7: Save all vehicle to file
    public void saveVehicleToFile(String fName) {
        if (this.isEmpty()) {
            System.out.println("Empty List");
            return;
        }
        try {
            File f = new File(fName);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Vehicle v : this) {
                pw.println(v.toString());
            }
            System.out.println("All vehicles saved!");
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
