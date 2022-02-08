package fr.jielos.pitchout.game.controllers;

import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameComponent;
import fr.jielos.pitchout.game.player.PlayerBoard;
import org.bukkit.entity.Player;

public class ScoreboardController extends GameComponent {

    public ScoreboardController(Game game) {
        super(game);
    }

    public void updateBoards() {
        for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
            final PlayerBoard playerScoreboard = getPlayerBoard(player);
            playerScoreboard.update();
        }
    }

    public void deleteBoards() {
        for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
            getPlayerBoard(player).destroy();
        }
    }

    public PlayerBoard getPlayerBoard(final Player player) {
        if(game.getData().getPlayersScoreboard().containsKey(player)) {
            return game.getData().getPlayersScoreboard().get(player);
        }

        return new PlayerBoard(game, player);
    }

}
