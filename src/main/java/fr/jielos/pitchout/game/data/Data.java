package fr.jielos.pitchout.game.data;

import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameComponent;
import fr.jielos.pitchout.game.player.PlayerData;
import fr.jielos.pitchout.game.player.PlayerBoard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data extends GameComponent {

    final List<Player> players;
    final List<Player> spectators;

    final Map<Player, PlayerData> playersData;
    final Map<Player, PlayerBoard> playersBoards;

    final Map<Player, Player> lastDamages;

    public Data(Game game) {
        super(game);

        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();

        this.playersData = new HashMap<>();
        this.playersBoards = new HashMap<>();

        this.lastDamages = new HashMap<>();
    }

    public List<Player> getPlayers() {
        return players;
    }
    public List<Player> getSpectators() {
        return spectators;
    }

    public Map<Player, PlayerData> getPlayersData() {
        return playersData;
    }
    public Map<Player, PlayerBoard> getPlayersScoreboard() {
        return playersBoards;
    }

    public Map<Player, Player> getLastDamages() {
        return lastDamages;
    }
}
