package de.lystx.bettergui.events;

import de.lystx.bettergui.Inventory;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;


@Getter
public class BetterInventoryClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Inventory inventory;
    private final Integer slot;
    private final ItemStack itemStack;

    public BetterInventoryClickEvent(Player player, Inventory inventory, Integer slot, ItemStack itemStack) {
        this.player = player;
        this.inventory = inventory;
        this.slot = slot;
        this.itemStack = itemStack;
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
