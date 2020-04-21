package fr.rphstudio.game.factories;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.physic.Physic2D;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.component.sfx.SoundFX;
import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.game.components.CurrentShape;
import fr.rphstudio.game.components.EnemyState;
import fr.rphstudio.game.script.SetRenderToPosition;
import fr.rphstudio.game.scripts.*;
import fr.rphstudio.launcher.Common;
import fr.rphstudio.launcher.MainLauncher;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class FactoryEnemy
{
    public static Entity create(ECSWorld world, int id, List<RenderAnimations> playerAnimList, int nbShapes, int nbPlayers) throws SlickException
    {
        //===========================================================
        // ENTITY
        //===========================================================

        //----------------------
        // Create Entity
        Entity enemy = new Entity(world, "ENEMY");

        //===========================================================
        // MULTI SHAPE COMMON COMPONENTS
        //===========================================================
        
        //----------------------
        // Create Physic "multi shapes" component
        Physic2D phyEnemy = new Physic2D("phyenemy");
        
        //----------------------
        // Create Sound
        SoundFX sfx = new SoundFX("SOUND_CLEAN", "./sfx/destruct.wav");
        enemy.addComponent(sfx);
        
        //===========================================================
        // LOOP FOR MULTI SHAPE COMPONENTS AND SCRIPTS
        //===========================================================
        float startPosX = 0;
        float startPosY = 0;
        float firstPosX = 0;
        float firstPosY = 0;
        float midPosX = 0;
        float midPosY = 0;
        for(int multiShapeLoop=0;multiShapeLoop<nbShapes;multiShapeLoop++)
        {
            // Prepare random values for creation
            if(multiShapeLoop > 0)
            {
                // Other shape positions
                float rad = Common.MULTI_SHAPE_RADIUS + (float)(Math.random()*Common.MULTI_SHAPE_DELTA_RADIUS);
                float ang = (float)(multiShapeLoop*2*Math.PI/nbShapes);
                startPosX = (float)(startPosX + rad*Math.cos(ang));
                startPosY = (float)(startPosY + rad*Math.sin(ang));
                midPosX   = (startPosX + firstPosX)/2.0f; 
                midPosY   = (startPosY + firstPosY)/2.0f; 
            }
            else
            {
                // First shape positions
                firstPosX =  (float)((MainLauncher.WIDTH-300)*Math.random())+150f;
                //firstPosY = -(float)(MainLauncher.HEIGHT*Math.random()/4);
                firstPosY = (float)((MainLauncher.HEIGHT-300)*Math.random())+150f;
                startPosX = firstPosX;
                startPosY = firstPosY;
            }
            float startShapeAngle = (float)(Math.random()*360);
            int   startShape      = (int)(Math.random()*Common.SHAPE_INDEX_NB+4);
            float startMassRatio  = (float)(Math.random()*0.4) + 0.8f;              // + or - 20 %
            float startDampening  = (float)(Math.random()*0.2) + 0.2f;
            float startScale      = 1.0f; //(float)(Math.random()*(Common.SCALE_MAX-Common.SCALE_MIN))+Common.SCALE_MIN;

            //===========================================================
            // MULTI SHAPE SPECIFIC COMPONENTS
            //===========================================================

            //----------------------
            // Create Position component
            Position pos01 = new Position("POSITION");
            Vector2f initPos01 = new Vector2f(startPosX/Common.NB_PIXELS_PER_METER, startPosY/Common.NB_PIXELS_PER_METER);
            // Set initial position
            pos01.setPosition(initPos01);
            // Add component to entity
            enemy.addComponent(pos01);

            //----------------------
            RenderAnimations anim01 = new RenderAnimations("RENDER", 1000+id);
            // Create animation
            Animation anm;
            Image img;
            // Add animation for SHAPE BLACK CIRCLE
            anm = new Animation();
            for(int loop=0;loop<=74;loop+=2)
            {
                img = new Image( "./sprites/characters/blackCircle/V04_00" + String.format("%0" + 2 + "d", loop) + ".png" );
                anm.addFrame(img.getScaledCopy(0.25f), 80);
            }
            anim01.addAnimation(anm, "SHAPE_BLACK_CIRCLE", 64, 64);
            // Add animation for SHAPE BLACK SQUARE
            anm = new Animation();
            for(int loop=0;loop<=74;loop+=2)
            {
                img = new Image( "./sprites/characters/blackSquare/V03_00" + String.format("%0" + 2 + "d", loop) + ".png" );
                anm.addFrame(img.getScaledCopy(0.25f), 80);
            }
            anim01.addAnimation(anm, "SHAPE_BLACK_SQUARE", 64, 64);
            // Add animation for SHAPE BLACK TRIANGLE
            anm = new Animation();
            for(int loop=0;loop<=74;loop+=2)
            {
                img = new Image( "./sprites/characters/blackTriangle/V01_00" + String.format("%0" + 2 + "d", loop) + ".png" );
                anm.addFrame(img.getScaledCopy(0.25f), 80);
            }
            anim01.addAnimation(anm, "SHAPE_BLACK_TRIANGLE", 64, 64);
            // Add animation for SHAPE BLACK CROSS
            anm = new Animation();
            for(int loop=0;loop<=74;loop+=2)
            {
                img = new Image( "./sprites/characters/blackCross/V02_00" + String.format("%0" + 2 + "d", loop) + ".png" );
                anm.addFrame(img.getScaledCopy(0.25f), 80);
            }
            anim01.addAnimation(anm, "SHAPE_BLACK_CROSS", 64, 64);
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
            // Set init position
            anim01.setPosition(initPos01.x*Common.NB_PIXELS_PER_METER, initPos01.y*Common.NB_PIXELS_PER_METER);
            // Set Scale random
            anim01.setScale( startScale );
            // Set rotation random
            anim01.setAngle( startShapeAngle );
            // Add component to entity
            enemy.addComponent(anim01);

            //----------------------
            // Create Current Shape Index component
            CurrentShape shape01 = new CurrentShape(startShape,Common.SHAPE_INDEX_NB*2,"SHAPE");
            // Add component to entity
            enemy.addComponent(shape01);

            //----------------------
            // Update Enemy physic component
            float radPix = anim01.getScale()*anim01.getCurrentImage().getWidth()/2.f * Common.SPRITE_PHYSIC_DISPLAY_RATIO;//pixel
            // If the shape is square, create square, else create circle
            if(startShape == Common.SHAPE_INDEX_SQUARE+4)
            {
                phyEnemy.addSquareBody("MAIN", initPos01, 2*radPix/(float)Common.NB_PIXELS_PER_METER, 2*radPix/(float)Common.NB_PIXELS_PER_METER, false, startMassRatio, startDampening);
            }
            else
            {
                phyEnemy.addCircleBody("MAIN", initPos01, radPix/(float)Common.NB_PIXELS_PER_METER, false, startMassRatio, startDampening);
            }
            if(multiShapeLoop == 0)
            {
                phyEnemy.setAngle(startShapeAngle);
                enemy.addComponent(phyEnemy);
            }
            
            //----------------------
            // Create Joint between first and current physc bodies
            if(multiShapeLoop > 0)
            {
                Vector2f jointPos01 = new Vector2f(midPosX/Common.NB_PIXELS_PER_METER, midPosY/Common.NB_PIXELS_PER_METER);
                for(int ii=0;ii<multiShapeLoop;ii++)
                {
                    phyEnemy.addJoint("JOINT", phyEnemy.getBody(ii), phyEnemy.getBody(multiShapeLoop), jointPos01);
                }
            }
            //----------------------
            // Create State
            EnemyState enemyState = new EnemyState();
            enemy.addComponent(enemyState);
            
            //===========================================================
            // MULTI SHAPE COMMON SCRIPTS
            //===========================================================

            //----------------------
            // Script RENDER to POSITION
            SetRenderToPosition scrRenToPos01 = new SetRenderToPosition(pos01,anim01,0,0);
            // Add component to entity
            enemy.addComponent(scrRenToPos01);

            //----------------------
            // Script select the correct render anim from current shape
            ScriptShapeToRender scrShapeToRender01 = new ScriptShapeToRender(anim01,shape01);
            // Add component to entity
            enemy.addComponent(scrShapeToRender01);

            //----------------------
            // Script for status and cure
            enemy.addComponent(new ScriptEnemyStatus(playerAnimList,anim01,shape01,enemyState,sfx));

            //----------------------
            // Script to set position component to physic position
            enemy.addComponent(new SetPositionAndRotationToPhysic(pos01,anim01,phyEnemy,startShapeAngle,multiShapeLoop));

            //===========================================================
            // MULTI SHAPE SPECIFIC SCRIPTS
            //===========================================================

            //----------------------
            // Create script to limit position of players (only applied to the first shape of this entity, other shapes are "joined" to the first one)
            if(multiShapeLoop == 0)
            {
                enemy.addComponent(new ScriptEnemyMovement(phyEnemy,shape01,anim01,nbPlayers));
            }
            
            //===========================================================
            // END OF LOOP
            //===========================================================
        }

        //----------------------
        // Return entity
        //----------------------
        return enemy;
    }
}
