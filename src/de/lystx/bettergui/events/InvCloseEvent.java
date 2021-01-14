package de.lystx.bettergui.events;

import de.lystx.bettergui.utils.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InvCloseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Inventory inventory;

    public InvCloseEvent(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
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
