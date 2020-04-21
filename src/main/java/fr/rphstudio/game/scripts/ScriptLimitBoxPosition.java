/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.common.RectangleBox;
import fr.rphstudio.ecs.component.control.PadHandler;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.launcher.Common;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ScriptLimitBoxPosition implements IComponent, IScript
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
    private       Position      position;
    private       RectangleBox  limit;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public ScriptLimitBoxPosition(Position pos, RectangleBox box)
    {
        // Store name
        this.name = "ScriptRotateShape";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.position = pos;
        this.limit    = box;
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
        if( (this.position != null) && (this.limit != null) )
        {
            // Check left limit
            if(this.position.getXPosition() < this.limit.getLeftLimit())
            {
                this.position.setXPosition(this.limit.getLeftLimit());
            }
            // Check top limit
            if(this.position.getYPosition() < this.limit.getTopLimit())
            {
                this.position.setYPosition(this.limit.getTopLimit());
            }
            // Check right limit
            if(this.position.getXPosition() > this.limit.getRightLimit())
            {
                this.position.setXPosition(this.limit.getRightLimit());
            }
            // Check bottom limit
            if(this.position.getYPosition() > this.limit.getBottomLimit())
            {
                this.position.setYPosition(this.limit.getBottomLimit());
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
