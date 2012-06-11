package com.gmail.czahrien.Payroll.Command;

import com.gmail.czahrien.Payroll.Plugin.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PayrollCommand implements CommandExecutor {

    PayrollPlugin myPlugin;
    
    public PayrollCommand(PayrollPlugin p) {
        myPlugin = p;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            switch(cmd.getName().toLowerCase()) {
                case "payroll":
                    if(sender.hasPermission("Payroll.user") && args.length >= 1) {
                        // TODO: List payrolls user is a part of.
                        return true;
                    }
                    break;
                case "payrolladmin":
                    if(sender.hasPermission("Payroll.admin") && args.length >= 1) {
                        switch(args[0]) {
                            case "ls":
                                // TODO: List (pages of 5) payrolls.
                                break;
                            case "add":
                                // TODO: Create new payroll.
                                break;
                            case "adduser":
                                // TODO: Add user to existing payroll
                                break;
                            case "rm":
                                // TODO: Remove payroll.
                                break;
                            case "rmuser":
                                // TODO: Remove a user from payroll.
                                break;
                            case "set":
                                // TODO: set <payroll> <option> <arg>
                                break;
                        }
                    }
                    break;
            }
            
        return false;
    }

}
