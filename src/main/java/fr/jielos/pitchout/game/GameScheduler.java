package fr.jielos.pitchout.game;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class GameScheduler extends BukkitRunnable {

    final public Game game;
    public GameScheduler(final Game game) {
        this.game = game;
    }

}
