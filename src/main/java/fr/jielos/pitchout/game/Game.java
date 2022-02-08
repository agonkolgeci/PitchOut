package fr.jielos.pitchout.game;

import fr.jielos.pitchout.game.controllers.GameController;
import fr.jielos.pitchout.game.controllers.ScoreboardController;
import fr.jielos.pitchout.game.data.Config;
import fr.jielos.pitchout.game.data.Data;
import fr.jielos.pitchout.game.player.PlayerData;
import fr.jielos.pitchout.game.references.Element;
import fr.jielos.pitchout.game.references.Message;
import fr.jielos.pitchout.game.references.Status;
import fr.jielos.pitchout.game.schedulers.GameEnd;
import fr.jielos.pitchout.game.schedulers.GameLaunch;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Random;

public class Game {

    final JavaPlugin instance;

    final GameController gameController;
    final ScoreboardController scoreboardController;

    final Data data;
    final Config config;

    Status status;

    GameLaunch gameLaunch;

    Player winner;

    public Game(final JavaPlugin instance) {
        this.instance = instance;

        this.gameController = new GameController(this);
        this.scoreboardController = new ScoreboardController(this);

        this.data = new Data(this);
        this.config = new Config(this);
    }

    public void init() {
        setStatus(Status.WAIT);

        Objective objective = getScoreboard().getObjective("healths");
        if(objective == null) {
            objective = getScoreboard().registerNewObjective("healths", "dummy");
        }

        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName("vie(s) restante(s)");

        for(final Player player : instance.getServer().getOnlinePlayers()) {
            gameController.clearContents(player);

            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(config.getWaitingRoom());

            data.getPlayers().add(player);
        }

        scoreboardController.updateBoards();

        checkLaunch();
    }

    public void launch() {
        setStatus(Status.LAUNCH);

        this.gameLaunch = new GameLaunch(this);
        this.gameLaunch.runTaskTimer(instance, 0 , 20);

        scoreboardController.updateBoards();
    }

    public void checkLaunch() {
        if(canLaunch()) {
            launch();
        }
    }

    public boolean canLaunch() {
        return data.getPlayers().size() >= config.getMinPlayers();
    }

    public void start() {
        setStatus(Status.PLAY);

        instance.getServer().broadcastMessage(Message.STARTING.getAsString());

        for(final Player player : data.getPlayers()) {
            final Location location = config.getSpawns().get(new Random().nextInt(config.getSpawns().size()));

            gameController.clearContents(player);
            player.teleport(location);

            final PlayerData playerData = new PlayerData(this, player);
            player.setMaxHealth(playerData.getMaxHealth());

            data.getPlayersData().put(player, playerData);

            player.setScoreboard(getScoreboard());
            playerData.updateObjective();

            for(final Element element : Element.values()) {
                player.getInventory().setItem(element.getSlot(), element.getItemStack());
            }
        }

        scoreboardController.updateBoards();
    }

    public void end(final Player winner) {
        setStatus(Status.END);
        this.winner = winner;

        for(final Player player : instance.getServer().getOnlinePlayers()) {
            player.teleport(config.getWaitingRoom());
            player.setGameMode(GameMode.ADVENTURE);

            gameController.clearContents(player);
        }

        if(isPlay(winner)) {
            final PlayerData attribute = data.getPlayersData().get(winner);
            instance.getServer().broadcastMessage(String.format(Message.PLAYER_WIN_GAME.getAsString(), winner.getName(), attribute.getHealths(), attribute.getKills()));
        }

        scoreboardController.updateBoards();

        new GameEnd(this).runTaskLater(instance, (config.getTimerEnd()* 20L));
    }

    public void checkEnd() {
        if(data.getPlayers().size() <= 1) {
            end(data.getPlayers().get(0));
        }
    }

    public boolean isPlay(final Player player) {
        return data.getPlayers().contains(player) && data.getPlayersData().containsKey(player);
    }

    public JavaPlugin getInstance() {
        return instance;
    }
    public Scoreboard getScoreboard() {
        return instance.getServer().getScoreboardManager().getMainScoreboard();
    }

    public GameController getGameController() {
        return gameController;
    }
    public ScoreboardController getScoreboardController() {
        return scoreboardController;
    }

    public Data getData() {
        return data;
    }
    public Config getConfig() {
        return config;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public GameLaunch getGameLaunch() {
        return gameLaunch;
    }

    public Player getWinner() {
        return winner;
    }
}
