package Tools;


import java.text.DateFormat;        //formatting date
import java.text.ParseException;    //Handling parse exception
import java.text.SimpleDateFormat;  //formatting date
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Calendar;          //general calendar
import java.util.Date;              //describe date
import java.util.GregorianCalendar; //calendar now
import java.util.Scanner;           //input

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khanh
 */ 
public class MyTools {
    public static final Scanner sc = new Scanner(System.in);
    
    /**
     * Parsing the input string to get a boolean data (true/false)
     * @param input: input string
     * @return true if the first character in input is 'T' or '1' or 'Y'
     */
    
    public static boolean parseBoolean(String input){
        input = input.trim().toUpperCase();// normalize input
        char c = input.charAt(0);//Take the first letter
        return c=='T' || c=='1' || c=='Y';//return true if the first character is T, 1, Y
    }
    
    /**
     * Normalizing a date string: Using '-' to separate date parts only
     * @param dateStr: input date string
     * @return new string
     */
    //"  9 ... -- 3 ///2023   " -->  "9-3-2023"
    public static String normalizeDateStr(String dateStr){
        // Remove or whitespace in the input string. Use replaceAll() with proper regex
        String result = dateStr.replaceAll("[\\s]+", "");
        // Replace all character . / - with '-' with replaceAll()
        result = result.replaceAll("[./-]+", "-");
        return result;
          
    }
    /**
     * Parsing the input string to date data
     * @param inputStr: date string input.
     * @param dateFormat: chosen date format.
     * @return Date object if successful and null if failed
     */
    public static Date parseDate(String inputStr, String dateFormat){
        inputStr = normalizeDateStr(inputStr);//normalize date string
        DateFormat formatter = new SimpleDateFormat(dateFormat);// Create a formatter
        try{ //Use formatter parse to return the result
            formatter.setLenient(false);
            return formatter.parse(inputStr);
        }
        catch (ParseException e){
            System.out.println(e);
        }
        return null; //if parse exception occurs return null

    }
   
    /**
     * Convert a Date object to string using a given date format
     * @param date: Date object
     * @param dateFormat: chosen date format
     * @return date string in the given format
     */
    
    public static String toString(Date date, String dateFormat){
        if(date==null) return "";
        // Create a formatter to work with the second parameter
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        //return the result after formatting
        return formatter.format(date);
    }
    /**
     * Getting year of date data
     * @param d: Date data
     * @param calendarPart: date part is declared in the class Calendar
     * @return year in date data
     */
    
    public static int getPart(Date d, int calendarPart){
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(calendarPart);
    }
    //--- METHODS FOR READING DATA FROM KEYBOARD
    /** Reading a boolean data */
    public static boolean readBoolean(String prompt){
        System.out.print(prompt + ": ");
        return parseBoolean(sc.nextLine());
    }
    /**
     * Reading a string using a pre-defined pattern
     * @param prompt: prompt will be printed before inputting
     * @param pattern: pre-defined pattern of input
     * @return an input string which matches the pattern
     */
    public static String readStr(String prompt, String pattern){
        String input;
        boolean valid = false;
        do{
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            valid = input.matches(pattern);
        }
        while(valid == false);
        return input;
    }
    /**
     * Reading a date data using a pre-defined date format
     * dd-MM-yyyy / MM-dd-yyyy / yyyy-MM-dd
     * @param prompt: prompt will be printed before inputting
     * @param dateFormat: pre-defined pattern of input
     * @return an input string which matches the pattern.
     */
    public static Date readDate(String prompt, String dateFormat){
        String input;
        Date d;
        do{
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            d = parseDate(input, dateFormat);
            if(d == null) System.out.println("Date data is not valid!");
        }
        while(d==null);
        return d;
    }
    //Enter a date after a date
    public static Date readDateAfter(String prompt, String dateFormat, Date markerDate){
        String input;
        Date d;
        boolean ok = false;
        do{
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            d = parseDate(input, dateFormat);
            ok = (d != null && d.after(markerDate));
            if(d == null) System.out.println("Date data is not valid!");
        }
        while(!ok);
        return d;
    }
    //Enter a date before a date
    public static Date readDateBefore(String prompt, String dateFormat, Date markerDate){
        String input;
        Date d;
        boolean ok = false;
        do{
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();
            d = parseDate(input, dateFormat);
            ok = (d != null && d.before(markerDate));
            if(d == null) System.out.println("Date data is not valid!");
        }
        while(!ok);
        return d;
    }
    //Automatically generating an increasing code
    public static String generateCode(String prefix, int length, int curNumber){
        String formatStr = "%0" + length + "d";
        return prefix + String.format(formatStr, curNumber);
    }
    
    //Captilizing first letter of a word in a string
    public static String capWords(String str){
        if(str.isEmpty()){
            return "";
        }
        str = str.trim();
        String arr[] = str.split("[\\s]+");
        StringBuilder word;
        for(int i = 0; i < arr.length; i++){
            word = new StringBuilder();
            word.append(arr[i].substring(0, 1).toUpperCase()).append(arr[i].substring(1).toLowerCase());
            arr[i] = word.toString();
        }
        String result = String.join(" ", arr);
        return result;
    }
}
