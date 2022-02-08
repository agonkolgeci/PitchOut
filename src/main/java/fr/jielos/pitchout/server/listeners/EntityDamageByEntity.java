package fr.jielos.pitchout.server.listeners;

import fr.jielos.pitchout.Main;
import fr.jielos.pitchout.game.Game;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntity implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        final Game game = Main.getGame();

        final Entity entityDamaged = event.getEntity();
        if(entityDamaged instanceof Player) {
            final Player damaged = (Player) entityDamaged;
            final Entity entityDamager = event.getDamager();

            Player damager = null;

            if(entityDamager instanceof Player) {
                damager = (Player) entityDamager;
            }

            if(entityDamager instanceof Arrow) {
                final Arrow arrow = (Arrow) entityDamager;
                final ProjectileSource projectileSource = arrow.getShooter();
                if(projectileSource instanceof Player) {
                    damager = (Player) projectileSource;
                }
            }

            if(damager != null && damager != damaged) {
                game.getData().getLastDamages().put(damaged, damager);
            }
        }
    }

}
