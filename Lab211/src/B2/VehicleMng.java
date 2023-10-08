/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B2;
import static B2.Menu.*;
import Tools.MyTools;
/**
 *
 * @author khanh
 */
public class VehicleMng {
    public static void main(String[] args) {
        VehicleList list = new VehicleList();
        String filename = "./data/vehicle.dat";
        list.LoadVehicleFromFile(filename);
        int choice;
        boolean changed = false;
        
        do{
            System.out.println();
            choice = getChoiceInt("Add new vehicle", "Check exist vehicle", "Update vehicle", "Delete vehicle", "Search vehicles", "Display vehicles", "Save all vehicles to file", "Quit");
            
            switch(choice){
                case 1:{
                    list.addVehicle();
                    changed = true;
                    break;
                }
                case 2:{
                    VehicleList L = new VehicleList();
                    L.LoadVehicleFromFile(filename);
                    L.checkExist();
                    break;
                }
                case 3:{
                    list.updateVehicle();
                    changed = true;
                    break;
                }
                case 4:{
                    list.deleteVehicle();
                    changed = true;
                    break;
                }
                case 5:{
                    list.searchMenu();
                    break;
                }
                case 6:{
                    list.displayMenu();
                    break;
                }
                case 7:{
                    list.saveVehicleToFile(filename);
                    changed = false;
                    break;
                }
                default:{
                    if(changed){
                        boolean b = MyTools.readBoolean("Data changed, do you want to save to file? (Y/N)");
                        if(b==true) list.saveVehicleToFile(filename);
                    }
                    System.out.println("Program exited!");
                }
            }
        }while((choice >= 1 && choice <= 7));
    }
}
