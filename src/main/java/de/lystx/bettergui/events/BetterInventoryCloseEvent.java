package de.lystx.bettergui.events;

import de.lystx.bettergui.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class BetterInventoryCloseEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Inventory inventory;


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
