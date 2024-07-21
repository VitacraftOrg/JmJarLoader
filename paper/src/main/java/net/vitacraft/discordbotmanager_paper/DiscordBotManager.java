package net.vitacraft.discordbotmanager_paper;

import lombok.Getter;
import net.vitacraft.discordbotmanager.Common;
import net.vitacraft.discordbotmanager_paper.message.Messenger;
import net.vitacraft.discordbotmanager_paper.commands.DBMCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DiscordBotManager extends JavaPlugin  {
    @Getter
    private static DiscordBotManager plugin;
    @Getter
    private Common common;

    @Override
    public void onEnable() {
        plugin = this;
        common = new Common("paper", new Messenger(this));
        Objects.requireNonNull(getCommand("dbm")).setExecutor(new DBMCommand(common.getSandboxManager()));
    }

    @Override
    public void onDisable() {

    }

}

