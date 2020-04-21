/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.launcher;

import fr.rphstudio.ecs.core.utils.ControllerButtons;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

class State04Pause extends BasicGameState
{
    //------------------------------------------------
    // PUBLIC CONSTANTS
    //------------------------------------------------
    public static final int ID = 400;

    
    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private StateBasedGame gameObject;
    private GameContainer  container;
    
    private Image backgnd;
        
    //------------------------------------------------
    // PRIVATE METHODS
    //------------------------------------------------
    private void resumeGame()
    {
        this.gameObject.enterState( State03Game.ID, new FadeOutTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN), new FadeInTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT) );
    }
    private void exitGame()
    {
        GameState gs;
        // Remove in game controller
        gs = this.gameObject.getState(State03Game.ID);
        try
        {
            gs.leave(this.container, this.gameObject);
        }
        catch(Exception e)
        {
            throw new Error(e);
        };
        // Reset the main menu state
        gs = this.gameObject.getState(State02Menu.ID);
        try
        {
            gs.init(this.container, this.gameObject);
        }
        catch(Exception e)
        {
            throw new Error(e);
        };
        // enter main menu
        this.gameObject.enterState( State02Menu.ID, new FadeOutTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN), new FadeInTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT) );
    }
    
    
    //------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------
    public State04Pause()
    {
        // Get current game information (such as player info, score, ...) by passing them through the constructor.
        // [TODO] Modify constructor to do that
    }
    
    
    //------------------------------------------------
    // INIT METHOD
    //------------------------------------------------
    @Override
    public void init(GameContainer container, StateBasedGame sbGame) throws SlickException
    {
        // Init fields
        this.container  = container;
        this.gameObject = sbGame;
        
        // Init pause information (about current game in progress)
        this.backgnd = new Image("./sprites/backgrounds/start.jpg");
    }
    
    
    //------------------------------------------------
    // RENDER METHOD
    //------------------------------------------------
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Fit Screen
        MainLauncher.fitScreen(container, g);
    
        // Render Pause Screen
        g.drawImage(this.backgnd, 0, 0);
        
        g.setColor(new Color(0,0,0,128));
        g.fillRect(0, 0, MainLauncher.WIDTH, MainLauncher.HEIGHT);
        
        g.setColor(Color.orange);
        g.drawString("PAUSE PAGE", Common.SCREEN_WIDTH/2, Common.SCREEN_HEIGHT/2);
    }

    
    //------------------------------------------------
    // UPDATE METHOD
    //------------------------------------------------
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {
        
    }
    
    
    //------------------------------------------------
    // KEYBOARD METHODS
    //------------------------------------------------
    @Override
    public void keyReleased(int key, char c)
    {
        switch(key)
        {
            // Go back to Menu screen if we press ESC (quit current game)
            case Input.KEY_ESCAPE:
                this.exitGame();
                break;
            // Resume current game for any other key
            default:
                this.resumeGame();
                break;
        }
    }
    
        
    //------------------------------------------------
    // CONTROLLER METHODS
    //------------------------------------------------
    @Override
    public void controllerButtonReleased(int controller, int button)
    {   
        // Get real button name
        button = ControllerButtons.getButtonNameFromButtonID(controller, button);
        // Exit game if BACK button is pressed, else resume current game 
        if(button == ControllerButtons.BUTTON_BACK)
        {
            this.exitGame();
        }
        else
        {
            this.resumeGame();
        }
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