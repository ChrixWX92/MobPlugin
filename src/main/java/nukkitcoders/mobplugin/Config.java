package nukkitcoders.mobplugin;

public class Config {

    public cn.nukkit.utils.Config pluginConfig;

    public int spawnDelay;
    public int despawnTicks;
    public int spawnerRange;
    public int endEndermanSpawnRate;
    public boolean noXpOrbs;
    public boolean noSpawnEggWasting;
    public boolean killOnDespawn;
    public boolean spawnersEnabled;
    public boolean checkTamedEntityAttack;
    public boolean creeperExplodeBlocks;

    Config(MobPlugin plugin) {
        plugin.saveDefaultConfig();
        pluginConfig = plugin.getConfig();
    }

    boolean init(MobPlugin plugin) {
        int ver = 16;

        if (pluginConfig.getInt("config-version") != ver) {
            if (pluginConfig.getInt("config-version") == 15) {
                pluginConfig.set("entities.entity-creation-disabled", "exampledworld1, exampleworld2");
                pluginConfig.set("other.creeper-explode-blocks", true);
            } else if (pluginConfig.getInt("config-version") == 14) {
                pluginConfig.set("entities.entity-creation-disabled", "exampledworld1, exampleworld2");
                pluginConfig.set("other.creeper-explode-blocks", true);
                pluginConfig.set("other.check-tamed-entity-attack", true);
            } else if (pluginConfig.getInt("config-version") == 13) {
                pluginConfig.set("entities.entity-creation-disabled", "exampledworld1, exampleworld2");
                pluginConfig.set("other.creeper-explode-blocks", true);
                pluginConfig.set("autospawn.piglin", 0);
            } else if (pluginConfig.getInt("config-version") == 12) {
                pluginConfig.set("entities.entity-creation-disabled", "exampledworld1, exampleworld2");
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
                pluginConfig.set("autospawn.piglin", 0);
                pluginConfig.set("other.creeper-explode-blocks", true);
                pluginConfig.set("other.check-tamed-entity-attack", true);
            } else if (pluginConfig.getInt("config-version") == 11) {
                pluginConfig.set("other.creeper-explode-blocks", true);
                pluginConfig.set("entities.entity-creation-disabled", "exampledworld1, exampleworld2");
                pluginConfig.set("other.spawners-enabled", true);
                pluginConfig.set("other.end-enderman-spawning", 10);
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
                pluginConfig.set("autospawn.piglin", 0);
                pluginConfig.set("other.check-tamed-entity-attack", true);
            } else if (pluginConfig.getInt("config-version") == 10) {
                pluginConfig.set("other.creeper-explode-blocks", true);
                pluginConfig.set("entities.entity-creation-disabled", "exampledworld1, exampleworld2");
                pluginConfig.set("other.kill-mobs-on-despawn", false);
                pluginConfig.set("other.spawners-enabled", true);
                pluginConfig.set("other.end-enderman-spawning", 10);
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
                pluginConfig.set("autospawn.piglin", 0);
                pluginConfig.set("other.check-tamed-entity-attack", true);
            } else if (pluginConfig.getInt("config-version") == 9) {
                pluginConfig.set("entities.entity-creation-disabled", "exampledworld1, exampleworld2");
                pluginConfig.set("other.creeper-explode-blocks", true);
                pluginConfig.set("other.spawn-no-spawning-area", -1);
                pluginConfig.set("other.kill-mobs-on-despawn", false);
                pluginConfig.set("other.spawners-enabled", true);
                pluginConfig.set("other.end-enderman-spawning", 10);
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
                pluginConfig.set("autospawn.piglin", 0);
                pluginConfig.set("other.check-tamed-entity-attack", true);
            } else {
                //plugin.getLogger().warning("MobPlugin's config file is outdated. Please delete the old config.");
                //plugin.getLogger().error("Config error. The plugin will be disabled.");
                //plugin.getServer().getPluginManager().disablePlugin(plugin);
                return true;
            }

            //pluginConfig.set("config-version", ver);
            //pluginConfig.save();
            //plugin.getLogger().notice("Config file updated to version " + ver);
        }

        spawnDelay = 0;// pluginConfig.getInt("entities.autospawn-ticks") >> 1; TODO: Must be set to however many ticks entities spawn if spontaneous spawning is ever required (recommend 200)
        noXpOrbs = false;//pluginConfig.getBoolean("other.use-no-xp-orbs"); TODO: Want no XP to be gained? Toggle it here.
        noSpawnEggWasting = false;//pluginConfig.getBoolean("other.do-not-waste-spawn-eggs"); TODO: Want infinitely mob-spawning dispensers? Enable this.
        despawnTicks = 0;//pluginConfig.getInt("entities.despawn-ticks"); TODO: Unless you'd like pets to disappear spontaneously, keep this as is.
        spawnerRange = 8;//pluginConfig.getInt("other.spawner-spawn-range"); TODO: Block radius, in case we ever need it.
        killOnDespawn = false;//pluginConfig.getBoolean("other.kill-mobs-on-despawn"); TODO: Don't know why you'd ever want this.
        endEndermanSpawnRate = 10;//pluginConfig.getInt("other.end-enderman-spawning"); TODO: Custom spawn rate for End biome.
        spawnersEnabled = true;//pluginConfig.getBoolean("other.spawners-enabled"); TODO: Have fun!
        checkTamedEntityAttack = false;//pluginConfig.getBoolean("other.check-tamed-entity-attack"); TODO: Pets are invulnerable anyway, but this turns off agro all the same.
        creeperExplodeBlocks = false;//pluginConfig.getBoolean("other.creeper-explode-blocks"); TODO: Turns off Creepers' griefing.
        return true;
    }
}
