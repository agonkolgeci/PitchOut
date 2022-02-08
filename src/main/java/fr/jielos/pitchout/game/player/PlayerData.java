package fr.jielos.pitchout.game.player;

import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameComponent;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerData extends GameComponent {

    final Player player;

    int healths;
    int kills;
    int deaths;

    public PlayerData(Game game, Player player) {
        super(game);

        this.player = player;

        this.healths = game.getConfig().getHealths();
        this.kills = 0;
    }

    public int getHealths() {
        return healths;
    }
    public void increaseHealth() {
        healths++;
    }
    public void decreaseHealth() {
        healths--;
    }

    public int getKills() {
        return kills;
    }
    public void increaseKill() {
        kills++;
    }

    public int getMaxHealth() {
        return healths*2;
    }

    public void updateObjective() {
        game.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(player.getName()).setScore(healths);
    }
}
