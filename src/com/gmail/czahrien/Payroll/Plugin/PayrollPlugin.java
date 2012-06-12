package com.gmail.czahrien.Payroll.Plugin;

import com.gmail.czahrien.Payroll.Command.*;
import com.gmail.czahrien.Payroll.Storage.PayrollStorage;
import org.bukkit.plugin.java.JavaPlugin;


public class PayrollPlugin extends JavaPlugin {
    private PayrollCommand myCommands;
    private PayrollStorage myStorage;

    @Override
    public void onEnable() {
        // Check payroll payouts every minute.
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                payoutCheck();
            }
        }, 0L, 1200L);
        
        myStorage = PayrollStorage.getInstance(this);
        myCommands = new PayrollCommand(this);
        getCommand("payroll").setExecutor(myCommands);
        getCommand("payrolladmin").setExecutor(myCommands);
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public void payoutCheck() {
        
    }
}
