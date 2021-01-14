package de.lystx.bettergui.service;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemService {

    private final ItemStack item;
    private final List<String> lore = new ArrayList<String>();
    private final ItemMeta meta;

    public ItemService(Material mat, short subid, int amount) {
        item = new ItemStack(mat, amount, subid);
        meta = item.getItemMeta();
    }

    public ItemService(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemService(Material mat, short subid) {
        item = new ItemStack(mat, 1, subid);
        meta = item.getItemMeta();
    }

    public ItemService(Material mat, int amount) {
        item = new ItemStack(mat, amount, (short)0);
        meta = item.getItemMeta();
    }

    public ItemService(Material mat) {
        item = new ItemStack(mat, 1, (short)0);
        meta = item.getItemMeta();
    }

    public ItemService setAmount(int value) {
        item.setAmount(value);
        return this;
    }

    public ItemService setNoName() {
        meta.setDisplayName(" ");
        return this;
    }

    public ItemService setGlow() {
        meta.addEnchant( Enchantment.DURABILITY, 1, true);
        meta.addItemFlags( ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemService setData(short data) {
        item.setDurability(data);
        return this;
    }

    public ItemService addLoreLine(String line) {
        lore.add(line);
        return this;
    }

    public ItemService addLoreArray(String[] lines) {
        for(int x = 0; x < lines.length; x++) {
            lore.add(lines[x]);
        }
        return this;
    }

    public ItemService addLoreAll(List<String> lines) {
        lore.addAll(lines);
        return this;
    }

    public ItemService setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemService setSkullOwner(String owner) {
        ((SkullMeta )meta).setOwner(owner);
        return this;
    }

    public ItemService setColor(Color c) {
        ((LeatherArmorMeta )meta).setColor(c);
        return this;
    }

    public ItemService setBannerColor(DyeColor c) {
        ((BannerMeta )meta).setBaseColor(c);
        return this;
    }

    public ItemService setUnbreakable(boolean value) {
        meta.spigot().setUnbreakable(value);
        return this;
    }

    public ItemService addEnchantment(Enchantment ench, int lvl) {
        meta.addEnchant(ench, lvl, true);
        return this;
    }

    public ItemService addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemService addLeatherColor(Color color) {
        ((LeatherArmorMeta ) meta).setColor( color );
        return this;
    }

    public ItemStack build() {
        if(lore.isEmpty() == false) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

}