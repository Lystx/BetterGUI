package de.lystx.bettergui.service;

import de.lystx.bettergui.Inventory;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class InventoryService {

    private final HashMap<Player, Inventory> inventories;
    private final HashMap<Inventory,  HashMap<Integer, Runnable>> runnables;

    public InventoryService() {
        this.inventories = new HashMap<>();
        this.runnables = new HashMap<>();
    }

}
