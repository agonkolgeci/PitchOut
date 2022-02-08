package fr.jielos.pitchout.server.listeners;

import fr.jielos.pitchout.Main;
import fr.jielos.pitchout.game.Game;
import fr.jielos.pitchout.game.references.Status;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        final Game game = Main.getGame();
        final Entity entity = event.getEntity();

        if(entity instanceof Player) {
            if(game.getStatus() == Status.PLAY) {
                event.setDamage(0);
            } else {
                event.setCancelled(true);
            }
        }
    }

}
