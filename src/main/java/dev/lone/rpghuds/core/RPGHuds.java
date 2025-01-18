package dev.lone.rpghuds.core;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.itemsadder.api.ItemsAdder;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.data.*;
import dev.lone.rpghuds.core.settings.MoneySettings;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RPGHuds
{
    private static RPGHuds instance;

    static final String WARNING = "Please don't forget to regen your resourcepack using /iazip command.";

    private final Main plugin;
    private final HashMap<Player, PlayerData> datasByPlayer = new HashMap<>();
    private final List<PlayerData> datas = new ArrayList<>();
    private final List<BukkitTask> refreshTasks = new ArrayList<>();

    boolean needsIaZip;
    boolean notifyIazip;
    private boolean allPlayersInitialized;

    //TODO: recode this shit. Very dirty
    private final List<String> hudsNames = Arrays.asList("rpghuds:money", "rpghuds:mobcoins", "rpghuds:minercoins", "rpghuds:farmercoins","rpghuds:jobs_hunter","rpghuds:jobs_miner","rpghuds:jobs_farmer");


    public RPGHuds(Main plugin)
    {
        instance = this;

        this.plugin = plugin;

        new EventsListener(plugin, this).registerListener();

        extractDefaultAssets();

        if (ItemsAdder.areItemsLoaded() && !needsIaZip)
            initAllPlayers();
    }

    public static RPGHuds inst()
    {
        return instance;
    }

    //TODO: recode this shit. Very dirty
    public List<String> getHudsNames()
    {
        return hudsNames;
    }

    @Nullable
    public Hud<?> getPlayerHud(Player player, String namespacedID)
    {
        PlayerData playerData = datasByPlayer.get(player);
        if (playerData == null)
            return null;
        return playerData.allHuds_byNamespacedId.get(namespacedID);
    }

    public void initAllPlayers()
    {
        if(allPlayersInitialized)
        {
            plugin.getLogger().severe("Error: players already initialized! Be sure to first call RPGHuds#cleanup().");
            return;
        }
        try
        {
            for (Player player : Bukkit.getServer().getOnlinePlayers())
                initPlayer(player);
            scheduleRefresh();
            allPlayersInitialized = true;
        }
        catch (NullPointerException e)
        {
            plugin.getLogger().warning(WARNING);
        }
    }

    void initPlayer(Player player)
    {
        PlayerData playerData;
        try
        {
            playerData = new PlayerData(new PlayerHudsHolderWrapper(player));

            if (Main.settings.moneyEnabled)
            {
                playerData.registerHud(new MoneyHud(
                        Main.settings.moneyPapi,
                        playerData.getHolder(),
                        new MoneySettings(
                                "rpghuds:money",
                                "rpghuds:money_icon",
                                "rpghuds:backgroud_32",
                                "rpghuds:digit_0",
                                "rpghuds:digit_1",
                                "rpghuds:digit_2",
                                "rpghuds:digit_3",
                                "rpghuds:digit_4",
                                "rpghuds:digit_5",
                                "rpghuds:digit_6",
                                "rpghuds:digit_7",
                                "rpghuds:digit_8",
                                "rpghuds:digit_9",
                                "rpghuds:char_unknown",
                                "rpghuds:char_k",
                                "rpghuds:char_m",
                                "rpghuds:char_b",
                                "rpghuds:char_t",
                                "rpghuds:char_dot",
                                "rpghuds:char_comma",
                                "rpghuds:char_arrow_up",
                                "rpghuds:char_arrow_down",
                                Main.settings.moneyOffset,
                                Main.settings.moneyWorlds
                        )
                ), false);
                if (Main.settings.mobcoinsEnabled) {
                    playerData.registerHud(new MobcoinsHub(
                            Main.settings.mobcoinsPapi,
                            playerData.getHolder(),
                            new MoneySettings(
                                    "rpghuds:mobcoins",
                                    "rpghuds:mobcoins_icon",
                                    "rpghuds:backgroud_32",
                                    "rpghuds:digit_0",
                                    "rpghuds:digit_1",
                                    "rpghuds:digit_2",
                                    "rpghuds:digit_3",
                                    "rpghuds:digit_4",
                                    "rpghuds:digit_5",
                                    "rpghuds:digit_6",
                                    "rpghuds:digit_7",
                                    "rpghuds:digit_8",
                                    "rpghuds:digit_9",
                                    "rpghuds:char_unknown",
                                    "rpghuds:char_k",
                                    "rpghuds:char_m",
                                    "rpghuds:char_b",
                                    "rpghuds:char_t",
                                    "rpghuds:char_dot",
                                    "rpghuds:char_comma",
                                    "rpghuds:char_arrow_up",
                                    "rpghuds:char_arrow_down",
                                    Main.settings.mobcoinsOffset,
                                    Main.settings.mobcoinsWorlds
                            )
                    ), false);
                }
                if (Main.settings.minecoinsEnabled) {
                    playerData.registerHud(new MobcoinsHub(
                            Main.settings.minecoinsPapi,
                            playerData.getHolder(),
                            new MoneySettings(
                                    "rpghuds:minecoins",
                                    "rpghuds:minecoins_icon",
                                    "rpghuds:backgroud_32",
                                    "rpghuds:digit_0",
                                    "rpghuds:digit_1",
                                    "rpghuds:digit_2",
                                    "rpghuds:digit_3",
                                    "rpghuds:digit_4",
                                    "rpghuds:digit_5",
                                    "rpghuds:digit_6",
                                    "rpghuds:digit_7",
                                    "rpghuds:digit_8",
                                    "rpghuds:digit_9",
                                    "rpghuds:char_unknown",
                                    "rpghuds:char_k",
                                    "rpghuds:char_m",
                                    "rpghuds:char_b",
                                    "rpghuds:char_t",
                                    "rpghuds:char_dot",
                                    "rpghuds:char_comma",
                                    "rpghuds:char_arrow_up",
                                    "rpghuds:char_arrow_down",
                                    Main.settings.minecoinsOffset,
                                    Main.settings.mobcoinsWorlds
                            )
                    ), false);
                }
                if (Main.settings.farmcoinsEnabled) {
                    playerData.registerHud(new MobcoinsHub(
                            Main.settings.farmcoinsPapi,
                            playerData.getHolder(),
                            new MoneySettings(
                                    "rpghuds:farmcoins",
                                    "rpghuds:farmcoins_icon",
                                    "rpghuds:backgroud_32",
                                    "rpghuds:digit_0",
                                    "rpghuds:digit_1",
                                    "rpghuds:digit_2",
                                    "rpghuds:digit_3",
                                    "rpghuds:digit_4",
                                    "rpghuds:digit_5",
                                    "rpghuds:digit_6",
                                    "rpghuds:digit_7",
                                    "rpghuds:digit_8",
                                    "rpghuds:digit_9",
                                    "rpghuds:char_unknown",
                                    "rpghuds:char_k",
                                    "rpghuds:char_m",
                                    "rpghuds:char_b",
                                    "rpghuds:char_t",
                                    "rpghuds:char_dot",
                                    "rpghuds:char_comma",
                                    "rpghuds:char_arrow_up",
                                    "rpghuds:char_arrow_down",
                                    Main.settings.farmcoinsOffset,
                                    Main.settings.farmcoinsWorlds
                            )
                    ), false);
                }
            }

            datasByPlayer.put(player, playerData);
            datas.add(playerData);
        }
        catch (NullPointerException exc)
        {
            Main.inst().getLogger().severe(ChatColor.RED + "Failed to load PlayerData: " + exc.getMessage());
        }
    }

    //TODO: implement animated icons.
    // Warning: make sure to increment the refresh rate only when it's actually needed by the animation.
    // I don't want the plugin to become heavy just for a stupid animation.

    private void scheduleRefresh()
    {
        refreshTasks.add(Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (PlayerData data : datas)
                data.refreshAllHuds();
        }, Main.settings.refreshIntervalTicks, Main.settings.refreshIntervalTicks));

        refreshTasks.add(Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (PlayerData data : datas)
                data.refreshHighFrequency();
        }, Main.settings.refreshHighFrequencyIntervalTicks, Main.settings.refreshHighFrequencyIntervalTicks));
    }

    void unregisterAllPlayers()
    {
        for (BukkitTask task : refreshTasks)
            task.cancel();
        refreshTasks.clear();
        allPlayersInitialized = false;
    }

    public void cleanup()
    {
        unregisterAllPlayers();

        for (PlayerData data : datas)
            data.cleanup();

        datas.clear();
        datasByPlayer.clear();
    }

    private void extractDefaultAssets()
    {
        CodeSource src = Main.class.getProtectionDomain().getCodeSource();
        if (src != null)
        {
            File itemsadderRoot = new File(plugin.getDataFolder().getParent() + "/ItemsAdder");

            URL jar = src.getLocation();
            ZipInputStream zip;
            try
            {
                plugin.getLogger().info(ChatColor.AQUA + "Extracting assets...");

                zip = new ZipInputStream(jar.openStream());
                while (true)
                {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;

                    String name = e.getName();
                    if (!e.isDirectory())
                    {
                        if (name.startsWith("contents/rpghuds/configs"))
                        {
                            doExtractFile(itemsadderRoot, name, name);
                        }
                        else
                        {
                            if (Main.settings.legacyTextures)
                            {
                                if (name.startsWith("contents/rpghuds/textures_legacy"))
                                {
                                    doExtractFile(itemsadderRoot, name, name.replace("textures_legacy", "textures"));
                                }
                            }
                            else
                            {
                                if (name.startsWith("contents/rpghuds/textures_new"))
                                {
                                    doExtractFile(itemsadderRoot, name, name.replace("textures_new", "textures"));
                                }
                            }
                        }
                    }
                }

                plugin.getLogger().info(ChatColor.GREEN + "DONE extracting assets!");
            }
            catch (IOException e)
            {
                plugin.getLogger().severe("        ERROR EXTRACTING assets! StackTrace:");
                e.printStackTrace();
            }
        }

        notifyIazip = needsIaZip;
        if (needsIaZip)
            plugin.getLogger().warning(WARNING);
    }

    private void doExtractFile(File itemsadderRoot, String name, String destName) throws IOException
    {
        File dest = new File(itemsadderRoot, destName);
        if (!dest.exists())
        {
            FileUtils.copyInputStreamToFile(plugin.getResource(name), dest);
            plugin.getLogger().info(ChatColor.AQUA + "       - Extracted " + destName);
            needsIaZip = true;
        }
    }
}
