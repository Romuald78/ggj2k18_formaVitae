package fr.rphstudio.game.factories;

import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.game.components.ListEnemy;
import fr.rphstudio.game.scripts.ScriptGenerateEnemy;
import org.newdawn.slick.SlickException;

import java.util.List;

public class FactoryEnemyManager {
    public static Entity create(ECSWorld world, int id, List<Entity> players, List<Entity> virus) throws SlickException
    {
        //===========================================================
        // ENTITY
        //===========================================================

        //----------------------
        // Create Entity
        Entity entity = new Entity(world, "ENEMY_GENERATOR"+Integer.toString(id));

        //===========================================================
        // COMPONENTS
        //===========================================================
        entity.addComponent(new ListEnemy());

        //===========================================================
        // SCRIPTS
        //===========================================================
        entity.addComponent(new ScriptGenerateEnemy(world, players, virus));

        //----------------------
        // Return entity
        //----------------------
        return entity;
    }
}
