/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.czahrien.Payroll.util;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Czahrien
 */
public class PayrollUtil {
    /**
     * Takes a string in the format (-?([1-9][0-9]*)[SMHDW])* and returns a time
     * corresponding to that string.
     * @param time The string to convert to a time.
     * @return -1 if the string was unparsable or represented a time less than
     * 1000 milliseconds.
     */
    public static long stringToTime(String time) {
        long ret = 0;
        String[] times = time.split("[\\s,]+");
        for( int i = 0; i < times.length; ++i ) {
            try{
                long num = Long.parseLong(time.substring(0, times[i].length() - 1));
                switch(times[i].charAt(times[i].length()-1)) {
                    case 'S':
                        break;
                    case 'M':
                        num *= 60;
                        break;
                    case 'H':
                        num *= 3600;
                        break;
                    case 'D':
                        num *= 86400;
                        break;
                    case 'W':
                        num *= 604800;
                        break;
                    default:
                        return -1;
                }
                ret += num;
            } catch(NumberFormatException e) {
                return -1;
            }
        }
                
        if(ret > 1) {
            return 1000*ret;
        } else {
            return -1;
        }
    }
    
    /**
     * Converts a number of milliseconds
     * @param millis
     * @return 
     */
    public static String timeToString(long millis) {
        String ret = "";
        LinkedList<String> lst = new LinkedList<>();
        long rem = millis/1000;

        if(rem == 0) {
            return "none";
        }
        
        long tmp;
        tmp = rem % 60;
        rem /= 60;
        if(tmp != 0) {
            String s = tmp > 1 ? "s" : "";
            lst.addFirst(tmp + " second" + s);
        }
        tmp = rem % 60;
        rem /= 60;
        if(tmp != 0) {
            String s = tmp > 1 ? "s" : "";
            lst.addFirst(tmp + " minute" + s);
        }
        tmp = rem % 24;
        rem /= 24;
        if(tmp != 0) {
            String s = tmp > 1 ? "s" : "";
            lst.addFirst(tmp + " hour" + s);
        }
        tmp = rem % 7;
        rem /= 7;
        if(tmp != 0) {
            String s = tmp > 1 ? "s" : "";
            lst.addFirst(tmp + " day" + s);
        }
        if(rem != 0) {
            String s = tmp > 1 ? "s" : "";
            lst.addFirst(tmp + " week" + s);
        }
        ListIterator<String> i = lst.listIterator();
        while(i.hasNext()) {
            ret += i.next();
        }
        return ret;
    }
}
