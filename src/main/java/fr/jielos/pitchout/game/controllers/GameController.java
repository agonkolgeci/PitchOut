package fr.jielos.pitchout.game.controllers;

import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameComponent;
import fr.jielos.pitchout.game.player.PlayerData;
import fr.jielos.pitchout.game.references.Message;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Random;

public class GameController extends GameComponent {

    public GameController(Game game) {
        super(game);
    }

    public void addSpawn(final Location spawn) {
        game.getConfig().getSpawns().add(spawn);

        final int index = game.getConfig().getSpawns().size();
        game.getInstance().getConfig().set(String.format("spawns.%d", index), spawn);
    }

    public void clearContents(final Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setMaxHealth(20); player.setHealth(20);

        player.setLevel(0); player.setExp(0);

        for(final PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public void putSpectator(final Player player) {
        if(!game.getData().getSpectators().contains(player)) {
            game.getData().getSpectators().add(player);

            player.setGameMode(GameMode.SPECTATOR);

            clearContents(player);
        }
    }

    public void killPlayer(final Player player, final Player killer) {
        if(game.isPlay(player)) {
            final PlayerData playerData = game.getData().getPlayersData().get(player);

            playerData.decreaseHealth();
            playerData.updateObjective();

            game.getData().getLastDamages().remove(player);
            game.getData().getPlayersData().replace(player, playerData);

            if(playerData.getHealths() < 1) {
                eliminatePlayer(player, killer);
            } else {
                if(killer == null) {
                    game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_KILLED.getAsString(), player.getName(), playerData.getHealths()));
                } else {
                    game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_KILLED_BY_ANOTHER.getAsString(), player.getName(), killer.getName(), playerData.getHealths()));
                }

                final Location location = game.getConfig().getSpawns().get(new Random().nextInt(game.getConfig().getSpawns().size()));
                player.teleport(location);
                player.setMaxHealth(playerData.getMaxHealth());
            }
        }

        game.getScoreboardController().getPlayerBoard(player).update();

        if(killer != null) {
            game.getScoreboardController().getPlayerBoard(killer).update();
        }
    }

    public void eliminatePlayer(final Player player, final Player killer) {
        game.getData().getPlayers().remove(player);

        if(killer != null && killer != player) {
            game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_ELIMINATED_BY_ANOTHER.getAsString(), player.getName(), killer.getName()));
        } else {
            game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_ELIMINATED.getAsString(), player.getName()));
        }

        game.getScoreboardController().updateBoards();

        putSpectator(player);

        game.checkEnd();
    }
}
