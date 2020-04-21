/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.common.Rotation;
import fr.rphstudio.ecs.component.control.PadHandler;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ScriptRotateShape implements IComponent, IScript
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
    private       PadHandler        padHdl;
    private       float             speedInc;
    private       float             minSpeed;
    private       float             maxSpeed;
    private       RenderAnimations  render;
    private       float             currentSpeed;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public ScriptRotateShape(RenderAnimations ren, PadHandler pad, float minSpd, float maxSpd, float spdInc)
    {
        // Store name
        this.name = "ScriptRotateShape";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.render    = ren;
        this.padHdl    = pad;
        this.minSpeed  = minSpd;
        this.maxSpeed  = maxSpd;
        this.speedInc  = spdInc;
        this.resetSpeed();
    }
    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    private void resetSpeed()
    {
        this.currentSpeed = this.minSpeed;
    }
    private void increaseSpeed(int delta)
    {
        this.currentSpeed *= (float)Math.pow(this.speedInc,delta);
        this.currentSpeed = Math.min(this.currentSpeed, this.maxSpeed);
    }
    
    
    //================================================
    // INTERFACE METHODS
    //================================================

    public long getID()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public void update(int delta)
    {
        if( (this.render != null) && (this.padHdl != null) )
        {
            // Store status
            boolean isL1Down = this.padHdl.isButtonActive("ROTATE_LEFT"); 
            boolean isR1Down = this.padHdl.isButtonActive("ROTATE_RIGHT"); 
            // Increase angle coef
            if(delta > 0)
            {
                this.increaseSpeed(delta);
            }
            // If no button is down, or if both are down, reset angle coef
            if( (!isL1Down && !isR1Down) || (isL1Down && isR1Down) )  
            {
                this.resetSpeed();
            }
            if(delta > 0)
            {
                // Check if the LEFT button is pressed
                if(isL1Down)
                {
                    this.render.addAngle(-this.currentSpeed);
                }
                // Check if the RIGHT button is pressed
                if(isR1Down)
                {
                    this.render.addAngle(this.currentSpeed);
                }
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
