package me.nelonn.publicvault;

import me.nelonn.publicvault.util.Command;
import me.nelonn.publicvault.util.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class PublicVaultCommand extends Command {
    private final PublicVault plugin;

    public PublicVaultCommand(PublicVault plugin) {
        super("publicvault");
        this.plugin = plugin;
    }

    @Override
    protected void onCommand(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            TextUtils.send(sender, "&cThis command only for players!");
            return;
        }
        Inventory publicVault = plugin.getPublicVault();
        if (publicVault != null) {
            player.openInventory(publicVault);
        }
    }
}
