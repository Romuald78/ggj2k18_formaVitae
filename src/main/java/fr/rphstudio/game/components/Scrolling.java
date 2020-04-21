/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.components;

import fr.rphstudio.ecs.component.common.*;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class Scrolling implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private int offset;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public Scrolling()
    {
        // Store name
        this.name = "Scrolling";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // init offset
        this.offset = 0;
    }    
    public Scrolling(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
       //init offset
        this.offset = 0;

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
    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    
    
    //================================================
    // SETTERS
    //================================================
    public void setOffset (int newOffset)
    {
      this.offset = newOffset;
    }
    public void increaseOffset(int delta)
    {
        this.offset += delta;
    }
    
    //================================================
    // GETTERS
    //================================================
    public int getOffset(){
       return this.offset; 
    }
    
    //================================================
    // END OF CLASS
    //================================================
}
