/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.control.AnalogPadHandler;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ScriptMoveAnalogPosition implements IComponent, IScript
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long       id;
    private final String     name;
    //----------------------------
    // Process properties
    //----------------------------
    private       AnalogPadHandler  analog;
    private       Position          position;
    private       Vector2f          speedVector;
    private       float             speedInc;
    private       float             dampening;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public ScriptMoveAnalogPosition(AnalogPadHandler analogHdl, Position pos, float speedFactor, float damp)
    {
        // Store name
        this.name = "ScriptRotateShape";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.position    = pos;
        this.analog      = analogHdl;
        this.speedVector = new Vector2f(0,0);
        this.speedInc    = speedFactor;
        this.dampening   = damp;
    }
    
    
    //================================================
    // PRIVATE METHODS
    //================================================

    
    
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
        if( (this.analog != null) && (this.position != null) )
        {
            if(delta > 0)
            {
                // Get current input vector
                float dx = this.analog.getDirectionX() * this.speedInc * delta;
                float dy = this.analog.getDirectionY() * this.speedInc * delta;
                // Update current speed vector with input
                this.speedVector.x += dx;
                this.speedVector.y += dy;
                // Apply dampening
                this.speedVector.x *= this.dampening;
                this.speedVector.y *= this.dampening;
                // Update position according to speed
                this.position.increaseXPosition(this.speedVector.x);
                this.position.increaseYPosition(this.speedVector.y);
            }
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
