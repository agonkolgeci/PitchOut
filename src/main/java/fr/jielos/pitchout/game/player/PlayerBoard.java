package fr.jielos.pitchout.game.player;

import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.GameComponent;
import fr.jielos.pitchout.game.references.Status;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerBoard extends GameComponent {

    final Player player;
    final BPlayerBoard board;
    public PlayerBoard(final Game game, final Player player) {
        super(game);

        this.player = player;
        this.board = Netherboard.instance().createBoard(player, game.getConfig().getScoreboardTitle());

        game.getData().getPlayersScoreboard().put(player, this);
    }

    public void update() {
        final List<String> lines = new ArrayList<>(game.getStatus().getScoreboardLines());
        final Map<String, String> remplacements = new HashMap<>();

        /* Remplacements */

        remplacements.put("{players_size}", String.valueOf(game.getData().getPlayers().size()));
        remplacements.put("{players_missing}", String.valueOf(game.getConfig().getMinPlayers() - game.getData().getPlayers().size()));
        remplacements.put("{map_name}", game.getConfig().getMapName());

        if(game.getStatus() == Status.LAUNCH) {
            remplacements.put("{timer_launch}", String.valueOf(game.getGameLaunch().getSeconds()));
        }

        if(game.getData().getPlayersData().containsKey(player)) {
            final PlayerData playerData = game.getData().getPlayersData().get(player);

            remplacements.put("{healths}", String.valueOf(playerData.getHealths()));
            remplacements.put("{kills}", String.valueOf(playerData.getKills()));
        }

        if(game.getStatus() == Status.END) {
            remplacements.put("{winner}", game.getWinner().getName());
        }

        /* -- */

        for(final Map.Entry<String, String> remplacement : remplacements.entrySet()) {
            lines.replaceAll(line -> line.replace(remplacement.getKey(), remplacement.getValue()));
        }

        if(game.getConfig().isScoreboardFooter()) {
            lines.addAll(Arrays.asList("§k", game.getConfig().getScoreboardFooterContent()));
        }

        board.setAll(lines.toArray(new String[0]));
    }

    public void destroy() {
        game.getData().getPlayersScoreboard().remove(player);
        board.delete();
    }

    public BPlayerBoard getBoard() {
        return board;
    }

}
