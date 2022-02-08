package fr.jielos.pitchout.game.data;

import fr.jielos.pitchout.components.Cuboid;
import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameComponent;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Config extends GameComponent {

    final int minPlayers;
    final int maxPlayers;
    final String mapName;

    final int healths;
    final int yLimit;

    final int timerLaunch;
    final int timerDeathMatch;
    final int timerEnd;

    final String scoreboardTitle;
    final boolean scoreboardFooter;
    final String scoreboardFooterContent;

    final Location waitingRoom;
    final List<Location> spawns;

    public Config(Game game) {
        super(game);

        final FileConfiguration fileConfiguration = game.getInstance().getConfig();

        this.minPlayers = fileConfiguration.getInt("minPlayers");
        this.maxPlayers = fileConfiguration.getInt("maxPlayers");
        this.mapName = fileConfiguration.getString("mapName");

        this.healths = fileConfiguration.getInt("rules.healths");
        this.yLimit = fileConfiguration.getInt("rules.yLimit");

        this.timerLaunch = fileConfiguration.getInt("timers.launch");
        this.timerDeathMatch = fileConfiguration.getInt("timers.deathMatch");
        this.timerEnd = fileConfiguration.getInt("timers.end");

        this.scoreboardTitle = fileConfiguration.getString("scoreboard.title");
        this.scoreboardFooter = fileConfiguration.getBoolean("scoreboard.footer.enabled");
        this.scoreboardFooterContent = fileConfiguration.getString("scoreboard.footer.content");

        this.waitingRoom = (Location) fileConfiguration.get("locations.waitingRoom");

        this.spawns = new ArrayList<>();

        final Cuboid cuboid = new Cuboid((Location) fileConfiguration.get("locations.spawns.cuboid.1"), (Location) fileConfiguration.get("locations.spawns.cuboid.2"));
        final Material material = Material.getMaterial(fileConfiguration.getInt("locations.spawns.blockId"));
        for(final Block block : cuboid.getBlocks().stream().filter(block -> block.getType() == material).collect(Collectors.toList())) {
            final Location location = block.getLocation().clone().add(.5, 1.5, .5);
            location.setYaw(getAngle(new Vector(location.getX(), 0, location.getZ()), waitingRoom.toVector()));

            spawns.add(location);
        }
    }

    public int getMinPlayers() {
        return Math.max(minPlayers, 2);
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }
    public String getMapName() {
        return mapName;
    }

    public int getHealths() {
        return healths;
    }
    public int getYLimit() {
        return yLimit;
    }

    public int getTimerLaunch() {
        return timerLaunch;
    }
    public int getTimerDeathMatch() {
        return timerDeathMatch;
    }
    public int getTimerEnd() {
        return timerEnd;
    }

    public String getScoreboardTitle() {
        return scoreboardTitle;
    }
    public boolean isScoreboardFooter() {
        return scoreboardFooter;
    }
    public String getScoreboardFooterContent() {
        return scoreboardFooterContent;
    }

    public Location getWaitingRoom() {
        return waitingRoom;
    }
    public List<Location> getSpawns() {
        return spawns;
    }

    private float getAngle(final Vector point1, final Vector point2) {
        double distanceX = point2.getX() - point1.getX();
        double distanceZ = point2.getZ() - point1.getZ();

        float angle = (float) Math.toDegrees(Math.atan2(distanceZ, distanceX)) - 90;
        if(angle < 0) {
            angle += 360.0F;
        }

        return angle;
    }
}
