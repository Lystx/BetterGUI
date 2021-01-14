package de.lystx.bettergui.events;

import de.lystx.bettergui.utils.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class InvClickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Inventory inventory;
    private Integer slot;
    private ItemStack itemStack;

    public InvClickEvent(Player player, Inventory inventory, Integer slot, ItemStack itemStack) {
        this.player = player;
        this.slot = slot;
        this.inventory = inventory;
        this.itemStack = itemStack;
    }


    public ItemStack getItem() {
        return itemStack;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Integer getSlot() {
        return slot;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
