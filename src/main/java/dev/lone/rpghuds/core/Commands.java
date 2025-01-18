package dev.lone.rpghuds.core;

import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.data.Hud;
import dev.lone.rpghuds.utils.Utilz;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter
{
    private static final List<String> EMPTY_LIST = Collections.singletonList("");

    public void register()
    {
        Bukkit.getPluginCommand("rpgcompass").setExecutor(this);
        Bukkit.getPluginCommand("rpghuds").setExecutor(this);

        Bukkit.getPluginCommand("rpgcompass").setTabCompleter(this);
        Bukkit.getPluginCommand("rpghuds").setTabCompleter(this);
    }

    private boolean hasPerm(CommandSender sender, Player target, String perm)
    {
        if (sender == target)
        {
            if (!sender.hasPermission(perm))
            {
                sender.sendMessage(ChatColor.RED + "No permission " + perm);
                return false;
            }
        }

        if (sender != target)
        {
            if (!sender.hasPermission(perm + ".others"))
            {
                sender.sendMessage(ChatColor.RED + "No permission " + perm + ".others");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        switch (command.getName())
        {
            // Recode this shit
            case "rpghuds":
            {
                if(args.length == 1 && args[0].equals("reload"))
                {
                    if(sender.hasPermission("rpghuds.reload"))
                    {
                        Main.inst().reloadPlugin();
                        Main.inst().getLogger().info(ChatColor.GREEN + "Reloaded");
                        if(sender instanceof Player)
                            sender.sendMessage(ChatColor.GREEN + "Reloaded");
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED + "No permission " + "rpghuds.reload");
                    }
                    return true;
                }

                if(args.length < 2)
                {
                    sender.sendMessage(Main.settings.msgWrongUsage);
                    return true;
                }

                Player player;
                if (args.length == 3)
                {
                    player = Bukkit.getPlayer(args[2]);
                }
                else
                {
                    if (sender instanceof Player)
                        player = (Player) sender;
                    else
                    {
                        sender.sendMessage(ChatColor.RED + "Please specify a player.");
                        return true;
                    }
                }

                Hud<?> playerHud = RPGHuds.inst().getPlayerHud(player, args[1]);
                if (playerHud == null)
                {
                    sender.sendMessage(Main.settings.msgHudNotFound);
                    return true;
                }

                switch (args[0])
                {
                    case "show":
                        if (hasPerm(sender, player, "rpghuds.show"))
                            playerHud.hide(false);
                        break;
                    case "hide":
                        if (hasPerm(sender, player, "rpghuds.hide"))
                            playerHud.hide(true);
                        break;
                }
                break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, @NotNull String[] args)
    {
        switch (command.getName())
        {
            case "rpghuds":
            {
                if (args.length == 1)
                {
                    if(sender.hasPermission("rpghuds.reload"))
                        return Arrays.asList("reload", "show", "hide");
                    return Arrays.asList("show", "hide");
                }

                if (args.length == 2)
                    return RPGHuds.inst().getHudsNames();
                if (args.length == 3)
                {
                    List<String> names = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers())
                        names.add(p.getName());
                    return names;
                }
                break;
            }
        }
        return EMPTY_LIST;
    }
}
