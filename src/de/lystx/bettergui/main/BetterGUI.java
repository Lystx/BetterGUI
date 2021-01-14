package de.lystx.bettergui.main;

import de.lystx.bettergui.listener.InventoryClickListener;
import de.lystx.bettergui.listener.InventoryCloseListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterGUI extends JavaPlugin {

    public static BetterGUI instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
    }

    public static BetterGUI getInstance() {
        return instance;
    }
}
