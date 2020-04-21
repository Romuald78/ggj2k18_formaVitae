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
import fr.rphstudio.game.components.CurrentShape;
import fr.rphstudio.launcher.Common;

import java.util.List;

/**
 * @author GRIGNON FAMILY
 */
public class ScriptShapeToRender implements IComponent, IScript {
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long id;
    private final String name;
    //----------------------------
    // Process properties
    //----------------------------
    private RenderAnimations render;
    private CurrentShape currentShape;


    //================================================
    // CONSTRUCTOR
    //================================================
    public ScriptShapeToRender(RenderAnimations ren, CurrentShape shap)
    {
        // Store name
        this.name = "ScriptShapeToRender";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.render       = ren;
        this.currentShape = shap;
    }


    //================================================
    // PRIVATE METHODS
    //================================================


    //================================================
    // INTERFACE METHODS
    //================================================
    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void update(int delta) {
        if ((this.currentShape != null) && (this.render != null))
        {
            render.selectAnimation( this.currentShape.getCurrentShapeIndex() );
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
