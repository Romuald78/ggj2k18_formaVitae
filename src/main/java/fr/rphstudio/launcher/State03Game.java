/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.launcher;

import fr.rphstudio.ecs.component.common.Life;
import fr.rphstudio.ecs.component.common.Score;
import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.utils.ControllerButtons;
import fr.rphstudio.game.PlayerInfo;
import fr.rphstudio.game.factories.*;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


class State03Game extends BasicGameState
{
    //------------------------------------------------
    // PUBLIC CONSTANTS
    //------------------------------------------------
    public static final int ID = 300;

    
    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private StateBasedGame gameObject;
    private GameContainer  container;
    
    private List<PlayerInfo> playerList;
    
    private ECSWorld       world;

    private Life           compLife;
    private Score          compScore;
    

    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private void goToPauseScreen()
    {
        // go to Pause state
        this.gameObject.enterState( State04Pause.ID, new FadeOutTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN), new FadeInTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT) );
    }
    private void goToEndScreen()
    {
        // Init and go to InGame state
        State05End sig = new State05End(this.compScore);
        this.gameObject.addState( sig );
        try
        {
            sig.init(this.container, this.gameObject);
        }
        catch(Exception e)
        {
            throw new Error(e);
        };
        this.gameObject.enterState( State05End.ID, new FadeOutTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN), new FadeInTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT) );
    }
    
    //------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------
    public State03Game(List<PlayerInfo> pList)
    {
        // Store player info list from constructor
        this.playerList = pList;
    }
    
    
    //------------------------------------------------
    // INIT METHOD
    //------------------------------------------------
    public void init(GameContainer container, StateBasedGame sbGame) throws SlickException
    {
        // Init fields
        this.container  = container;
        this.gameObject = sbGame;

        container.setClearEachFrame(true);
        
        // Instanciate World
        this.world = new ECSWorld("World1");
        
        // Init InGame information (in terms of game data)
        // [TODO]
        
        // Create pause screen (with related game data)
        // [TODO]
        State04Pause sig = new State04Pause();
        this.gameObject.addState( sig );
        try
        {
            sig.init(this.container, this.gameObject);
        }
        catch(Exception e)
        {
            throw new Error(e);
        };
        
        //=================================================================
        // CREATE PLAYERS
        //=================================================================
        List<Entity> players = new ArrayList<Entity>();
        for(PlayerInfo info : this.playerList)
        {
            // Create players entity
            Entity playerEnt = FactoryPlayer.createPlayer(world, info.getPlayerID(), info.getControllerID());
            // And add it to the world
            players.add(playerEnt);
            this.world.addEntity(playerEnt);
        }
        
        //=================================================================
        // CREATE SCROLLING
        //=================================================================
        this.world.addEntity(FactoryScrolling.createScrolling(world));

        //=================================================================
        // CREATE ENEMIES
        //=================================================================     
        List<Entity> virus = new ArrayList<Entity>();
        // Create all enemy entities via the manager
        this.world.addEntity(FactoryEnemyManager.create(world, 1,players,virus));

        Entity factEnt = FactoryEnvironement.create(world,1,virus);
        world.addEntity(factEnt);
        
        // Store Life component
        List<IComponent> compList = factEnt.getComponent("GAME_LIFE");
        if(compList.size() == 1)
        {
            this.compLife = (Life)(compList.get(0));
        }
        compList = factEnt.getComponent("GAME_SCORE");
        if(compList.size() == 1)
        {
            this.compScore = (Score)(compList.get(0));
        }

    }
    
    
    //------------------------------------------------
    // RENDER METHOD
    //------------------------------------------------
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Fit Screen
        MainLauncher.fitScreen(container, g);
        
        // Call world render method
        this.world.render(container, game, g);
        
        //matchingAlgorithm.printDebug(g);
    }

    
    //------------------------------------------------
    // UPDATE METHOD
    //------------------------------------------------
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {
        container.getGraphics().clear();
        // Call all script components and execute them
        this.world.update(container, game, delta);
        
        // Check for end of game
        if(this.compLife.getLife() <= 0)
        {
            // Game is over
            this.goToEndScreen();
        }
    }
    
    
    //------------------------------------------------
    // KEYBOARD METHODS
    //------------------------------------------------
    @Override
    public void keyPressed(int key, char c)
    {   
        // Call World Key Method
        this.world.keyPressed(key);
        
        // Check for application exit
        switch(key)
        {
            case Input.KEY_ESCAPE:
                // Go to pause screen
                this.goToPauseScreen();
                break;
            default:
                break;
        }
    }    
    @Override
    public void keyReleased(int key, char c)
    {
        // Call World Key Method
        this.world.keyReleased(key);
    }
    
        
    //------------------------------------------------
    // CONTROLLER METHODS
    //------------------------------------------------
    @Override
    public void controllerButtonPressed(int controller, int button)
    {
        // Translate raw button index to named button index
        button = ControllerButtons.getButtonNameFromButtonID(controller, button);
        // Call World PAD Button method
        this.world.controllerButtonPressed(controller, button);
        // Go to pause menu if requested
        if(button == ControllerButtons.BUTTON_BACK)
        {
            this.goToPauseScreen();
        }
    }
    @Override
    public void controllerButtonReleased(int controller, int button)
    {   
        // Translate raw button index to named button index
        button = ControllerButtons.getButtonNameFromButtonID(controller, button);
        // Call World PAD Button method
        this.world.controllerButtonReleased(controller, button);
    }
    @Override
    public void controllerLeftPressed(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionPressed(controller, ECSWorld.PAD_DIR_LEFT);
    }
    @Override
    public void controllerLeftReleased(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionReleased(controller, ECSWorld.PAD_DIR_LEFT);
    }    
    @Override
    public void controllerRightPressed(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionPressed(controller, ECSWorld.PAD_DIR_RIGHT);
    }
    @Override
    public void controllerRightReleased(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionReleased(controller, ECSWorld.PAD_DIR_RIGHT);
    }    
    @Override
    public void controllerUpPressed(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionPressed(controller, ECSWorld.PAD_DIR_UP);
    }
    @Override
    public void controllerUpReleased(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionReleased(controller, ECSWorld.PAD_DIR_UP);
    }    
    @Override
    public void controllerDownPressed(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionPressed(controller, ECSWorld.PAD_DIR_DOWN);
    }
    @Override
    public void controllerDownReleased(int controller)
    {
        // Call World PAD Direction method
        this.world.controllerDirectionReleased(controller, ECSWorld.PAD_DIR_DOWN);
    }
    
    
    //------------------------------------------------
    // STATE ID METHOD
    //------------------------------------------------
    @Override
    public int getID()
    {
          return this.ID;
    }
    
    
    //------------------------------------------------
    // END OF STATE
    //------------------------------------------------
}