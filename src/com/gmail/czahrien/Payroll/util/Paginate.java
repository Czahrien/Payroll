/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.czahrien.Payroll.util;

import java.util.Collection;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Czahrien
 */
public class Paginate {
    public static <E extends Paginatable> void paginator(Collection<E> c, CommandSender sender, 
            int page, int num_per_page) {
        int pages = c.size()/num_per_page + (c.size() % num_per_page > 0 ? 1 : 0);
        if(page < 1) {
            sender.sendMessage(ChatColor.RED + "The page number must be a positive number!");
        } else if (page > pages) {
            sender.sendMessage( ChatColor.RED + 
                    (pages > 0 ? ("You asked for page " + page +" but there are only " + pages + " pages!") 
                                 : "There are no pages to display!"));
        } else {
            int i = 0;
            for(E e : c) {
                if(i < num_per_page * (page - 1)) {
                    ++i;
                } else if(i < num_per_page*page){
                    ++i;
                    sender.sendMessage(e.getMessage());
                } else {
                    break;
                }
            }
        }
    }
}
