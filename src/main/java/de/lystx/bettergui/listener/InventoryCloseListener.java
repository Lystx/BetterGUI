package de.lystx.bettergui.listener;

import de.lystx.bettergui.Inventory;
import de.lystx.bettergui.events.BetterInventoryCloseEvent;
import de.lystx.bettergui.main.BetterGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inv = BetterGUI.getInstance().getInventoryService().getInventories().get(player);
        if (inv != null && inv.getPlayer().equals(player)) {
            if (!inv.isClickable()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(BetterGUI.getInstance(), inv::open, 1L);
            } else {
                if (player.getOpenInventory().getTitle().equalsIgnoreCase(inv.getName())) {
                    inv.close();
                }
            }
            if (inv.getCloserunnable() != null) {
                inv.getCloserunnable().run();
            }
            Bukkit.getPluginManager().callEvent(new BetterInventoryCloseEvent(player, inv));
        }
    }
}
