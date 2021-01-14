package de.lystx.bettergui.utils;

import com.google.common.collect.Lists;
import de.lystx.bettergui.main.BetterGUI;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;


import java.util.*;
import java.util.stream.IntStream;

public class Inventory {

    public static HashMap<Player, Inventory> invS = new HashMap<>();
    private org.bukkit.inventory.Inventory inventory;
    private Boolean clickable, closable;
    private Player player;
    private HashMap<Integer, Runnable> runnableS = new HashMap<>();
    private Runnable closerunnable;
    private Integer scheduler;
    private Integer schedulerByte;
    private Integer schedulerAnimate;
    private Iterator<String> iterator;
    private InventoryType type;

    public Inventory(Player player, String name, InventoryType type) {
        this.player = player;
        clickable = true;
        closable = true;
        schedulerByte = 0;
        schedulerAnimate = 0;
        scheduler = 0;
        this.type = type;
        inventory = Bukkit.createInventory(player, type, name);
    }

    public Inventory(Player player, String name, Integer rows) {
        this.player = player;
        clickable = true;
        closable = true;
        schedulerByte = 0;
        schedulerAnimate = 0;
        scheduler = 0;
        type = InventoryType.CHEST;
        inventory = Bukkit.createInventory(player, rows*9, name);
    }

    public HashMap<Integer, Runnable> getRunnable() {
        return runnableS;
    }

    public Runnable getRunnable(Integer slot) {
        Runnable runnable = runnableS.get(slot);
        return runnable;
    }

    public org.bukkit.inventory.Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return getInventory().getTitle();
    }

    public Integer getSize() {
        return getInventory().getSize();
    }

    public Boolean getClickable() {
        return this.clickable;
    }

    public Boolean getCloseable() {
        return this.closable;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setRand() {
        setRand(new Item(Material.STAINED_GLASS_PANE, (short)7).setNoName().build());
    }

    public ItemStack getItem(Integer slot) {
        return getInventory().getItem(slot);
    }

    public List<ItemStack> getContentsAsList() {
        List<ItemStack> list = Lists.newLinkedList();
        list.addAll(Arrays.asList(getContents()));
        return list;
    }
    public ItemStack[] getContents() {
        return getInventory().getContents();
    }

    public static Inventory getInventory(Player player) {
        return invS.get(player);
    }

    public void rainBow(Long delay) {
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(BetterGUI.getInstance(), new Runnable() {
            @Override
            public void run() {
                ItemStack item = new Item(Material.STAINED_GLASS_PANE, schedulerByte.byteValue()).setNoName().build();
                setRand(item);
                if (schedulerByte >= 15) {
                    schedulerByte = 0;
                }
                schedulerByte++;
            }
        }, 0, delay);
    }

    public void animate(String[] animations, Long delay) {
        List<String> animation = Arrays.asList(animations);
        iterator = animation.iterator();
        schedulerAnimate = Bukkit.getScheduler().scheduleAsyncRepeatingTask(BetterGUI.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (iterator.hasNext()) {
                    changeName(iterator.next());
                } else {
                    iterator = animation.iterator();
                }
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
        Integer rows = (getSize() / 9);
        int size = getSize();
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
            if (getItem(i).equals(itemStack)) {
                return i;
            }
        }
        return -1;
    }

    public Runnable getCloserunnable() {
        return closerunnable;
    }

    public Inventory fill(ItemStack itemStack) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            getInventory().setItem(i, itemStack);
        }
        return this;
    }

    public Inventory setCloseRunnable(Runnable runnable) {
        closerunnable = runnable;
        return this;
    }

    public Inventory addItem(ItemStack itemStack, Long delay) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BetterGUI.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(getName())) {
                    addItem(itemStack);
                }
            }
        }, delay);
        return this;
    }

    public Inventory setContents(ItemStack[] contents) {
        getInventory().setContents(contents);
        return this;
    }

    public Inventory setItem(Integer i, ItemStack itemStack, Long delay) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BetterGUI.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(getName())) {
                    setItem(i, itemStack);
                }
            }
        }, delay);
        return this;
    }

    public Inventory playSound(Sound sound) {
        getPlayer().playSound(getPlayer().getLocation(), sound, 2F, 2F);
        return this;
    }

    public Inventory playSound(Sound sound, Long delay) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BetterGUI.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(getName())) {
                    playSound(sound);
                }
            }
        }, delay);
        return this;
    }

    public Inventory addItem(ItemStack itemStack) {
        getInventory().addItem(itemStack);
        return this;
    }

    public Inventory removeItem(ItemStack itemStack) {
        getInventory().remove(itemStack);
        return this;
    }

    public Inventory removeItem(Integer slot) {
        getInventory().remove(getItem(slot));
        return this;
    }

    public Inventory setItem(Integer i, ItemStack itemStack) {
        getInventory().setItem(i, itemStack);
        return this;
    }

    public Inventory clear() {
        getInventory().clear();
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
        invS.remove(getPlayer());
        stopAnimate();
        stopRainbow();
        if (getPlayer().getOpenInventory() != null) {
            if (getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(getName())) {
                getPlayer().getOpenInventory().close();
            }
        }
        getRunnable().clear();
        closerunnable = null;
        removeInv(getPlayer());
        return this;
    }

    public Inventory setRunnable(Integer slot, Runnable runnable) {
        getRunnable().put(slot, runnable);
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
        invS.put(getPlayer(), this);
        getPlayer().openInventory(getInventory());
    }

    public Boolean containsMinimum(ItemStack itemStack, Integer min) {
        return getInventory().containsAtLeast(itemStack, min);
    }

    public static void removeInv(Player player) {
        invS.remove(player);
    }
}