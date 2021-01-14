package de.lystx.bettergui.listener;

import de.lystx.bettergui.events.InvCloseEvent;
import de.lystx.bettergui.main.BetterGUI;
import de.lystx.bettergui.utils.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
       Inventory inv = Inventory.getInventory(player);
        if (inv != null && inv.getPlayer().equals(player)) {
            if (!inv.getCloseable()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(BetterGUI.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        inv.open();
                    }
                }, 1L);
            } else {
                if (player.getOpenInventory().getTitle().equalsIgnoreCase(inv.getName())) {
                    inv.close();
                }
            }
            if (inv.getCloserunnable() != null) {
                inv.getCloserunnable().run();
            }
            Bukkit.getPluginManager().callEvent(new InvCloseEvent(player, inv));
        }
    }
}
