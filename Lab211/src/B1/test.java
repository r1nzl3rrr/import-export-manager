/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B1;

import Tools.MyTools;
import static Tools.MyTools.*;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author khanh
 */
public class test {
    public static void main(String[] args) {
        System.out.println("Test boolean string:");//Test boolean string
        System.out.println(parseBoolean("     TrUE     "));
        System.out.println(parseBoolean("     fALSe     "));
        System.out.println(parseBoolean("     1234     "));
        System.out.println(parseBoolean("   01 23     "));
        System.out.println(parseBoolean("       total   "));
        System.out.println(parseBoolean("     cosine     "));
        System.out.println("Test normalizeDateStr(String): ");
        String S = "   7 ...  ---   2 / 2023    ";
        System.out.println(S + " --> " + normalizeDateStr(S));
        S = "   7 ...  2 //// 2023    ";
        System.out.println(S + " --> " + normalizeDateStr(S));
        //Tests date <--> String
        System.out.println("\nTest Date <--> String:");
        String[] formats = {"yyyy-MM-dd", "MM-dd-yyyy", "dd-MM-yyyy"};
        String[] dStrs = {"  2023-02-21  ", "   12.--- 25 - 2023 ","   7 .. 2// 2023"};
        Date d = null;
        for(int i = 0; i < 3; i++){
            System.out.println(dStrs[i] + "(" + formats[i] + ")");
            d=parseDate(dStrs[i], formats[i]);
            if(d!=null){
                System.out.println("Year: " + getPart(d, Calendar.YEAR));
                System.out.println("----> Result: " + d);
                System.out.println("----> In the format " + formats[i] + ": " + MyTools.toString(d, formats[i]));
            }
            else System.out.println("Parsing error!");
        }
        // Test reading a boolean
        System.out.println("Test reading a boolean data");
        boolean b = readBoolean("Input a boolean data (T/F, 1/0, Y/N)");
        System.out.println(b + " inputted");

        //Test input a date data
        System.out.print("Test input a date data");
        d = readDate("Input a data data (dd-mm-yyyy)", "dd-MM-yyyy");
        System.out.println("Inputted date:");
        System.out.println("In format dd-MM-yyyy: "+ MyTools.toString (d, "dd-MM-yyyy"));
        System.out.println("In format MM-dd-yyyy: "+ MyTools.toString (d, "MM-dd-yyyy"));
        System.out.println("In format yyyy-MM-dd: " + MyTools.toString (d, "yyyy-MM-dd"));

        // Test inputting a phone number including 9..11 digits
        String phoneNo = readStr("Phone number (9..11 digits)", "[\\d]{9,11}");
        System.out.println("Inputted phone no. :"+ phoneNo);
        //Testing for generating an automatic code
        System.out.println("Testing for generating an automatic code");
        //Declare variables
        String prefix = "P";
        int curNumber = 25;
        int len = 7;
        //Generate the code
        String code = generateCode(prefix, len, curNumber);
        //Print the generated code
        System.out.println("Generated code: " + code);
        //Increment the current number
        curNumber++;
        //Generate the code again
        code = generateCode(prefix, len, curNumber);
        //Print the generated code
        System.out.println("Generated code: " + code);
        
        //Test reading date data before and after today
        System.out.println("Testing reading date data before and after today");
        //Declare a variable today of type Date
        Date today = new Date();
        //Print the value of today
        System.out.println("Today: "+ today.toString());
        //Declare a variable dBefore of type Date
        Date dBefore = MyTools.readDateBefore ("Date before today", "dd-MM-yyyy", today);
        //Print the value of dBefore
        System.out.println(MyTools.toString (dBefore, "dd-MM-yyyy"));
        //Declare a variable dAfter of type Date
        Date dAfter = MyTools.readDateAfter ("Date after today", "dd-MM-yyyy", today);
        //Print the value of dAfter
        System.out.println (MyTools.toString (dAfter, "dd-MM-yyyy"));

        
        
    }
}
