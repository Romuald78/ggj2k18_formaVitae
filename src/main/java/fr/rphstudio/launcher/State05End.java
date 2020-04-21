/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.launcher;

import fr.rphstudio.ecs.component.common.Score;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

class State05End extends BasicGameState
{
    //------------------------------------------------
    // PUBLIC CONSTANTS
    //------------------------------------------------
    public static final int ID = 500;

    
    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private StateBasedGame gameObject;
    private GameContainer  container;
    
    private int scoring;
    private Image backgnd;
    
    private TrueTypeFont ttf;
    
    
    //------------------------------------------------
    // PRIVATE METHODS
    //------------------------------------------------
    private void goToNextStep()
    {
        // Back to main menu
        GameState gs;
        // Remove in game controller
        gs = this.gameObject.getState(State01Start.ID);
        try{gs.leave(this.container, this.gameObject);}catch(Exception e){throw new Error(e);};
        // Reset the main menu state
        gs = this.gameObject.getState(State01Start.ID);
        try{gs.init(this.container, this.gameObject);}catch(Exception e){throw new Error(e);};
        // enter main menu
        this.gameObject.enterState( State01Start.ID, new FadeOutTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT), new FadeInTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN) );
    }
    
    //------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------
    public State05End(Score compScore)
    {
        // Get information about final game status (such as player scores, winner, ...)
        this.scoring = (int)(compScore.getValue());
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
        
        this.backgnd = new Image("./sprites/backgrounds/fin.jpg");
        
        // Fonts
        Font font = new Font("./sprites/menus/ARACNE.OTF", Font.BOLD, 32);
        this.ttf  = new TrueTypeFont(font, true);

    }
    
    
    //------------------------------------------------
    // RENDER METHOD
    //------------------------------------------------
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Fit Screen
        MainLauncher.fitScreen(container, g);
    
        // Render End Screen
        g.drawImage(this.backgnd, 0, 0);
        
        this.ttf.drawString( (Common.SCREEN_WIDTH*2)/3.0f, (Common.SCREEN_HEIGHT*4)/5.0f-100, "Game Over. Your score is "+Integer.toString(this.scoring), Common.TEXT_COLOR);
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
        // go to next step, whatever the key is pressed
        this.goToNextStep();
    }
    
        
    //------------------------------------------------
    // CONTROLLER METHODS
    //------------------------------------------------
    @Override
    public void controllerButtonReleased(int controller, int button)
    {   
        // go to next step, whatever the controller button is pressed
        this.goToNextStep();
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