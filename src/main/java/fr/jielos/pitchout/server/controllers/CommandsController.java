package fr.jielos.pitchout.server.controllers;

import fr.jielos.pitchout.server.commands.CommandForcestart;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandsController extends ServerController {

    public CommandsController(JavaPlugin instance) {
        super(instance);
    }

    @Override
    public void register() {
        instance.getCommand("forcestart").setExecutor(new CommandForcestart());
    }
}
