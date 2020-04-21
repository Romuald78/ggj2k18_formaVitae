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
public class CurrentShape implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private       int      curShapeIndex;
    private       int      nbShapes;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public CurrentShape(int init, int nb)
    {
        // Store name
        this.name = "CurrentShape";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init shape indexes
        this.curShapeIndex = init;
        this.nbShapes      = nb;
    }    
    public CurrentShape(int init, int nb, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init shape indexes
        this.nbShapes = nb;
        this.selectShape(init);
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
    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    
    
    //================================================
    // SETTERS
    //================================================
    public void selectShape(int index)
    {
        if( (index >=0) && (index < this.nbShapes) )
        {
            this.curShapeIndex = index;
        }
        else
        {
            throw new Error("[ERROR] shape index is not correct !");
        }
    }
    public void selectNextShapeIndex()
    {
        this.curShapeIndex = (this.curShapeIndex+1+this.nbShapes) % this.nbShapes;
    }
    
    
    //================================================
    // GETTERS
    //================================================
    public int getCurrentShapeIndex()
    {
        return this.curShapeIndex;
    }
    
    
    //================================================
    // END OF CLASS
    //================================================
}
