package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.component.sfx.SoundFX;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.game.components.CurrentShape;
import fr.rphstudio.game.components.EnemyState;
import fr.rphstudio.launcher.Common;
import org.newdawn.slick.SlickException;

import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class ScriptEnemyStatus implements IComponent, IScript {
    private final long       id;
    private final String     name;
    private RenderAnimations        animVirus;
    private List<RenderAnimations>  listAnimPlayer;
    private CurrentShape     shapeVirus;
    private EnemyState       enemyState;
    private boolean          matched = false;
    private SoundFX          sfx;

    public ScriptEnemyStatus(List<RenderAnimations> listAnimP, RenderAnimations animV, CurrentShape shapeV, EnemyState enemyState, SoundFX fx)
    {
        // Store process fields
        this.listAnimPlayer = listAnimP;
        this.animVirus      = animV;
        this.enemyState     = enemyState;
        this.sfx            = fx;
        this.shapeVirus     = shapeV;
        // Get name of the component
        this.name           = getClass().getName();
        // Get unique ID
        this.id             = CoreUtils.getNewID();
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
        if (!matched)
        {
            // Get position for this virus
            Vector2f posV  = this.animVirus.getPosition();
            // Get animation for this virus
            int animIndexV = this.animVirus.getCurrentAnimIndex();
            // Get rotation for this virus
            float rotateV  = this.animVirus.getAngle();
            // For all the players
            for(RenderAnimations renP:this.listAnimPlayer)
            {
                //----------------------------
                // Get player position
                Vector2f posP = renP.getPosition();
                // Check position
                if(Math.abs(posP.x-posV.x) <= Common.MATCH_DELTA_POSITION_X)
                {
                    if(Math.abs(posP.y-posV.y) <= Common.MATCH_DELTA_POSITION_Y)
                    {
                        //----------------------------
                        // Get player animation index
                        int animIndexP = renP.getCurrentAnimIndex();
                        if(animIndexV == animIndexP+Common.SHAPE_INDEX_NB)
                        {
                            boolean isAngleOK = false;
                            float   rotateP   = renP.getAngle();
                            float   angleDiff = Math.abs(rotateP-rotateV);
                            if(angleDiff > 180)
                            {
                                angleDiff = 360-angleDiff;
                            }
                            switch(animIndexP)
                            {
                                // Useless to set angle for a circle
                                case Common.SHAPE_INDEX_CIRCLE:
                                    isAngleOK = true;
                                    break;
                                // Cross and Square needs a 90 degree multiple angle
                                case Common.SHAPE_INDEX_CROSS:
                                case Common.SHAPE_INDEX_SQUARE:
                                    angleDiff %= 90;
                                    if(angleDiff > 45)
                                    {
                                        angleDiff = 90-angleDiff;
                                    }
                                // Triangle shape needs a perfect angle so no modification is required
                                case Common.SHAPE_INDEX_TRIANGLE:
                                    if(angleDiff <= Common.MATCH_DELTA_ANGLE)
                                    {
                                        isAngleOK = true;
                                    }
                                    break;
                                // Other shapes are forbidden
                                default:
                                    throw new Error("[ERROR] shape number forbidden !");
                            }
                            //----------------------------
                            // We have found a candidate
                            if(isAngleOK)
                            {
                                this.matched = true;
                                // clean the virus status
                                this.enemyState.uncorrupt();
                                // play sound
                                this.sfx.playSound();
                                // Modify animation to switch to the "white ones" (not the black corrupted cell anymore)
                                this.shapeVirus.selectShape(animIndexP);
                                // Set color for animation to make it different view
                                this.animVirus.setAllColor( new Color(237,59,42,128) );
                                // Just exit
                                break;
                            }
                        }
                    }   
                }
            }
        }
    }
}
