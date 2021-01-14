package de.lystx.bettergui.listener;

import de.lystx.bettergui.Inventory;
import de.lystx.bettergui.events.BetterInventoryClickEvent;
import de.lystx.bettergui.main.BetterGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if (event.getRawSlot() > event.getInventory().getSize()) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        org.bukkit.inventory.Inventory inventory = event.getClickedInventory();
        if (BetterGUI.getInstance().getInventoryService().getInventories().containsKey(player) && inventory.equals(BetterGUI.getInstance().getInventoryService().getInventories().get(player).getInventory())) {
            Inventory inv = BetterGUI.getInstance().getInventoryService().getInventories().get(player);
            if (!inv.isClickable()) {
                event.setCancelled(true);
            }
            Bukkit.getPluginManager().callEvent(new BetterInventoryClickEvent(player, inv, event.getSlot(), event.getCurrentItem()));
            if (BetterGUI.getInstance().getInventoryService().getRunnables().get(inv).containsKey(event.getRawSlot())) {
                BetterGUI.getInstance().getInventoryService().getRunnables().get(inv).get(event.getRawSlot()).run();
            }
        }
    }
}
