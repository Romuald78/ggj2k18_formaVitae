/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.render.RenderParticles;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.launcher.Common;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class SetParticleToPosition implements IComponent, IScript
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long   id;
    private final String name;
    private Position     posCmp = null;
    private RenderParticles    parCmp = null;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Position pos, RenderParticles par)
    {
        // Store components
        this.posCmp = pos;
        this.parCmp = par;
    }
    public SetParticleToPosition(Position pos, RenderParticles par)
    {
        // Store name
        this.name = "ScriptMoveRenderToPosition";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, par);
    }
    public SetParticleToPosition(Position pos, RenderParticles par, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, par);
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
        if(this.posCmp != null && this.parCmp != null)
        {
            this.parCmp.setPosition(0, new Vector2f(this.posCmp.getXPosition()*Common.NB_PIXELS_PER_METER,this.posCmp.getYPosition()*Common.NB_PIXELS_PER_METER) );
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
