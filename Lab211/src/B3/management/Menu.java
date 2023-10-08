/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B3.management;
import java.util.Scanner;
import java.util.List;
import Tools.MyTools;
/**
 *
 * @author khanh
 */
public class Menu {
    public final static Scanner sc = new Scanner(System.in);
    
    public static int getChoiceInt(String... options){
        int L = options.length;
        for(int i=0; i < L; i++){
            System.out.println((i+1) + "-" + options[i]);
        }
        System.out.println("-----------------------------------");
        return Integer.parseInt(MyTools.readStr("Choose (1.." + L + ")", "\\d+"));
    }
    
    public static Object getChoiceObject(List list){
        int choice;
        int L = list.size();
        int i;
        for(i = 0; i < L; i++){
            System.out.println((i+1) + '-' + list.get(i).toString());
        }
        choice = Integer.parseInt(sc.nextLine());
        return (choice > 0 && choice <= L) ? list.get(choice - 1) : null;
    }
}
