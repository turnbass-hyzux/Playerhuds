package dev.lone.rpghuds.core;

import dev.lone.rpghuds.utils.Utilz;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;

/**
 * Recode this shit
 */
@Deprecated
public class Settings
{
    public boolean moneyEnabled;
    public String moneyPapi;
    public long refreshIntervalTicks;
    public long refreshHighFrequencyIntervalTicks;
    public boolean legacyTextures;
    public int moneyOffset;
    public HashSet<String> moneyWorlds;
    public boolean mobcoinsEnabled;
    public String mobcoinsPapi;
    public int mobcoinsOffset;
    public HashSet<String> mobcoinsWorlds;
    public boolean minecoinsEnabled;
    public String minecoinsPapi;
    public int minecoinsOffset;
    public HashSet<String> minecoinsWorlds;
    public boolean farmcoinsEnabled;
    public String farmcoinsPapi;
    public int farmcoinsOffset;
    public HashSet<String> farmcoinsWorlds;

    public boolean jobs_hunterEnabled;
    public String jobs_hunterPapi;
    public int jobs_hunterOffset;
    public HashSet<String> jobs_hunterWorlds;
    public boolean jobs_minerEnabled;
    public String jobs_minerPapi;
    public int jobs_minerOffset;
    public HashSet<String> jobs_minerWorlds;
    public boolean jobs_farmerEnabled;
    public String jobs_farmerPapi;
    public int jobs_farmerOffset;
    public HashSet<String> jobs_farmerWorlds;
    public boolean compassEnabled;
    public int compassOffset;
    public HashSet<String> compassWorlds;

    public boolean quiverEnabled;
    public HashSet<String> quiverWorlds;
    public int quiverOffset;
    public int quiverOffsetWhenOffhandShown;
    public int quiverContentUpdateTicks;

    public boolean arrowTargetEnabled;
    public int arrowTargetOffset;
    public HashSet<String> arrowTargetWorlds;

    public boolean debug;
    public String msgHudNotFound;
    public String msgWrongUsage;
    public String msgDestinationSet;
    public String msgDestinationRemoved;

    public Settings(FileConfiguration config)
    {
        this.refreshIntervalTicks = config.getLong("huds_refresh_interval_ticks", 30);
        this.refreshHighFrequencyIntervalTicks = config.getLong("huds_high_frequency_refresh_interval_ticks", 2);
        this.legacyTextures = config.getBoolean("legacy_1_18_and_lower_textures", false);

        this.moneyEnabled = config.getBoolean("money.enabled", true);
        this.moneyPapi = config.getString("money.text", "%vault_eco_balance_fixed%");
        this.moneyOffset = config.getInt("money.offset", 88);
        this.moneyWorlds = new HashSet<>(config.getStringList("money.worlds"));

        this.mobcoinsEnabled = config.getBoolean("mobcoins.enabled", true);
        this.mobcoinsPapi = config.getString("mobcoins.text", "%vault_eco_balance_fixed%");
        this.mobcoinsOffset = config.getInt("mobcoins.offset", 88);
        this.mobcoinsWorlds = new HashSet<>(config.getStringList("mobcoins.worlds"));

        this.minecoinsEnabled = config.getBoolean("minecoins.enabled", true);
        this.minecoinsPapi = config.getString("minecoins.text", "%vault_eco_balance_fixed%");
        this.minecoinsOffset = config.getInt("minecoins.offset", 88);
        this.minecoinsWorlds = new HashSet<>(config.getStringList("minecoins.worlds"));

        this.farmcoinsEnabled = config.getBoolean("farmcoins.enabled", true);
        this.farmcoinsPapi = config.getString("farmcoins.text", "%vault_eco_balance_fixed%");
        this.farmcoinsOffset = config.getInt("farmcoins.offset", 88);
        this.farmcoinsWorlds = new HashSet<>(config.getStringList("farmcoins.worlds"));

        this.jobs_hunterEnabled = config.getBoolean("jobs_hunter.enabled", true);
        this.jobs_hunterPapi = config.getString("jobs_hunter.text", "%vault_eco_balance_fixed%");
        this.jobs_hunterOffset = config.getInt("jobs_hunter.offset", 88);
        this.jobs_hunterWorlds = new HashSet<>(config.getStringList("jobs_hunter.worlds"));

        this.jobs_minerEnabled = config.getBoolean("jobs_miner.enabled", true);
        this.jobs_minerPapi = config.getString("jobs_miner.text", "%vault_eco_balance_fixed%");
        this.jobs_minerOffset = config.getInt("jobs_miner.offset", 88);
        this.jobs_minerWorlds = new HashSet<>(config.getStringList("jobs_miner.worlds"));

        this.jobs_farmerEnabled = config.getBoolean("jobs_farmer.enabled", true);
        this.jobs_farmerPapi = config.getString("jobs_farmer.text", "%vault_eco_balance_fixed%");
        this.jobs_farmerOffset = config.getInt("jobs_farmer.offset", 88);
        this.jobs_farmerWorlds = new HashSet<>(config.getStringList("jobs_farmer.worlds"));



        this.debug = config.getBoolean("log.debug", false);

        this.msgHudNotFound = Utilz.color(config.getString("lang.hud_not_found"));
        this.msgWrongUsage = Utilz.color(config.getString("lang.wrong_usage"));
        this.msgDestinationSet = Utilz.color(config.getString("lang.destination_set"));
        this.msgDestinationRemoved = Utilz.color(config.getString("lang.destination_removed"));
    }
}
