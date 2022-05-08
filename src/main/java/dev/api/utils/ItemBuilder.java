package dev.api.utils;

import java.util.HashMap;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemBuilder {
    private Material material;

    private Short durability;

    private String title;

    private int amount = 1;

    private List<String> lores;

    private byte materialData;

    private HashMap<Enchantment, Integer> enchantments = new HashMap<>();

    private ItemStack itemStack;

    public ItemBuilder() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder durability(short durability) {
        this.durability = Short.valueOf(durability);
        return this;
    }

    public ItemBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder lores(List<String> lores) {
        this.lores = lores;
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, Integer.valueOf(level));
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment) {
        enchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder clearLore() {
        this.lores.clear();
        return this;
    }

    public ItemBuilder color(Color color) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (!(meta instanceof LeatherArmorMeta))
            throw new UnsupportedOperationException("Cannot set color of a non-leather armor item.");
        ((LeatherArmorMeta)meta).setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder data(int data) {
        this.materialData = (byte)data;
        return this;
    }

    public ItemBuilder clearEnchantments() {
        this.enchantments.clear();
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = this.itemStack;
        if (this.material != null)
            itemStack.setType(this.material);
        for (Enchantment enchantment : this.enchantments.keySet())
            itemStack.addUnsafeEnchantment(enchantment, ((Integer)this.enchantments.get(enchantment)).intValue());
        ItemMeta meta = itemStack.getItemMeta();
        if (this.amount > 0)
            itemStack.setAmount(this.amount);
        if (this.durability != null)
            itemStack.setDurability(this.durability.shortValue());
        if (this.title != null)
            meta.setDisplayName(MessageUtility.formatMessage("&r" + this.title));
        if (this.lores != null && this.lores.size() > 0)
            meta.setLore(MessageUtility.formatMessages(this.lores));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}