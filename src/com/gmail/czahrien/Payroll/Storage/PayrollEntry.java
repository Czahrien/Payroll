/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.czahrien.Payroll.Storage;

import java.util.HashSet;

/**
 *
 * @author Czahrien
 */
public class PayrollEntry {
    public long myNextPayout;
    public long myPayoutTime;
    public HashSet<String> players;
    
    public void payOut() {
        if(System.currentTimeMillis() > myNextPayout ) {
            myNextPayout += myPayoutTime;
        }
    }
}
