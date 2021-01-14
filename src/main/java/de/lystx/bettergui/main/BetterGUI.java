package de.lystx.bettergui.main;

import de.lystx.bettergui.Inventory;
import de.lystx.bettergui.listener.InventoryClickListener;
import de.lystx.bettergui.listener.InventoryCloseListener;
import de.lystx.bettergui.service.InventoryService;
import de.lystx.bettergui.service.ItemService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterGUI extends JavaPlugin {

    public static BetterGUI instance;
    private InventoryService inventoryService;


    @Override
    public void onEnable() {
        instance = this;
        this.inventoryService = new InventoryService();
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
    }

    public static BetterGUI getInstance() {
        return instance;
    }


    public InventoryService getInventoryService() {
        return inventoryService;
    }
}
