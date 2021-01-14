package de.lystx.bettergui.listener;

import de.lystx.bettergui.events.InvClickEvent;
import de.lystx.bettergui.utils.Inventory;
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
        if (Inventory.invS.containsKey(player) && inventory.equals(Inventory.invS.get(player).getInventory())) {
            if (!Inventory.invS.get(player).getClickable()) {
                event.setCancelled(true);
            }
            Bukkit.getPluginManager().callEvent(new InvClickEvent(player, Inventory.invS.get(player), event.getSlot(), event.getCurrentItem()));
            if (Inventory.invS.get(player).getRunnable().containsKey(event.getRawSlot())) {
                Inventory.invS.get(player).getRunnable().get(event.getRawSlot()).run();
            }
        }
    }
}
