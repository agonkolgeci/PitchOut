package fr.jielos.pitchout.server.controllers;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class ServerController {

    final public JavaPlugin instance;
    public ServerController(final JavaPlugin instance) {
        this.instance = instance;
    }

    public abstract void register();

}
