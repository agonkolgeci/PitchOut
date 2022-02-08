package fr.jielos.pitchout.server.listeners;

import fr.jielos.pitchout.Main;
import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.references.Message;
import fr.jielos.pitchout.game.references.Status;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Game game = Main.getGame();
        final Player player = event.getPlayer();

        event.setJoinMessage(null);

        if(game.getStatus() == Status.WAIT || game.getStatus() == Status.LAUNCH) {
            if(!game.getData().getPlayers().contains(player)) {
                game.getData().getPlayers().add(player);

                game.getGameController().clearContents(player);
                player.setGameMode(GameMode.ADVENTURE);

                event.setJoinMessage(String.format(Message.PLAYER_JOIN.getAsString(), player.getName(), game.getData().getPlayers().size(), game.getConfig().getMaxPlayers()));

                if(game.getStatus() == Status.WAIT) {
                    game.checkLaunch();
                }
            }
        } else {
            game.getGameController().putSpectator(player);
        }

        player.teleport(game.getConfig().getWaitingRoom());
        game.getScoreboardController().updateBoards();
    }

}
