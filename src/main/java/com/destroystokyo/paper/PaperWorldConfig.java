package com.destroystokyo.paper;

import org.bukkit.configuration.file.YamlConfiguration;
import org.spigotmc.SpigotWorldConfig;

import java.util.List;

import static com.destroystokyo.paper.PaperConfig.log;

public class PaperWorldConfig {

    private final String worldName;
    private final SpigotWorldConfig spigotConfig;
    private final YamlConfiguration config;
    private boolean verbose;

    public PaperWorldConfig(String worldName, SpigotWorldConfig spigotConfig) {
        this.worldName = worldName;
        this.spigotConfig = spigotConfig;
        this.config = PaperConfig.config;
        init();
    }

    public void init() {
        log("-------- World Settings For [" + worldName + "] --------");
        PaperConfig.readConfig(PaperWorldConfig.class, this);
    }

    private void set(String path, Object val) {
        config.set("world-settings.default." + path, val);
        if (config.get("world-settings." + worldName + "." + path) != null) {
            config.set("world-settings." + worldName + "." + path, val);
        }
    }

    private boolean getBoolean(String path, boolean def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getBoolean("world-settings." + worldName + "." + path, config.getBoolean("world-settings.default." + path));
    }

    private double getDouble(String path, double def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getDouble("world-settings." + worldName + "." + path, config.getDouble("world-settings.default." + path));
    }

    private int getInt(String path, int def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getInt("world-settings." + worldName + "." + path, config.getInt("world-settings.default." + path));
    }

    private float getFloat(String path, float def) {
        // TODO: Figure out why getFloat() always returns the default value.
        return (float) getDouble(path, (double) def);
    }

    private <T> List getList(String path, T def) {
        config.addDefault("world-settings.default." + path, def);
        return (List<T>) config.getList("world-settings." + worldName + "." + path, config.getList("world-settings.default." + path));
    }

    private String getString(String path, String def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getString("world-settings." + worldName + "." + path, config.getString("world-settings.default." + path));
    }

    public int cactusMaxHeight;
    public int reedMaxHeight;

    private void blockGrowthHeight() {
        cactusMaxHeight = getInt("max-growth-height.cactus", 3);
        reedMaxHeight = getInt("max-growth-height.reeds", 3);
        log("Max height for cactus growth " + cactusMaxHeight + ". Max height for reed growth " + reedMaxHeight);
    }

    public double babyZombieMovementSpeed;

    private void babyZombieMovementSpeed() {
        babyZombieMovementSpeed = getDouble("baby-zombie-movement-speed", 0.5D); // Player moves at 0.1F, for reference
        log("Baby zombies will move at the speed of " + babyZombieMovementSpeed);
    }

    public int fishingMinTicks;
    public int fishingMaxTicks;

    private void fishingTickRange() {
        fishingMinTicks = getInt("fishing-time-range.MinimumTicks", 100);
        fishingMaxTicks = getInt("fishing-time-range.MaximumTicks", 600);
        log("Fishing time ranges are between " + fishingMinTicks + " and " + fishingMaxTicks + " ticks");
    }

    public boolean nerfedMobsShouldJump;

    private void nerfedMobsShouldJump() {
        nerfedMobsShouldJump = getBoolean("spawner-nerfed-mobs-should-jump", false);
    }

    public int softDespawnDistance;
    public int hardDespawnDistance;

    private void despawnDistances() {
        softDespawnDistance = getInt("despawn-ranges.soft", 32); // 32^2 = 1024, Minecraft Default
        hardDespawnDistance = getInt("despawn-ranges.hard", 128); // 128^2 = 16384, Minecraft Default

        if (softDespawnDistance > hardDespawnDistance) {
            softDespawnDistance = hardDespawnDistance;
        }

        log("Living Entity Despawn Ranges:  Soft: " + softDespawnDistance + " Hard: " + hardDespawnDistance);

        softDespawnDistance = softDespawnDistance * softDespawnDistance;
        hardDespawnDistance = hardDespawnDistance * hardDespawnDistance;
    }

    public boolean keepSpawnInMemory;

    private void keepSpawnInMemory() {
        keepSpawnInMemory = getBoolean("keep-spawn-loaded", true);
        log("Keep spawn chunk loaded: " + keepSpawnInMemory);
    }

    public int fallingBlockHeightNerf;
    public int entityTNTHeightNerf;

    private void heightNerfs() {
        fallingBlockHeightNerf = getInt("falling-block-height-nerf", 0);
        entityTNTHeightNerf = getInt("tnt-entity-height-nerf", 0);
        if (fallingBlockHeightNerf != 0) log("Falling Block Height Limit set to Y: " + fallingBlockHeightNerf);
        if (entityTNTHeightNerf != 0) log("TNT Entity Height Limit set to Y: " + entityTNTHeightNerf);
    }

    public boolean disableEndCredits;

    private void disableEndCredits() {
        disableEndCredits = getBoolean("game-mechanics.disable-end-credits", false);
        log("End credits disabled: " + disableEndCredits);
    }

    public boolean allowLeashingUndeadHorse = false;

    private void allowLeashingUndeadHorse() {
        allowLeashingUndeadHorse = getBoolean("allow-leashing-undead-horse", false);
    }

    public boolean armorStandEntityLookups = true;

    private void armorStandEntityLookups() {
        armorStandEntityLookups = getBoolean("armor-stands-do-collision-entity-lookups", true);
    }

    public boolean useInhabitedTime = true;

    private void useInhabitedTime() {
        useInhabitedTime = getBoolean("use-chunk-inhabited-timer", true);
    }

    public boolean disableIceAndSnow;

    private void disableIceAndSnow() {
        disableIceAndSnow = getBoolean("disable-ice-and-snow", false);
    }

    public boolean disableThunder;

    private void disableThunder() {
        disableThunder = getBoolean("disable-thunder", false);
    }

    public int maxChunkSendsPerTick = 81;

    private void maxChunkSendsPerTick() {
        maxChunkSendsPerTick = getInt("max-chunk-sends-per-tick", maxChunkSendsPerTick);
        if (maxChunkSendsPerTick <= 0) {
            maxChunkSendsPerTick = 81;
        }
        log("Max Chunk Sends Per Tick: " + maxChunkSendsPerTick);
    }

    public boolean disableChestCatDetection;

    private void disableChestCatDetection() {
        disableChestCatDetection = getBoolean("game-mechanics.disable-chest-cat-detection", false);
    }

    public int maxChunkGensPerTick = 10;

    private void maxChunkGensPerTick() {
        maxChunkGensPerTick = getInt("max-chunk-gens-per-tick", maxChunkGensPerTick);
        if (maxChunkGensPerTick <= 0) {
            maxChunkGensPerTick = Integer.MAX_VALUE;
            log("Max Chunk Gens Per Tick: Unlimited (NOT RECOMMENDED)");
        } else {
            log("Max Chunk Gens Per Tick: " + maxChunkGensPerTick);
        }
    }

    public boolean queueLightUpdates;

    private void queueLightUpdates() {
        queueLightUpdates = getBoolean("queue-light-updates", false);
        log("Lighting Queue enabled: " + queueLightUpdates);
    }

    public int shieldBlockingDelay = 5;

    private void shieldBlockingDelay() {
        shieldBlockingDelay = getInt("game-mechanics.shield-blocking-delay", 5);
    }

    public int waterOverLavaFlowSpeed;

    private void waterOverLawFlowSpeed() {
        waterOverLavaFlowSpeed = getInt("water-over-lava-flow-speed", 5);
        log("Water over lava flow speed: " + waterOverLavaFlowSpeed);
    }

    public boolean netherVoidTopDamage;

    private void netherVoidTopDamage() {
        netherVoidTopDamage = getBoolean("nether-ceiling-void-damage", false);
        log("Top of the nether void damage: " + netherVoidTopDamage);
    }

    public int lavaFlowSpeedNormal;
    public int lavaFlowSpeedNether;

    private void lavaFlowSpeeds() {
        lavaFlowSpeedNormal = getInt("lava-flow-speed.normal", 30);
        lavaFlowSpeedNether = getInt("lava-flow-speed.nether", 10);
    }

    public int mobSpawnerTickRate;

    private void mobSpawnerTickRate() {
        mobSpawnerTickRate = getInt("mob-spawner-tick-rate", 1);
    }

    public int containerUpdateTickRate;

    private void containerUpdateTickRate() {
        containerUpdateTickRate = getInt("container-update-tick-rate", 1);
    }

    public boolean disableTeleportationSuffocationCheck;

    private void disableTeleportationSuffocationCheck() {
        disableTeleportationSuffocationCheck = getBoolean("disable-teleportation-suffocation-check", false);
    }

    public int nonPlayerArrowDespawnRate = -1;

    private void nonPlayerArrowDespawnRate() {
        nonPlayerArrowDespawnRate = getInt("non-player-arrow-despawn-rate", -1);
        if (nonPlayerArrowDespawnRate == -1) {
            nonPlayerArrowDespawnRate = spigotConfig.arrowDespawnRate;
        }
        log("Non Player Arrow Despawn Rate: " + nonPlayerArrowDespawnRate);
    }

    public double skeleHorseSpawnChance;

    private void skeleHorseSpawnChance() {
        skeleHorseSpawnChance = getDouble("skeleton-horse-thunder-spawn-chance", 0.01D); // -1.0D represents a "vanilla" state
    }

    public int grassUpdateRate = 1;

    private void grassUpdateRate() {
        grassUpdateRate = Math.max(0, getInt("grass-spread-tick-rate", grassUpdateRate));
        log("Grass Spread Tick Rate: " + grassUpdateRate);
    }

    public short keepLoadedRange;

    private void keepLoadedRange() {
        keepLoadedRange = (short) (getInt("keep-spawn-loaded-range", Math.min(spigotConfig.viewDistance, 8)) * 16);
        log("Keep Spawn Loaded Range: " + (keepLoadedRange / 16));
    }

    public boolean enableTreasureMaps = true;
    public boolean treasureMapsAlreadyDiscovered = false;

    private void treasureMapsAlreadyDiscovered() {
        enableTreasureMaps = getBoolean("enable-treasure-maps", true);
        treasureMapsAlreadyDiscovered = getBoolean("treasure-maps-return-already-discovered", false);
        if (treasureMapsAlreadyDiscovered) {
            log("Treasure Maps will return already discovered locations");
        }
    }

    public boolean disableSprintInterruptionOnAttack;

    private void disableSprintInterruptionOnAttack() {
        disableSprintInterruptionOnAttack = getBoolean("game-mechanics.disable-sprint-interruption-on-attack", false);
    }

    public boolean allowPermaChunkLoaders = false;

    private void allowPermaChunkLoaders() {
        allowPermaChunkLoaders = getBoolean("game-mechanics.allow-permanent-chunk-loaders", allowPermaChunkLoaders);
        log("Allow Perma Chunk Loaders: " + (allowPermaChunkLoaders ? "enabled" : "disabled"));
    }

    public int bedSearchRadius = 1;
    private void bedSearchRadius() {
        bedSearchRadius = getInt("bed-search-radius", 1);
        if (bedSearchRadius < 1) {
            bedSearchRadius = 1;
        }
        if (bedSearchRadius > 1) {
            log("Bed Search Radius: " + bedSearchRadius);
        }
    }

    public int queueSizeAutoSaveThreshold = 50;
    private void queueSizeAutoSaveThreshold() {
        queueSizeAutoSaveThreshold = getInt("save-queue-limit-for-auto-save", 50);
    }

    public boolean autoReplenishLootables;
    public boolean restrictPlayerReloot;
    public boolean changeLootTableSeedOnFill;
    public int maxLootableRefills;
    public int lootableRegenMin;
    public int lootableRegenMax;
    private void enhancedLootables() {
        autoReplenishLootables = getBoolean("lootables.auto-replenish", false);
        restrictPlayerReloot = getBoolean("lootables.restrict-player-reloot", true);
        changeLootTableSeedOnFill = getBoolean("lootables.reset-seed-on-fill", true);
        maxLootableRefills = getInt("lootables.max-refills", -1);
        lootableRegenMin = PaperConfig.getSeconds(getString("lootables.refresh-min", "12h"));
        lootableRegenMax = PaperConfig.getSeconds(getString("lootables.refresh-max", "2d"));
        if (autoReplenishLootables) {
            log("Lootables: Replenishing every " +
                    PaperConfig.timeSummary(lootableRegenMin) + " to " +
                    PaperConfig.timeSummary(lootableRegenMax) +
                    (restrictPlayerReloot ? " (restricting reloot)" : "")
            );
        }
    }
}
