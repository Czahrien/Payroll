package com.gmail.czahrien.Payroll.Command;

import com.gmail.czahrien.Payroll.Plugin.*;
import com.gmail.czahrien.Payroll.Storage.PayrollEntry;
import com.gmail.czahrien.Payroll.Storage.PayrollStorage;
import com.gmail.czahrien.Payroll.util.Paginate;
import com.gmail.czahrien.Payroll.util.PayrollUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayrollCommand implements CommandExecutor {

    PayrollPlugin myPlugin;
    PayrollStorage myPayrollStorage;
    
    public PayrollCommand(PayrollPlugin p) {
        myPlugin = p;
        myPayrollStorage = PayrollStorage.getInstance(p);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            switch(cmd.getName().toLowerCase()) {
                case "payroll":
                    if(sender.hasPermission("Payroll.user")) {
                        if(!(sender instanceof Player)) {
                            sender.sendMessage(ChatColor.RED + "This command is only valid for players!");
                            return true;
                        }
                        int page = 1;
                        if(args.length > 1) {
                            try {
                                page = Integer.parseInt(args[1]);
                            } catch(NumberFormatException e) {
                                sender.sendMessage(ChatColor.RED + "Invalid page number: " + args[1]);
                            }
                        }
                        Collection<PayrollEntry> v = myPayrollStorage.myPayrolls.values();
                        LinkedList<PayrollEntry> usersPayrolls = new LinkedList<>();
                        for(PayrollEntry e : v) {
                            if(e.myPlayers.contains(sender.getName())) {
                                usersPayrolls.add(e);
                            }
                        }
                        Paginate.paginator(usersPayrolls, sender, page, 5);
                        return true;
                    }
                    break;
                case "payrolladmin":
                    if(sender.hasPermission("Payroll.admin") && args.length >= 1) {
                        switch(args[0]) {
                            case "ls":
                                int page = 1;
                                if(args.length > 1) {
                                    try {
                                        page = Integer.parseInt(args[1]);
                                    } catch(NumberFormatException e) {
                                        sender.sendMessage(ChatColor.RED + "Invalid page number: " + args[1]);
                                    }
                                }
                                Paginate.paginator(myPayrollStorage.myPayrolls.values(), sender, page, 5);
                                return true;
                            case "add":
                               if(args.length > 2) {
                                   //TODO: Annoying things I don't want to do at the moment.
                               }
                                break;
                            case "adduser":
                                if(args.length == 3) {
                                    PayrollEntry e = myPayrollStorage.myPayrolls.get(args[1]);
                                    if(e == null) {
                                        sender.sendMessage(ChatColor.RED + "No such payroll: " + args[1]);
                                        return false;
                                    }
                                    Player p = Bukkit.getPlayer(args[2]);
                                    if(p == null) {
                                        sender.sendMessage(ChatColor.RED + "No such player: " + args[2]);
                                        return false;
                                    }
                                    e.addPlayer(p);
                                    sender.sendMessage(ChatColor.AQUA + args[2] + " removed added successfully to " + args[1] + "!");

                                    return true;
                                } 
                                break;
                            case "rm":
                                // TODO: Remove payroll.
                                break;
                            case "rmuser":
                                if(args.length == 3) {
                                    PayrollEntry e = myPayrollStorage.myPayrolls.get(args[1]);
                                    if(e == null) {
                                        sender.sendMessage(ChatColor.RED + "No such payroll: " + args[1]);
                                        return false;
                                    }
                                    Player p = Bukkit.getPlayer(args[2]);
                                    if(p == null) {
                                        sender.sendMessage(ChatColor.RED + "No such player: " + args[2]);
                                        return false;
                                    }
                                    if(e.removePlayer(p)) {
                                        sender.sendMessage(ChatColor.AQUA + args[2] + " removed successfully from " + args[1] + "!");
                                    } else {
                                        sender.sendMessage(ChatColor.RED + args[2] + " is not in the payroll " + args[1] + "!" );
                                    }
                                    return true;
                                } 
                                break;
                            case "set":
                                // TODO: set <payroll> <option> <arg(s)>
                                break;
                        }
                    }
                    break;
            }
            
        return false;
    }

}
