package fr.jielos.pitchout.server.listeners;

import fr.jielos.pitchout.Main;
import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.references.Message;
import fr.jielos.pitchout.game.references.Status;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Game game = Main.getGame();
        final Player player = event.getPlayer();

        event.setQuitMessage(null);

        if(game.getStatus() == Status.WAIT || game.getStatus() == Status.LAUNCH) {
            game.getData().getPlayers().remove(player);

            event.setQuitMessage(String.format(Message.PLAYER_QUIT.getAsString(), player.getName(), game.getData().getPlayers().size(), game.getConfig().getMaxPlayers()));
        } else if(game.getStatus() == Status.PLAY) {
            game.getGameController().eliminatePlayer(player, null);

            event.setQuitMessage(String.format(Message.PLAYER_LEAVE.getAsString(), player.getName()));
        }

        game.getData().getSpectators().remove(player);
        game.getScoreboardController().getPlayerBoard(player).destroy();
        game.getScoreboardController().updateBoards();
    }

}
