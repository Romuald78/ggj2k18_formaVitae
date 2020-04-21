/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.game.factories;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.common.RectangleBox;
import fr.rphstudio.ecs.component.control.AnalogPadHandler;
import fr.rphstudio.ecs.component.control.PadHandler;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.component.render.RenderParticles;
import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.utils.ControllerButtons;
import fr.rphstudio.game.components.CurrentShape;
import fr.rphstudio.game.script.SetRenderToPosition;
import fr.rphstudio.game.scripts.ScriptLimitBoxPosition;
import fr.rphstudio.game.scripts.ScriptMoveAnalogPosition;
import fr.rphstudio.game.scripts.ScriptRotateShape;
import fr.rphstudio.game.scripts.ScriptScaleShape;
import fr.rphstudio.game.scripts.ScriptSelectShape;
import fr.rphstudio.game.scripts.ScriptShapeToRender;
import fr.rphstudio.game.scripts.SetParticleToPosition;
import fr.rphstudio.launcher.Common;
import java.io.IOException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

/**
 *
 * @author GRIGNON FAMILY
 */
public class FactoryPlayer
{
    public static Entity createPlayer(ECSWorld world, int playerNum, int controlNum) throws SlickException
    {
        //===========================================================
        // ENTITY
        //===========================================================
        
        //----------------------
        // Create Entity
        Entity player01 = new Entity(world, "PLAYER_"+Integer.toString(playerNum));

        //===========================================================
        // COMPONENTS
        //===========================================================
        
        //----------------------
        // Create Position component
        Position pos01 = new Position("POSITION");
        // Set initial position
        Vector2f initPos01 = new Vector2f(500/Common.NB_PIXELS_PER_METER,500/Common.NB_PIXELS_PER_METER);
        pos01.setPosition(initPos01);
        // Add component to entity
        player01.addComponent(pos01);

        //----------------------
        // Create Analog handler component
        AnalogPadHandler analog01 = new AnalogPadHandler("ANALOG", controlNum);
        // Add component to entity
        player01.addComponent(analog01);

        //----------------------
        // Create Pad handler component
        PadHandler pad01 = new PadHandler("PAD", controlNum);
        pad01.addButton("ROTATE_LEFT" , ControllerButtons.BUTTON_L1);
        pad01.addButton("ROTATE_RIGHT", ControllerButtons.BUTTON_R1);
        pad01.addButton("CHANGE_SHAPE", ControllerButtons.BUTTON_A );
        pad01.addButton("ZOOM_IN"     , ControllerButtons.BUTTON_Y );
        pad01.addButton("ZOOM_OUT"    , ControllerButtons.BUTTON_B );
        // Add component to entity
        player01.addComponent(pad01);

        //----------------------
        // Create Pad handler component
        RenderAnimations anim01 = new RenderAnimations("RENDER", 10000+playerNum);
        // Create animation
        Animation anm;
        Image img;
        
        // Add animation for SHAPE CIRCLE
        anm = new Animation();
        for(int loop=0;loop<=74;loop+=2)
        {
            img = new Image( "./sprites/characters/circle/AV01_00" + String.format("%0" + 2 + "d", loop) + ".png" );
            anm.addFrame(img.getScaledCopy(0.25f), 80);
        }
        anim01.addAnimation(anm, "SHAPE_CIRCLE", 64, 64);
        
        // Add animation for SHAPE SQUARE
        anm = new Animation();
        for(int loop=0;loop<=74;loop+=2)
        {
            img = new Image( "./sprites/characters/square/AV03_00" + String.format("%0" + 2 + "d", loop) + ".png" );
            anm.addFrame(img.getScaledCopy(0.25f), 80);
        }
        anim01.addAnimation(anm, "SHAPE_SQUARE", 64, 64);
        
        // Add animation for SHAPE TRIANGLE
        anm = new Animation();
        for(int loop=0;loop<=74;loop+=2)
        {
            img = new Image( "./sprites/characters/triangle/AV04_00" + String.format("%0" + 2 + "d", loop) + ".png" );
            anm.addFrame(img.getScaledCopy(0.25f), 80);
        }
        anim01.addAnimation(anm, "SHAPE_TRIANGLE", 64, 64);
        
        // Add animation for SHAPE CROSS
        anm = new Animation();
        for(int loop=0;loop<=74;loop+=2)
        {
            img = new Image( "./sprites/characters/cross/AV02_00" + String.format("%0" + 2 + "d", loop) + ".png" );
            anm.addFrame(img.getScaledCopy(0.25f), 80);
        }
        anim01.addAnimation(anm, "SHAPE_CROSS", 64, 64);
        // Add component to entity
        player01.addComponent(anim01);

        //----------------------
        // Create Current Shape Index component
        CurrentShape shape01 = new CurrentShape(playerNum%Common.SHAPE_INDEX_NB,Common.SHAPE_INDEX_NB,"SHAPE");
        // Add component to entity
        player01.addComponent(shape01);
        
        //----------------------
        // Create Box to limit
        Vector2f topLeft     = new Vector2f( Common.LIMIT_BOX_PIXELS_LEFT /(float)Common.NB_PIXELS_PER_METER, Common.LIMIT_BOX_PIXELS_TOP   /(float)Common.NB_PIXELS_PER_METER );
        Vector2f bottomRight = new Vector2f( Common.LIMIT_BOX_PIXELS_RIGHT/(float)Common.NB_PIXELS_PER_METER, Common.LIMIT_BOX_PIXELS_BOTTOM/(float)Common.NB_PIXELS_PER_METER );
        RectangleBox limitBox01 = new RectangleBox(topLeft, bottomRight);
        // Add component to entity
        player01.addComponent(limitBox01);
        
        //----------------------
        // Create particles
        RenderParticles particles01 = new RenderParticles(9000+playerNum, "./sprites/particles/playerParticle.png", 250, ParticleSystem.BLEND_ADDITIVE, false);
        try
        {
            Vector2f pixelPos = new Vector2f( initPos01.x*Common.NB_PIXELS_PER_METER, initPos01.y*Common.NB_PIXELS_PER_METER );
            particles01.addEmitter( "./sprites/particles/playerEmitter.xml", pixelPos );
            particles01.getEmitter(0).addColorPoint(0.0f, Common.PLAYER_COLORS[playerNum]);
            particles01.getEmitter(0).addColorPoint(1.0f, Common.PLAYER_COLORS[playerNum]);
        }
        catch(IOException e)
        {
            throw new Error(e);
        }
        // Add component to entity
        player01.addComponent(particles01);
        
        
        
        //===========================================================
        // SCRIPTS
        //===========================================================
               
        //----------------------
        // Script MOVE player
        ScriptMoveAnalogPosition scrMove01 = new ScriptMoveAnalogPosition(analog01, pos01, Common.PLAYER_MOVE_SPEED_FACTOR, Common.PLAYER_MOVE_DAMPENING);
        // Add component to entity
        player01.addComponent(scrMove01);

        //----------------------
        // Script RENDER to POSITION
        SetRenderToPosition scrRenToPos01 = new SetRenderToPosition(pos01,anim01,0,0);
        // Add component to entity
        player01.addComponent(scrRenToPos01);

        //----------------------
        // Script ROTATE player
        ScriptRotateShape scrRotateShape01 = new ScriptRotateShape(anim01,pad01, Common.PLAYER_ROTATE_SPEED_MIN,Common.PLAYER_ROTATE_SPEED_MAX,Common.PLAYER_ROTATE_SPEED_INC);
        // Add component to entity
        player01.addComponent(scrRotateShape01);
        
        //----------------------
        // Script Select Shape
        ScriptSelectShape scrShape01 = new ScriptSelectShape(pad01, shape01);
        // Add component to entity
        player01.addComponent(scrShape01);
        
        //----------------------
        // Script select the correct render anim from current shape
        ScriptShapeToRender scrShapeToRender01 = new ScriptShapeToRender(anim01,shape01);
        // Add component to entity
        player01.addComponent(scrShapeToRender01);
        
        //----------------------
        // Script select the correct render anim from current shape
        ScriptScaleShape scrScaleShape01 = new ScriptScaleShape(anim01,pad01);
        // Add component to entity
        player01.addComponent(scrScaleShape01);
        
        //----------------------
        // Create script to limit position of players
        ScriptLimitBoxPosition scrLimit01 = new ScriptLimitBoxPosition(pos01, limitBox01);
        // Add component to entity
        player01.addComponent(scrLimit01);
        
        //----------------------
        // Create script to set the particle system to the position
        SetParticleToPosition scrPartPos = new SetParticleToPosition(pos01,particles01);
        // Add component to entity
        player01.addComponent(scrPartPos);
        
        
        
        //----------------------
        // Return entity
        //----------------------
        return player01;
    }
}
