package fr.rphstudio.game.factories;

import fr.rphstudio.ecs.component.common.Life;
import fr.rphstudio.ecs.component.common.Score;
import fr.rphstudio.ecs.component.physic.Physic2D;
import fr.rphstudio.ecs.component.render.RenderText;
import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.game.scripts.ScriptDestroyVirus;
import fr.rphstudio.game.scripts.ScriptEndGame;
import fr.rphstudio.launcher.Common;
import fr.rphstudio.launcher.MainLauncher;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


public class FactoryEnvironement
{
    public static Entity create(ECSWorld world, int id, List<Entity> virus) throws SlickException
    {
        //===========================================================
        // ENTITY
        //===========================================================

        //----------------------
        // Create Entity
        Entity entity = new Entity(world, "GAME_ENV");

        //===========================================================
        // COMPONENTS
        //===========================================================

        int borderHeightPixel = MainLauncher.HEIGHT * 3;
        int borderWidthPixel = 400;
        float borderWidthWU = borderWidthPixel/(float) Common.NB_PIXELS_PER_METER;
        float borderHeightWU = borderHeightPixel/(float) Common.NB_PIXELS_PER_METER;
        float screenWidthWU = MainLauncher.WIDTH /(float) Common.NB_PIXELS_PER_METER;
        float screenHeightWU = MainLauncher.HEIGHT /(float) Common.NB_PIXELS_PER_METER;
        // Add left wall component
        Physic2D leftWall2D = new Physic2D("LEFT_WALL");
        leftWall2D.addSquareBody("MAIN", new Vector2f(-borderWidthWU/2,-screenHeightWU/2),borderWidthWU,borderHeightWU,true,1000.0f,1.0f);
        entity.addComponent(leftWall2D);
        // Add right wall component
        Physic2D rightWall2D = new Physic2D("RIGHT_WALL");
        rightWall2D.addSquareBody("MAIN", new Vector2f(screenWidthWU+(borderWidthWU/2),-screenHeightWU/2),borderWidthWU,borderHeightWU,true,1000.0f,1.0f);
        entity.addComponent(rightWall2D);
        
        // Add component Score
        Score score = new Score("GAME_SCORE");
        score.setValue(0);
        entity.addComponent(score);
        
        // Add Life component
        Life life = new Life("GAME_LIFE");
        life.setMaxLife(Common.LIFE_LEVEL);
        life.setLife(Common.LIFE_LEVEL);
        entity.addComponent(life);
        
        // Add physic horizontal bottom bar
        Physic2D bottomWall = new Physic2D("BOTTOM_WALL");
        bottomWall.addSquareBody( "MAIN",
                                  new Vector2f((0)/Common.NB_PIXELS_PER_METER, 1280/Common.NB_PIXELS_PER_METER),
                                  1920*2/Common.NB_PIXELS_PER_METER,
                                  400/Common.NB_PIXELS_PER_METER,
                                  true, 1000.0f, 1.0f);
        entity.addComponent(bottomWall);

        // Add physic horizontal TOP bar
        Physic2D topWall = new Physic2D("TOP_WALL");
        topWall.addSquareBody( "MAIN",
                                  new Vector2f((0)/Common.NB_PIXELS_PER_METER, -200/Common.NB_PIXELS_PER_METER),
                                  1920*2/Common.NB_PIXELS_PER_METER,
                                  400/Common.NB_PIXELS_PER_METER,
                                  true, 1000.0f, 1.0f);
        entity.addComponent(topWall);
        
        // Add component RenderText for Score
        RenderText renderScore = new RenderText("RENDER_SCORE",500001);
        renderScore.setColor(Color.black);
        renderScore.setPosition(new Vector2f(760,10));
        entity.addComponent(renderScore);
        
        //===========================================================
        // SCRIPTS
        //===========================================================

        // Add script to destroy entities
        ScriptDestroyVirus scrDestroy = new ScriptDestroyVirus(score, bottomWall, virus, renderScore);
        entity.addComponent(scrDestroy);

        // Add script for end game
        ScriptEndGame scrEnd = new ScriptEndGame(life,topWall);
        entity.addComponent(scrEnd);

        
        //----------------------
        // Return entity
        //----------------------
        return entity;
    }
}
