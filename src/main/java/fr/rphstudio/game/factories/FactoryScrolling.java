/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.factories;

import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.game.components.Scrolling;
import fr.rphstudio.game.scripts.ScriptScrolling;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author kille
 */
public class FactoryScrolling {
    static public Entity createScrolling(ECSWorld world) throws SlickException{
        //-----------------------------
        // Create ENTITY Scrolling
        //-----------------------------
        Entity scrollEnt = new Entity (world);
        
        //-----------------------------
        // Add render1 component
        RenderAnimations anim01 = new RenderAnimations("RENDER1", 100);
        // Create animation
        Animation anm;
        SpriteSheet ss;   
        ss  = new SpriteSheet("./sprites/backgrounds/background.jpg", 1920, 3659);
        anm = new Animation();
        anm.addFrame(ss.getSprite(0,0), 100);
        anim01.addAnimation(anm, "SCROLL1", 0, 0);
        // Add component to entity
        scrollEnt.addComponent(anim01);
        
        //-----------------------------
        // Add render1 component
        RenderAnimations anim02 = new RenderAnimations("RENDER2", 100);
        // Create animation
        ss  = new SpriteSheet("./sprites/backgrounds/background.jpg", 1920, 3659);
        anm = new Animation();
        anm.addFrame(ss.getSprite(0,0), 100);
        anim02.addAnimation(anm, "SCROLL2", 0, 0);
        // Add component to entity
        scrollEnt.addComponent(anim02);        
        
        //-----------------------------
        // Add Scrolling offset component
        Scrolling scroll = new Scrolling();
        // Add component to entity
        scrollEnt.addComponent(scroll); 
        
        //-----------------------------
        // Add Script to process scrolling component
        ScriptScrolling scrScroll = new ScriptScrolling(anim01, anim02, scroll);
        // Add component to entity
        scrollEnt.addComponent(scrScroll);
                
        
        //----------------------
        // Return entity
        //----------------------
        return scrollEnt;        
    }
    
}
