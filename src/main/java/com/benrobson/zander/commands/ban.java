package com.benrobson.zander.commands;

import com.benrobson.zander.ZanderMain;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ban implements CommandExecutor {
    ZanderMain plugin;

    public ban(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("zander.ban")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyplayerandreasoning")));
                return true;
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyreason")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);

                StringBuilder str = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    str.append(args[i] + " ");
                }

                String banner = "CONSOLE";
                if (sender instanceof Player) {
                    banner = sender.getName();
                }

                if (target == null) {
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(args[0], ChatColor.WHITE + str.toString().trim(), null, banner);
                } else {
                    target.getServer().getBanList(BanList.Type.NAME).addBan(target.getDisplayName(), ChatColor.WHITE + str.toString().trim(), null, banner);
                    target.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.title")) + "\n" + ChatColor.YELLOW + "You have been banned by " + ChatColor.RESET +  banner + "\n" + "Reason: " + ChatColor.YELLOW +  str.toString().trim());
                }

                //
                // Database Query
                // Add new punishment to database.
                //
                try {
                    PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO gamepunishments (punisheduser_id, punisher_id, punishtype, reason) values ((select id from playerdata where username = ?), (select id from playerdata where username = ?), 'BAN', ?);");

                    insertstatement.setString(1, args[0]);
                    insertstatement.setString(2, banner);
                    insertstatement.setString(3, str.toString().trim());

                    insertstatement.executeUpdate();
                    plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.developmentprefix")) + " " + args[0] + " has been banned. Adding punishment record to database.");

                    for (Player p : Bukkit.getOnlinePlayers()){
                        if (p.hasPermission("zander.punishnotify")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.punishmentprefix")) + " " + args[0] + " has been banned by " + banner + " for " + str.toString().trim());
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.playernotexist")));
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        return true;
    }
}
