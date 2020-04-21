/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.game.components.Scrolling;
import fr.rphstudio.launcher.Common;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ScriptScrolling implements IComponent, IScript
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
    private RenderAnimations render1;
    private RenderAnimations render2;
    private Scrolling scroll;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public ScriptScrolling(RenderAnimations ren1, RenderAnimations ren2, Scrolling scrl )
    {
        // Store name
        this.name = "ScriptRotateShape";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // affectation
        this.render1 = ren1;
        this.render2 = ren2;
        this.scroll = scrl;
        
        
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
        if( (this.render1 != null) && (this.render2 != null) && (this.scroll!=null) )
        {
            // Get increment for next step
            int increment = (int)(delta*Common.SCROLLING_SPEED);
            // Get background image height
            int modulo1   = this.render1.getAnimation("SCROLL1").getCurrentFrame().getHeight();
            // Get background offset range size
            int modulo2   = 2*modulo1;
            // Get offset between -modulo1 and +modulo1
            int newOffset = ((this.scroll.getOffset() + modulo1 + increment)%modulo2)-modulo1;
            // Set new value for offset
            this.scroll.setOffset(newOffset);
            // Now we can blit Render 1
            this.render1.setPosition( new Vector2f(0,newOffset) );
            // Now we can blit Render 2
            if( newOffset < 0 )
            {
                this.render2.setPosition( new Vector2f(0,newOffset+modulo1) );
            }
            else
            {
                this.render2.setPosition( new Vector2f(0,newOffset-modulo1) );
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
