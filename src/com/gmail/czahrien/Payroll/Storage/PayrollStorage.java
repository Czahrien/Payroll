package com.gmail.czahrien.Payroll.Storage;

import com.gmail.czahrien.Payroll.Plugin.PayrollPlugin;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.TreeMap;

/**
 *
 * @author Czahrien
 */
public class PayrollStorage implements Serializable {
    
    private static transient PayrollStorage instance = null;
    public static transient PayrollPlugin plugin = null;
    
    public TreeMap<String,PayrollEntry> myPayrolls;
    
    public static PayrollStorage getInstance(PayrollPlugin p) {
        if(instance == null) {
            instance = loadFile(p);
            plugin = p;
        }
        return instance;
    }
    
    private static PayrollStorage loadFile(PayrollPlugin p) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PayrollStorage.dat"));
            PayrollStorage prs = ((PayrollStorage)ois.readObject());
            ois.close();
            return prs;
        } catch(ClassNotFoundException e) {
            p.getLogger().info("Invalid data file. Creating new PayrollStorage from scratch.");
        } catch(FileNotFoundException e) {
            p.getLogger().info("No data file found. Creating new PayrollStorage from scratch.");
        } catch(IOException e) {
            p.getLogger().info("Error reading data file.");
        }
        return new PayrollStorage();
    }
    
    public void checkPayouts() {
        for(PayrollEntry pe : myPayrolls.values()) {
            pe.payout();
        }
    }
    
    private PayrollStorage() {
        myPayrolls = new TreeMap<>();
    }
    
    public void save() {
        try {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("PayrollStorage.dat"));
           oos.writeObject(this);
           oos.close();
        } catch(IOException e) {
            plugin.getLogger().info("Error saving PayrollStorage!");
        }
    }
}
