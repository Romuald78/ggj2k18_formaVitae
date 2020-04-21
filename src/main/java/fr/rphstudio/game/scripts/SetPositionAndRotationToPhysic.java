/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.physic.Physic2D;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;

/**
 *
 * @author GRIGNON FAMILY
 */
public class SetPositionAndRotationToPhysic implements IComponent, IScript
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long       id;
    private final String     name;
    private Position         posCmp  = null;
    private Physic2D         phyCmp  = null;
    private int              bodyRef = 0;
    private RenderAnimations render;
    private float            relativeAngle;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Position pos, RenderAnimations ren, Physic2D phy, float relAng, int bdyRf)
    {
        // Store components
        this.posCmp  = pos;
        this.phyCmp  = phy;
        this.bodyRef = bdyRf;
        this.render  = ren;
        this.relativeAngle = relAng;
    }
    public SetPositionAndRotationToPhysic(Position pos, RenderAnimations ren, Physic2D phy, float relAng, int bdyRf)
    {
        // Store name
        this.name = "ScriptMoveRenderToPosition";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, ren, phy, relAng, bdyRf);
    }
    public SetPositionAndRotationToPhysic(Position pos, RenderAnimations ren, Physic2D phy, float relAng, int bdyRf, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, ren, phy, relAng, bdyRf);
    }
    
    
    
    //================================================
    // INTERFACE METHODS
    //================================================
    @Override
    public long getID()
    {
        return this.id;
    }
    @Override
    public String getName()
    {
        return this.name;
    }
    @Override
    public void update(int delta)
    {
        if(this.posCmp != null && this.phyCmp != null)
        {
            // Set position
            this.posCmp.setXPosition( (float)this.phyCmp.getXPosition(this.bodyRef) );
            this.posCmp.setYPosition( (float)this.phyCmp.getYPosition(this.bodyRef) );
        }
        if(this.render != null && this.phyCmp != null)
        {
            // Set rotation of animation
            this.render.setAngle( (this.phyCmp.getDirectionAngle()+this.relativeAngle)%360.0f );
        }
    }
    
    
    //================================================
    // SETTERS
    //================================================
    
    
    //================================================
    // GETTERS
    //================================================
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
