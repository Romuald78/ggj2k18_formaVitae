/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game;

import org.newdawn.slick.Color;

/**
 *
 * @author GRIGNON FAMILY
 */
public class PlayerInfo
{
    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private boolean isChoiceValid;
    private int     playerID;
    private int     controllerID;
    private String  controllerName;
    private Color   color;
    
    
    //------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------
    public PlayerInfo(String ctrlName, int ctrl, Color clr)
    {
        // Store info
        this.controllerName = ctrlName;
        this.controllerID   = ctrl;
        this.color          = clr;
        // Init other fields
        this.playerID       = -1;
        this.isChoiceValid  = false;
    }
    
    
    //------------------------------------------------
    // PUBLIC METHODS
    //------------------------------------------------
    public boolean isChoiceValid()
    {
        return this.isChoiceValid;
    }
    public void validateChoice()
    {
        this.isChoiceValid = true;
    }
    public void invalidateChoice()
    {
        this.isChoiceValid = false;
    }
    public void setPlayerID(int plyr)
    {
        this.playerID = plyr;
    }
    public int getPlayerID()
    {
        return this.playerID;
    }
    public int getControllerID()
    {
        // Return controller ID value
        return this.controllerID;
    }
    public String getControllerName()
    {
        return this.controllerName;
    }
    public Color getColor()
    {
        // Create local color
        Color c = new Color(255,255,255,255);
        // copy color value
        c.r = this.color.r;
        c.g = this.color.g;
        c.b = this.color.b;
        c.a = this.color.a;
        // return copy
        return c;
    }
    
    
    //------------------------------------------------
    // END OF CLASS
    //------------------------------------------------        
}
