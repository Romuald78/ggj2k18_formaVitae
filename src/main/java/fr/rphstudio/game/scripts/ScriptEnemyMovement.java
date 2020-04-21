package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.physic.Physic2D;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.game.components.CurrentShape;
import fr.rphstudio.launcher.Common;
import org.newdawn.slick.SlickException;

import java.util.List;

public class ScriptEnemyMovement implements IComponent, IScript {
    private final long     id;
    private final String   name;
    private RenderAnimations animations;
    private Position     position;
    private Physic2D     physicComponent;
    private int          nbPlayers;
    private CurrentShape curShap;
    
    private float angle;
    private float angleStepMax;
    
    public ScriptEnemyMovement(Physic2D physicComponent, CurrentShape shapeEnemy, RenderAnimations animations, int nbPlay) {
        this.physicComponent = physicComponent;
        this.nbPlayers = nbPlay;
        this.animations = animations;
        this.position = position;
        this.name = getClass().getName();
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init angle to down
        this.angleStepMax = 80;
        this.angle = 90;
        this.curShap = shapeEnemy;
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(int delta) throws SlickException
    {
        // Process the movement update
        if(this.physicComponent != null)
        {
            // Get angle of the force
            float ang = this.angle + (float)(Math.random()*this.angleStepMax*2) - this.angleStepMax;
            ang *= (float)(Math.PI / 180);
            float amp = (float)(Math.random()*0.04)+0.02f;
            // Multiply amplitude according to the shape
            int i = Common.SHAPE_INDEX_CIRCLE+Common.SHAPE_INDEX_NB;
            if(i == this.curShap.getCurrentShapeIndex())
            {
                amp *= 3;
            }
            i = Common.SHAPE_INDEX_SQUARE+Common.SHAPE_INDEX_NB;
            if(i == this.curShap.getCurrentShapeIndex())
            {
                amp *= 2;
            }
            i = Common.SHAPE_INDEX_CROSS+Common.SHAPE_INDEX_NB;
            if(i == this.curShap.getCurrentShapeIndex())
            {
                amp /= 2;
            } 
            // in all cases multiply amplitude by the number of players
            amp *= this.nbPlayers;
            // Set force
            float dx  = amp*(float)Math.cos(ang);
            float dy  = amp*(float)Math.sin(ang);
            this.physicComponent.setForce(dx, dy);
        }
        //*/
    }
}
