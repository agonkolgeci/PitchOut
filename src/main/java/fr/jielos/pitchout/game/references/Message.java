package fr.jielos.pitchout.game.references;

public enum Message {

    START_FORCED("§7§oVous avez forcé le démarrage de la partie."),
    START_CANNOT_BE_FORCED("§c§oVous ne pouvez pas forcer le démarrage de la partie, puisque celle-ci a déjà démarrer ou alors il n'y a pas assez de joueurs."),

    PLAYER_JOIN("§7%s §ea rejoint la partie ! §a(%d/%d)"),
    PLAYER_QUIT("§7%s §ea quitté la partie ! §c(%d/%d)"),
    PLAYER_LEAVE("§7§o%s a quitté la partie!"),

    STARTING_SOON("§eDébut de la partie dans §6%d seconde%s §e!"),
    STARTING_CANCELED("§c§oLancement de la partie annulé, il n'y a pas assez de joueurs!"),
    STARTING("§eLancement de la partie en cours§6.. §e!"),

    PLAYER_KILLED("§c%s §7est tombé(e) et possède désormais §c%d vie(s) restantes ❤ §7!"),
    PLAYER_KILLED_BY_ANOTHER("§c%s §7a été expulsé par §a%s §7et possède désormais §c%d vie(s) restantes ❤ §7!"),
    PLAYER_ELIMINATED("§7§l%s §6vient d'être éliminé!"),
    PLAYER_ELIMINATED_BY_ANOTHER("§7§l%s §6vient d'être eliminé par §e§l%s."),

    PLAYER_WIN_GAME("§6Félicitations à §a§l%s§r §6qui remporte la partie avec un total de §7§l%d vie(s) restante(s) §6pour §7§l%d personne(s) tuée(s) §6!"),
    KICK_GAME_END("\n§4Impossible de rejoindre cette partie!\n§cLa partie est malheureusement terminée.");;

    final String string;

    Message(final String string) {
        this.string = string;
    }

    public String getAsString() {
        return string;
    }


}
