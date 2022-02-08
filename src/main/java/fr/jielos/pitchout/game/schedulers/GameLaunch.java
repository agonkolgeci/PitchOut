package fr.jielos.pitchout.game.schedulers;

import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameScheduler;
import fr.jielos.pitchout.game.references.Message;
import fr.jielos.pitchout.game.references.Status;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameLaunch extends GameScheduler {

    public GameLaunch(Game game) {
        super(game);

        this.seconds = game.getConfig().getTimerLaunch();
    }

    int seconds;

    @Override
    public void run() {
        if(game.canLaunch()) {
            if(seconds > 0) {
                if(seconds % 10 == 0 || seconds <= 5) {
                    game.getInstance().getServer().broadcastMessage(String.format(Message.STARTING_SOON.getAsString(), seconds, (seconds > 1 ? "s" : "")));

                    for(final Player player : game.getData().getPlayers()) {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                    }
                }

                for(final Player player : game.getData().getPlayers()) {
                    player.setLevel(seconds);
                }

                game.getScoreboardController().updateBoards();

                seconds--;
            } else {
                cancel();

                game.start();
            }
        } else {
            cancel();

            game.setStatus(Status.WAIT);
            game.getScoreboardController().updateBoards();

            game.getInstance().getServer().broadcastMessage(Message.STARTING_CANCELED.getAsString());
        }
    }

    public int getSeconds() {
        return seconds;
    }
}
