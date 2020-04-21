/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.scripts;

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
public class ScriptScaleShape implements IComponent, IScript
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
    private       RenderAnimations  render;
    private       PadHandler        padHdl;
    private       boolean           isZoomIn;
    private       boolean           isZoomOut;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public ScriptScaleShape(RenderAnimations ren, PadHandler pad)
    {
        // Store name
        this.name = "ScriptRotateShape";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.render    = ren;
        this.padHdl    = pad;
        this.isZoomIn  = false;
        this.isZoomOut = false;
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
            // If we have pushed on the zoom button
            if(this.padHdl.isButtonRisingEdge("ZOOM_IN"))
            {
                this.isZoomIn = true;
            }
            if(this.padHdl.isButtonFallingEdge("ZOOM_IN"))
            {
                this.isZoomIn = false;
            }
            // If we have pushed on the de-zoom button
            if(this.padHdl.isButtonRisingEdge("ZOOM_OUT"))
            {
                this.isZoomOut = true;
            }
            if(this.padHdl.isButtonFallingEdge("ZOOM_OUT"))
            {
                this.isZoomOut = false;
            }
            // Check if we have to modify the coef
            if(this.isZoomIn && !this.isZoomOut)
            {
                float scale = this.render.getScale();
                scale *= (float)Math.pow(1.0f+Common.SCALE_COEF, delta);
                scale = Math.min(scale, Common.SCALE_MAX);
                scale = Math.max(scale, Common.SCALE_MIN);
                this.render.setScale(scale);
            }
            if(!this.isZoomIn && this.isZoomOut)
            {
                float scale = this.render.getScale();
                scale *= (float)Math.pow(1.0f-Common.SCALE_COEF, delta);
                scale = Math.min(scale, Common.SCALE_MAX);
                scale = Math.max(scale, Common.SCALE_MIN);
                this.render.setScale(scale);
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
