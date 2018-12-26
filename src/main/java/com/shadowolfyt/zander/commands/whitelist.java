package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.guis.WhitelistGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class whitelist implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("zander.whitelist")) {
            if (args.length == 0) {
                new WhitelistGUI(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("add")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                target.setWhitelisted(true);
                Bukkit.getServer().reloadWhitelist();
                player.sendMessage(ChatColor.GREEN + target.getName() + " has been added to the whitelist.");
                return true;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                target.setWhitelisted(false);
                Bukkit.getServer().reloadWhitelist();
                player.sendMessage(ChatColor.RED + target.getName() + " has been removed from the whitelist.");
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        return true;
    }
}