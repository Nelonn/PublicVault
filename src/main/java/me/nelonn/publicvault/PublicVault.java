package me.nelonn.publicvault;

import me.nelonn.publicvault.util.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PublicVault extends JavaPlugin {
    private static PublicVault instance;

    private Inventory publicVault;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("publicvault").setExecutor(new PublicVaultCommand(this));

        publicVault = Bukkit.createInventory(null, 6 * 9, "Public Vault");

        File file = new File(getDataFolder(), "data.yml");
        if (file.exists()) {
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            for (String pos : data.getKeys(false)) {
                publicVault.setItem(Integer.parseInt(pos), data.getItemStack(pos));
            }
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::save, 60 * 20, 60 * 20);
    }

    public void save() {
        File file = new File(getDataFolder(), "data.yml");
        FileConfiguration data = new YamlConfiguration();
        ItemStack[] contents = publicVault.getContents();
        int pos = 0;
        for (ItemStack itemStack : contents) {
            data.set(String.valueOf(pos), itemStack);
            pos++;
        }
        try {
            data.save(file);
        } catch (Exception e) {
            TextUtils.send(Bukkit.getConsoleSender(), "&cError while saving public vault:");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
        save();
    }

    public Inventory getPublicVault() {
        return publicVault;
    }

    public static PublicVault getInstance() {
        return instance;
    }
}
