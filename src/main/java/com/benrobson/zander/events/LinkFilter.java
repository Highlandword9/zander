package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LinkFilter implements Listener {
    private ZanderMain plugin;
    public LinkFilter(ZanderMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void LinkFilter(AsyncPlayerChatEvent event) {
//        Player player = event.getPlayer();
//
//        List<String> FilteredLinks = plugin.configurationManager.getlinksfilter().getStringList("filteredlinks");
//        String message = event.getMessage();
//
//        for (String link: FilteredLinks){
//            if (message.toLowerCase().matches("^" + link + "$")){
//                player.sendMessage(ChatColor.RED + plugin.configurationManager.getlang().getString("punish.linkfilteringameprompt"));
//                event.setCancelled(true);
//            }
//        }
    }
}
