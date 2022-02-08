package fr.jielos.pitchout.game.schedulers;

import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameScheduler;

public class GameEnd extends GameScheduler {

    public GameEnd(Game game) {
        super(game);
    }

    @Override
    public void run() {
        cancel();

        game.getInstance().getServer().reload();
    }
}
