package fr.jielos.pitchout.game.references;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Status {

    WAIT(new ArrayList<>(Arrays.asList("", "§a§lStatus", "§7- §fJoueurs: §e{players_size}", "§7- §fCarte: §3{map_name}", " ", "§7§oEn attente de §e{players_missing} §7j.", "§7§opour démarrer la partie."))),

    LAUNCH(new ArrayList<>(Arrays.asList("", "§a§lStatut", "§7- §fJoueurs: §e{players_size}", "§7- §fCarte: §3{map_name}", "§7- §fLancement: §a{timer_launch}s"))),

    PLAY(new ArrayList<>(Arrays.asList("", "§a§lPartie", "§7- §fJoueurs restant: §e{players_size}", " ", "§6§lMes statistiques", "§7- §fVie(s): §c{healths} ❤", "§7- §fTué(s): §a{kills} ⚔"))),

    END(new ArrayList<>(Arrays.asList("", "§7Félicitations à §e{winner}", "§7qui remporte la partie !", " ", "§6§lMes statistiques", "§7- §fVie(s): §c{healths} ❤", "§7- §fTué(s): §a{kills} ⚔")));

    final List<String> scoreboardLines;

    Status(final List<String> scoreboardLines) {
        this.scoreboardLines = scoreboardLines;
    }

    public List<String> getScoreboardLines() {
        return scoreboardLines;
    }
}
