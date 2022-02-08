package fr.jielos.pitchout.server.commands;

import fr.jielos.pitchout.Main;
import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.references.Message;
import fr.jielos.pitchout.game.references.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandForcestart implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Game game = Main.getGame();

        if(game.getStatus() == Status.WAIT || game.getStatus() == Status.LAUNCH) {
            if(game.getData().getPlayers().size() >= 2) {
                sender.sendMessage(Message.START_FORCED.getAsString());

                if(game.getGameLaunch() != null) {
                    game.getGameLaunch().cancel();
                }

                game.start();

                return true;
            }
        }

        sender.sendMessage(Message.START_CANNOT_BE_FORCED.getAsString());

        return false;

    }
}
