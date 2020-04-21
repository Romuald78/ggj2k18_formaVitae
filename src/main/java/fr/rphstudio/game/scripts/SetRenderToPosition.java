/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.script;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.launcher.Common;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class SetRenderToPosition implements IComponent, IScript
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long       id;
    private final String     name;
    private Position         posCmp = null;
    private RenderAnimations renCmp = null;
    private float            dX     = 0;
    private float            dY     = 0;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Position pos, RenderAnimations ren, float offsetX, float offsetY)
    {
        // Store components
        this.posCmp = pos;
        this.renCmp = ren;
        this.dX     = offsetX;
        this.dY     = offsetY;
    }
    public SetRenderToPosition(Position pos, RenderAnimations ren, float offsetX, float offsetY )
    {
        // Store name
        this.name = "ScriptMoveRenderToPosition";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, ren, offsetX, offsetY);
    }
    public SetRenderToPosition(Position pos, RenderAnimations ren, float offsetX, float offsetY, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, ren, offsetX, offsetY);
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
        if(this.posCmp != null && this.renCmp != null)
        {
            this.renCmp.setPosition( (float)(this.posCmp.getXPosition()*Common.NB_PIXELS_PER_METER)+this.dX,
                                     (float)(this.posCmp.getYPosition()*Common.NB_PIXELS_PER_METER)+this.dY );
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
