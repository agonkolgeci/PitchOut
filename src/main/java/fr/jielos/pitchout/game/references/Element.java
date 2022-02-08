package fr.jielos.pitchout.game.references;

import fr.jielos.pitchout.components.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Element {

    BOW(0, new ItemBuilder(new ItemStack(Material.BOW)).unbreakable().addEnchant(Enchantment.ARROW_KNOCKBACK, 5).addEnchant(Enchantment.ARROW_INFINITE, 1).hideAttributes().setName("§b§lLa Foudre de Zeus")),
    SPADE(1, new ItemBuilder(new ItemStack(Material.WOOD_SPADE)).unbreakable().addEnchant(Enchantment.KNOCKBACK, 5).hideAttributes().setName("§6§lLa Main Droite de Dieu")),
    ARROW(9, new ItemBuilder(new ItemStack(Material.ARROW)).setName("§eLa flèche magique").setLore("§7Permet d'utiliser §b§lLa Foudre De Zeus §7!")),;

    final int slot;
    final ItemBuilder itemBuilder;

    Element(final int slot, final ItemBuilder itemBuilder) {
        this.slot = slot;
        this.itemBuilder = itemBuilder;
    }

    public int getSlot() {
        return slot;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

    public ItemStack getItemStack() {
        return itemBuilder.toItemStack().clone();
    }
}
