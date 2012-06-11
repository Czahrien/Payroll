package com.gmail.czahrien.Payroll.Plugin;

import com.gmail.czahrien.Payroll.Command.*;
import org.bukkit.plugin.java.JavaPlugin;


public class PayrollPlugin extends JavaPlugin {
    private PayrollCommand myCommands;

    @Override
    public void onEnable() {
        // Check payroll payouts every minute.
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                payoutCheck();
            }
        }, 0L, 1200L);
        
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
