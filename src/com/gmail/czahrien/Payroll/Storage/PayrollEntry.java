/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.czahrien.Payroll.Storage;

import com.gmail.czahrien.Payroll.util.Paginatable;
import com.gmail.czahrien.Payroll.util.PayrollUtil;
import java.io.Serializable;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Czahrien
 */
public class PayrollEntry implements Serializable, Paginatable {
    public String myName;
    public long myNextPayout;
    public long myPayoutTime;
    public HashSet<String> myPlayers;
    private LinkedList<String> myCommands;
    
    public PayrollEntry(String name, long delay) {
        myName = name;
        myPayoutTime = delay;
        myNextPayout = System.currentTimeMillis() + delay;
        myPlayers = new HashSet<>();
        myCommands = new LinkedList<>();
        PayrollStorage.getInstance(null).myPayrolls.put(myName, this);
    }
    
    public void payout() {
        payout(false);
    }
    
    public void payout(boolean forced) {
        if(forced || myPayoutTime > 0 && System.currentTimeMillis() > myNextPayout ) {
            myNextPayout += myPayoutTime;
            for(String name : myPlayers) {
                ListIterator<String> cmds = myCommands.listIterator();
                while(cmds.hasNext()) {
                    String command = cmds.next();
                    command.replaceAll("#NAME#", name);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
                }
            }
        }
    }
    
    public void addPlayer(Player p) {
        myPlayers.add(p.getName());
    }
    
    public boolean removePlayer(Player p) {
        return myPlayers.remove(p.getName());
    }
    
    public void addPlayer(String p) {
        myPlayers.add(p);
    }
    
    public boolean removePlayer(String p) {
        return myPlayers.remove(p);
    }
    
    public void setPayoutTime(long time) {
        myPayoutTime = time;
    }
    
    public void reset() {
        myNextPayout = System.currentTimeMillis() + myPayoutTime;
    }
    
    public void addCommand(String cmd) {
        myCommands.addLast(cmd);
    }
    
    public boolean addCommand(int index, String cmd) {
        if(index >= 0 && index <= myCommands.size() ) {
            myCommands.add(index, cmd);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean removeCommand() {
        if(myCommands.size() > 0) {
            myCommands.removeFirst();
            return true;
        } 
        return false;
    }
    public boolean removeCommand(int index) {
        if(index >= 0 && index <= myCommands.size() ) {
            myCommands.remove(index);
            return true;
        } else {
            return false;
        }
    }
    public void setName(String newName) {
        TreeMap<String,PayrollEntry> ps = PayrollStorage.getInstance(null).myPayrolls;
        ps.remove(myName);
        myName = newName;
        ps.put(myName, this);
    }
    
    @Override
    public String[] getMessage() {
        String[] ret = new String[2];
        ret[0] = ChatColor.BLUE + myName + " - " + ChatColor.GOLD + "Payout every " + PayrollUtil.timeToString(myPayoutTime);
        ret[1] = ChatColor.GOLD + "    Next payout in " + PayrollUtil.timeToString(myNextPayout - System.currentTimeMillis());
        return ret;
    }
}
