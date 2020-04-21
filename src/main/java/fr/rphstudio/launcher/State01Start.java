/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.launcher;

import fr.rphstudio.ecs.component.sfx.MusicFX;
import fr.rphstudio.ecs.core.utils.ControllerButtons;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Controllers;

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

public class State01Start extends BasicGameState
{   
    //------------------------------------------------
    // PUBLIC CONSTANTS
    //------------------------------------------------
    public static final int ID = 100;

    
    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private StateBasedGame gameObject;
    private GameContainer  container;
    private String         version;
    
    private Image          backGround;
    private Image          startButton;

    private MusicFX mfx;

    
    //------------------------------------------------
    // PRIVATE METHODS
    //------------------------------------------------
    // Get current program version string from file
    private void getVersion()
    {
        // Get display version
        BufferedReader br = null;
        try
        {
            this.version = "";
            br = new BufferedReader(new FileReader("info/version.txt"));
            String line;
            line = br.readLine();
            while(line != null)
            {
                this.version = this.version + line + "\n";
                line = br.readLine();
            }
            if (br != null)
            {
                br.close();
            }
        }
        catch (IOException e)
        {
            throw new Error(e);
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (IOException ex)
            {
                throw new Error(ex);
            }
        }
    }
    // Quit game
    private void quitGame()
    {
        this.container.exit();
    }
    // Go to next menu 
    private void goToMainMenu()
    {
        // Get Menu state
        GameState gs    = this.gameObject.getState(State02Menu.ID);
        // Init Menu state
        try
        {
            gs.init(this.container, this.gameObject);
        }
        catch(Exception e)
        {
            throw new Error(e);
        };
        // enter Menu state
        this.gameObject.enterState( State02Menu.ID, new FadeOutTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN), new FadeInTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT) );
    }   
    // Load plugged controller names
    private void loadControllerNames()
    {
        ArrayList<String> selectedCtrlNames = new ArrayList<String>();
        ArrayList<String> AllCtrlNames = new ArrayList<String>();
        for(int i=0;i<Controllers.getControllerCount();i++)
        {
            String nam = Controllers.getController(i).getName();
            int    cnt = Controllers.getController(i).getButtonCount();
            System.out.println("[INFO] Found a controller named '"+nam+"' with "+cnt+" buttons");
            AllCtrlNames.add(nam);
            if( Controllers.getController(i).getButtonCount() >= ControllerButtons.NB_BUTTONS_PER_CONTROLLER )
            {
                selectedCtrlNames.add(nam);
                System.out.println("[INFO] Selected this controller named '"+nam+"'");
            }
        }
        ControllerButtons.initControllerNames(selectedCtrlNames.toArray(new String[0]));
    }
    
    
    //------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------
    public State01Start()
    {
        
    }
    
    
    //------------------------------------------------
    // INIT METHOD
    //------------------------------------------------
    public void init(GameContainer container, StateBasedGame sbGame) throws SlickException
    {
        // Init fields
        this.container         = container;
        this.gameObject        = sbGame;
        
        // Get version string
        this.getVersion();

        // Load controller names
        this.loadControllerNames();

        // Load background image
        this.backGround  = new Image("sprites/backgrounds/start.jpg");
        this.startButton = new Image("sprites/menus/startButton.png");
        
        // Launch music        
        this.mfx = new MusicFX("MUSIC","./music/music.ogg");
        this.mfx.playMusic();
    
    }

        
    //------------------------------------------------
    // RENDER METHOD
    //------------------------------------------------
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Fit Screen
        MainLauncher.fitScreen(container, g);
        // Render Start screen background
        g.setColor(Color.white);
        g.drawImage(this.backGround, 0, 0);
        
        // Render version number
        g.setColor(Color.black);
        g.drawString(this.version, 15, 15);
    }

    
    //------------------------------------------------
    // UPDATE METHOD
    //------------------------------------------------
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {   

    }
    
    
    //------------------------------------------------
    // KEYBOARD METHODS
    //------------------------------------------------
    @Override
    public void keyPressed(int key, char c)
    {
        switch(key)
        {
            // Quit game by pressing escape
            case Input.KEY_ESCAPE:
                this.quitGame();
                break;
            // Go to main menu by pressing any other key
            default :     
                this.goToMainMenu();
                break;        
        }
    }
    
    
    //------------------------------------------------
    // CONTROLLER METHODS
    //------------------------------------------------
    @Override
    public void controllerButtonPressed(int controller, int button)
    {   
        // System.out.println(controller+" "+button);
        // Go to main menu by pressing any controller button
        int buttonName = ControllerButtons.getButtonNameFromButtonID(controller, button);
        switch(buttonName)
        {
            // Go to player selection menu if START button is pressed
            case ControllerButtons.BUTTON_START:
                this.goToMainMenu();
                break;
            // Exit game if BACK button is pressed
            case ControllerButtons.BUTTON_BACK:
                this.quitGame();
                break;
            // Nothing to do for other buttons
            default:
                break;
            
        }
        /*
        switch(buttonName)
        {
            case ControllerButtons.BUTTON_A     :
                System.out.println("BUTTON A");
                break;
            case ControllerButtons.BUTTON_B     :
                System.out.println("BUTTON B");
                break;
            case ControllerButtons.BUTTON_X     :
                System.out.println("BUTTON X");
                break;
            case ControllerButtons.BUTTON_Y     :
                System.out.println("BUTTON Y");
                break;
            case ControllerButtons.BUTTON_L1    :
                System.out.println("BUTTON LEFT");
                break;
            case ControllerButtons.BUTTON_R1    :
                System.out.println("BUTTON RIGHT");
                break;
            case ControllerButtons.BUTTON_START :
                System.out.println("BUTTON START");
                break;
            case ControllerButtons.BUTTON_BACK  :
                System.out.println("BUTTON BACK");
                break;
            default:
                break;
        }
        //*/
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