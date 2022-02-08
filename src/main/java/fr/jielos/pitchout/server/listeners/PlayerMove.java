package fr.jielos.pitchout.server.listeners;

import fr.jielos.pitchout.Main;
import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.player.PlayerData;
import fr.jielos.pitchout.game.references.Status;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Game game = Main.getGame();
        final Player player = event.getPlayer();

        if(game.getStatus() == Status.PLAY) {
            if(player.getLocation().getY() <= game.getConfig().getYLimit()) {
                if(game.isPlay(player)) {
                    if(game.getData().getLastDamages().containsKey(player)) {
                        final Player lastDamager = game.getData().getLastDamages().get(player);

                        if(lastDamager != player && game.isPlay(lastDamager)) {
                            final PlayerData lastDamagerData = game.getData().getPlayersData().get(lastDamager);

                            lastDamagerData.increaseKill();

                            game.getData().getPlayersData().replace(lastDamager, lastDamagerData);
                        }

                        game.getGameController().killPlayer(player, lastDamager);
                    } else {
                        game.getGameController().killPlayer(player, null);
                    }
                }
            }
        }
    }

}
