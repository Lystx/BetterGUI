package de.lystx.bettergui;

import com.google.common.collect.Lists;
import de.lystx.bettergui.main.BetterGUI;
import de.lystx.bettergui.service.ItemService;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class Inventory {

    private org.bukkit.inventory.Inventory inventory;
    private InventoryType type;

    private boolean clickable;
    private boolean closable;
    private Player player;
    private Runnable closerunnable;
    private int scheduler;
    private int schedulerByte;
    private int schedulerAnimate;
    private Iterator<String> iterator;



    public Inventory(Player player, String name, InventoryType type) {
        this(player, name, 3);
        this.type = type;
        this.inventory = Bukkit.createInventory(player, type, name);
    }

    public Inventory(Player player, String name, Integer rows) {
        this.player = player;
        this.clickable = true;
        this.closable = true;
        this.type = InventoryType.CHEST;
        this.inventory = Bukkit.createInventory(player, rows*9, name);
    }



    public Runnable getRunnable(Integer slot) {
        return BetterGUI.getInstance().getInventoryService().getRunnables().getOrDefault(this, new HashMap<>()).getOrDefault(slot, null);
    }

    public String getName() {
        return this.inventory.getTitle();
    }

    public Integer getSize() {
        return this.inventory.getSize();
    }


    public void setRand() {
        setRand(new ItemService(Material.STAINED_GLASS_PANE, (short)7).setNoName().build());
    }

    public ItemStack getItem(Integer slot) {
        return this.inventory.getItem(slot);
    }

    public List<ItemStack> getContentsAsList() {
        List<ItemStack> list = Lists.newLinkedList();
        list.addAll(Arrays.asList(getContents()));
        return list;
    }
    public ItemStack[] getContents() {
        return this.inventory.getContents();
    }

    public void rainBow(Long delay) {
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(BetterGUI.getInstance(), () -> {
            ItemStack item = new ItemService(Material.STAINED_GLASS_PANE, (short) schedulerByte).setNoName().build();
            setRand(item);
            if (schedulerByte >= 15) {
                schedulerByte = 0;
            }
            schedulerByte++;
        }, 0, delay);
    }

    public void animate(String[] animations, Long delay) {
        List<String> animation = Arrays.asList(animations);
        iterator = animation.iterator();
        schedulerAnimate = Bukkit.getScheduler().scheduleAsyncRepeatingTask(BetterGUI.getInstance(), () -> {
            if (iterator.hasNext()) {
                changeName(iterator.next());
            } else {
                iterator = animation.iterator();
            }
        }, 0, delay);
    }

    public void stopAnimate() {
        if (Bukkit.getScheduler().isCurrentlyRunning(schedulerAnimate)) {
            Bukkit.getScheduler().cancelTask(schedulerAnimate);
        }
    }

    public void stopRainbow() {
        if (Bukkit.getScheduler().isCurrentlyRunning(scheduler)) {
            Bukkit.getScheduler().cancelTask(scheduler);
        }
    }

    public void setRand(ItemStack itemStack) {
        IntStream.range(0, 8).forEach(i -> {
            this.setItem(i, itemStack);
        });
        for (int s = 8; s < (this.getSize() - 9); s += 9) {
            int lastSlot = s + 1;
            this.setItem(s, itemStack);
            this.setItem(lastSlot, itemStack);

        }
        for (int lr = (this.getSize() - 9); lr < this.getSize(); lr++) {
            this.setItem(lr, itemStack);
        }
    }


    public Integer getSlot(ItemStack itemStack) {
        for (Integer i = 0; i < getSize(); i++) {
            if (this.getItem(i).equals(itemStack)) {
                return i;
            }
        }
        return -1;
    }


    public Inventory fill(ItemStack itemStack) {
        for (int i = 0; i < this.getSize(); i++) {
            this.setItem(i, itemStack);
        }
        return this;
    }

    public Inventory setCloseRunnable(Runnable runnable) {
        this.closerunnable = runnable;
        return this;
    }

    public Inventory addItem(ItemStack itemStack, Long delay) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BetterGUI.getInstance(), () -> {
            if (this.player.getOpenInventory().getTitle().equalsIgnoreCase(this.getName())) {
                this.addItem(itemStack);
            }
        }, delay);
        return this;
    }

    public Inventory setContents(ItemStack[] contents) {
        this.inventory.setContents(contents);
        return this;
    }

    public Inventory setItem(Integer i, ItemStack itemStack, Long delay) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BetterGUI.getInstance(), () -> {
            if (this.player.getOpenInventory().getTitle().equalsIgnoreCase(this.getName())) {
                this.setItem(i, itemStack);
            }
        }, delay);
        return this;
    }
    
    public Inventory addItem(ItemStack itemStack) {
        this.inventory.addItem(itemStack);
        return this;
    }

    public Inventory removeItem(ItemStack itemStack) {
        this.inventory.remove(itemStack);
        return this;
    }

    public Inventory removeItem(Integer slot) {
        this.inventory.remove(getItem(slot));
        return this;
    }

    public Inventory setItem(Integer i, ItemStack itemStack) {
        this.inventory.setItem(i, itemStack);
        return this;
    }

    public Inventory setItem(Integer i, ItemStack itemStack, Runnable runnable) {
        this.inventory.setItem(i, itemStack);
        this.setRunnable(i, runnable);
        return this;
    }

    public Inventory clear() {
        this.inventory.clear();
        return this;
    }

    public Inventory setClickable(Boolean b) {
        this.clickable = b;
        return this;
    }

    public Inventory setCloseable(Boolean b) {
        this.closable = b;
        return this;
    }

    public Inventory close() {
        BetterGUI.getInstance().getInventoryService().getInventories().remove(this.player);
        this.stopAnimate();
        this.stopRainbow();
        if (this.player.getOpenInventory() != null) {
            if (this.player.getOpenInventory().getTitle().equalsIgnoreCase(getName())) {
                this.player.getOpenInventory().close();
            }
        }
        BetterGUI.getInstance().getInventoryService().getRunnables().remove(this);
        this.closerunnable = null;
        BetterGUI.getInstance().getInventoryService().getInventories().remove(this.player);
        return this;
    }

    public Inventory setRunnable(Integer slot, Runnable runnable) {
        HashMap<Integer, Runnable> map = BetterGUI.getInstance().getInventoryService().getRunnables().getOrDefault(this, new HashMap<>());
        map.put(slot, runnable);
        BetterGUI.getInstance().getInventoryService().getRunnables().put(this, map);
        return this;
    }

    public Inventory changeName(String string) {
        EntityPlayer ep = ((CraftPlayer)getPlayer()).getHandle();
        String id = "minecraft:chest";
        switch (inventory.getType()) {
            case ANVIL:
                id = "minecraft:anvil";
                break;
            case BEACON:
                id = "minecraft:beacon";
                break;
            case BREWING:
                id = "minecraft:brewing_stand";
                break;
            case DISPENSER:
                id = "minecraft:dispenser";
                break;
            case DROPPER:
                id = "minecraft:dropper";
                break;
            case ENCHANTING:
                id = "minecraft:enchanting_table";
                break;
            case ENDER_CHEST:
            case CHEST:
                id = "minecraft:chest";
                break;
            case FURNACE:
                id = "minecraft:furnace";
                break;
            case HOPPER:
                id = "minecraft:hopper";
                break;
            case MERCHANT:
                id = "minecraft:villager";
                break;
            case WORKBENCH:
                id = "minecraft:crafting_table";
                break;
        }
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(ep.activeContainer.windowId, id, new ChatMessage(string), getPlayer().getOpenInventory().getTopInventory().getSize());
        ep.playerConnection.sendPacket(packet);
        ep.updateInventory(ep.activeContainer);
        return this;
    }

    public void open() {
        BetterGUI.getInstance().getInventoryService().getInventories().put(this.player, this);
        this.player.openInventory(this.inventory);
    }

    public Boolean containsMinimum(ItemStack itemStack, Integer min) {
        return this.inventory.containsAtLeast(itemStack, min);
    }

}