package de.ludwigschindler.lscommands.main;

import de.ludwigschindler.lscommands.main.language.LSLanguage;
import de.ludwigschindler.lscommands.main.language.LSText;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class LSGUI implements InventoryHolder, Listener {

    private Inventory inv;
    private GUIType type;

    private Runnable action;

    public LSGUI() {
        Bukkit.getPluginManager().registerEvents(this, LSCommands.plugin);
    }

    private static ItemStack createGUIItem(Material material, String name, String... lore) {
        return createGUIItem(material, 1, name, lore);
    }

    private static ItemStack createGUIItem(Material material, int anzahl, String name, String... lore) {
        ItemStack item = new ItemStack(material, anzahl);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public void setAction(Runnable runnable) {
        action = runnable;
    }

    public void createGUI(int rows, String title) {
        inv = Bukkit.createInventory(this, rows * 9, title);
    }

    public void createBigPageGUI(LSLanguage lang, String title) {
        createGUI(6, title);
        type = GUIType.BIG_PAGE;

        getInventory().setItem(53, createGUIItem(Material.BARRIER, LSText.getText(lang, "general.gui.cancelClose")));


    }

    public void run(Runnable runnable) {
        runnable.run();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getInventory().getHolder() == this) {

        LSPlayer p = LSPlayer.getPlayer((Player) e.getWhoClicked());

        ItemStack item = e.getCurrentItem();

        if (item == null || item.getType() == Material.AIR) return;

        if (type == GUIType.BIG_PAGE) {
            if (e.getRawSlot() == 53) {
                new ArrayList<>(e.getInventory().getViewers()).forEach(HumanEntity::closeInventory);
            } else {
                runAction();
            }
        }
            HandlerList.unregisterAll(this);
        }
    }

    private void runAction() {
        action.run();
    }

    private enum GUIType {
        BIG_PAGE, CONFIRM
    }


}
