/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.launcher;

import fr.rphstudio.ecs.core.utils.ControllerButtons;
import fr.rphstudio.game.PlayerInfo;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

class State02Menu extends BasicGameState
{
    //------------------------------------------------
    // PUBLIC CONSTANTS
    //------------------------------------------------
    public static final int ID = 200;

    
    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private StateBasedGame gameObject;
    private GameContainer  container;
    
    private Image            background;
    private Image            playerImg;
    private Image            padImg;
    private List<Animation>  animPlayers;
    
    private List<PlayerInfo> playerList;
    private Image            buttonA;
            
    
    //------------------------------------------------
    // PRIVATE METHODS
    //------------------------------------------------
    private void goBackToStartMenu()
    {
        this.gameObject.enterState(State01Start.ID, new FadeOutTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN), new FadeInTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT) );
    }
    private void moveSelection(int ctrlID, int offset)
    {
        // first look for the current entry with controller ID
        PlayerInfo player = null;
        for(PlayerInfo p: this.playerList)
        {
            if(p.getControllerID() == ctrlID)
            {
                player = p;
                break;
            }
        }
        // If we have found the good controller
        if(player != null)
        {
            // First check this controller is not already validated (so it is not possible to change the selection)
            if(!player.isChoiceValid())
            {
                // then get current player index
                int oldIndex = player.getPlayerID();
                int newIndex = (oldIndex + offset + Common.NB_MAX_PLAYERS) % Common.NB_MAX_PLAYERS;
                // Now check all positions until we have made a complete turn
                while(newIndex != oldIndex)
                {
                    // If the new index is available, select it and exit
                    if(this.isPlayerFree(newIndex))
                    {
                        // Select new player and exit
                        player.setPlayerID(newIndex);
                        break;
                    }
                    else
                    {
                        newIndex = (newIndex + (int)(Math.signum(offset)) + Common.NB_MAX_PLAYERS) % Common.NB_MAX_PLAYERS;
                    }
                }
            }
        }
    }
    private boolean isControllerRegistered(int ctrl)
    {
        // init local result
        boolean res = false;
        // Look for controller id
        for(PlayerInfo p: this.playerList)
        {
            if(p.getControllerID() == ctrl)
            {
                res = true;
                break;
            }
        }
        // Return result of search
        return res;
    }
    private boolean isPlayerFree(int plyr)
    {
        // init local result
        boolean res = true;
        // Look for player ID
        for(PlayerInfo p: this.playerList)
        {
            if(p.getPlayerID() == plyr)
            {
                res = false;
                break;
            }
        }
        // Return result of search
        return res;
    }
    private void invalidatePlayer(int ctrlID)
    {
        // Check if the current controller is already in the list
        if(this.isControllerRegistered(ctrlID))
        {
            // this controller is already registered
            for(int i=0;i<this.playerList.size();i++)
            {
                if(this.playerList.get(i).getControllerID() == ctrlID)
                {
                    // Invalidate choice and exit
                    this.playerList.get(i).invalidateChoice();
                    break;
                }
            }
        }
    }
    private void validatePlayer(int ctrlID)
    {
        // Check if the current controller is already in the list
        if(this.isControllerRegistered(ctrlID))
        {
            // this controller is already registered : this means this is the second time it presses a button
            for(int i=0;i<this.playerList.size();i++)
            {
                if(this.playerList.get(i).getControllerID() == ctrlID)
                {
                    // Validate choice and exit
                    this.playerList.get(i).validateChoice();
                    break;
                }
            }
        }
    }
    private boolean isAllPlayersInvalidated()
    {
        // Init result
        boolean res = true;
        // Look for all players, and if at least one is a valid, we return false
        for(PlayerInfo p: this.playerList)
        {
            if(p.isChoiceValid())
            {
                res = false;
            }
        }
        // return result
        return res;
    }
    private void addPlayer(int ctrlID)
    {
        // Check if the current controller is not already in the list
        if(!this.isControllerRegistered(ctrlID))
        {
            // this controller is the first time to press a button : add it into the list (if there is some space remaining)
            if(this.playerList.size() < Common.NB_MAX_PLAYERS)
            {
                // Add controller in the list
                //String ctrlNam = Controllers.getController(ctrlID).getName();
                this.playerList.add( new PlayerInfo("", ctrlID, Common.PLAYER_COLORS[this.playerList.size()]) );
                // Now decide which player ID we can set
                for(int i=0;i<Common.NB_MAX_PLAYERS;i++)
                {
                    if(this.isPlayerFree(i))
                    {
                        this.playerList.get(this.playerList.size()-1).setPlayerID(i);
                        break;
                    }
                }
            }
        }
    }
    private void startGame(int ctrlID)
    {
        // Check if we have enough players to start
        boolean isGameReadyToStart = true;
        if(this.playerList.size()<=0)
        {
            isGameReadyToStart = false;
        }
        // Check all players have been validated
        for(PlayerInfo p:this.playerList)
        {
            if(!p.isChoiceValid())
            {
                isGameReadyToStart = false;
                break;
            }
        }
        // Check the current controller button is one of the players (and not another unused controller plugged to the computer)
        if(isGameReadyToStart)
        {
            isGameReadyToStart = false;
            for(PlayerInfo p:this.playerList)
            {
                if(p.getControllerID()==ctrlID)
                {
                    isGameReadyToStart = true;
                    break;
                }
            }
        }
        // Go to InGame state if needed
        if(isGameReadyToStart)
        {
            // Init and go to InGame state
            State03Game sig = new State03Game(this.playerList);
            this.gameObject.addState( sig );
            try
            {
                sig.init(this.container, this.gameObject);
            }
            catch(Exception e)
            {
                throw new Error(e);
            };
            this.gameObject.enterState( State03Game.ID, new FadeOutTransition(Common.COLOR_FADE_IN, Common.TIME_FADE_IN), new FadeInTransition(Common.COLOR_FADE_OUT, Common.TIME_FADE_OUT) );
        }
    }
    
    
    //------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------
    public State02Menu()
    {
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
        
        // Init background image
        this.background = new Image("sprites/backgrounds/select.jpg");
        // Init player images
        this.playerImg  = new Image("sprites/menus/slotPlayer.png");     
        // Init pad controller image
        this.padImg  = new Image("sprites/menus/gamepad.png").getScaledCopy(Common.GAMEPAD_SIZE, Common.GAMEPAD_SIZE);     
        
        // Create anim with all players
        SpriteSheet ss;
        this.animPlayers = new ArrayList<Animation>();
        this.animPlayers.add(new Animation());
        ss = new SpriteSheet("./sprites/characters/circle.png",512,512);
        this.animPlayers.get(0).addFrame(ss.getSprite(0,0).getScaledCopy(100, 100), 100);
        this.animPlayers.add(new Animation());
        ss = new SpriteSheet("./sprites/characters/square.png",512,512);
        this.animPlayers.get(1).addFrame(ss.getSprite(0,0).getScaledCopy(100, 100), 100);
        this.animPlayers.add(new Animation());
        ss = new SpriteSheet("./sprites/characters/cross.png",512,512);
        this.animPlayers.get(2).addFrame(ss.getSprite(0,0).getScaledCopy(100, 100), 100);
        this.animPlayers.add(new Animation());
        ss = new SpriteSheet("./sprites/characters/triangle.png",512,512);
        this.animPlayers.get(3).addFrame(ss.getSprite(0,0).getScaledCopy(100, 100), 100);
        
        // init button A
        this.buttonA = new Image("./sprites/menus/buttonA.png").getScaledCopy(100,100);
        
        // Init player list and info before game
        this.playerList = new ArrayList<PlayerInfo>();
    }
    
    
    //------------------------------------------------
    // RENDER METHOD
    //------------------------------------------------
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Fit Screen
        MainLauncher.fitScreen(container, g);
    
        // Render menu background
        g.drawImage(this.background, 0, 0);
        
        // Render player images
        // [TODO]
        for(int x=0;x<Common.NB_PLAYERS_COLS;x++)
        {
            for(int y=0;y<Common.NB_PLAYERS_ROWS;y++)
            {
                // Get position of the player and index value
                int x0  = (x*Common.PLAYERS_COL_W)+Common.PLAYERS_OFFSET_X;
                int y0  = (y*Common.PLAYERS_ROW_H)+Common.PLAYERS_OFFSET_Y;
                int idx = x + (y*Common.NB_PLAYERS_COLS); 
                // check if current player has been selected
                PlayerInfo player = null;
                for(PlayerInfo p: this.playerList)
                {
                    if(p.getPlayerID() == idx)
                    {
                        player = p;
                        break;
                    }
                }
                // Draw a rectangle around player selections
                if(player != null)
                {
                    g.setColor(player.getColor());
                }
                
                // draw the player selection
                g.drawImage(this.playerImg, x0, y0);
                
                if(player == null)
                {
                    g.drawImage(this.buttonA, x0+70, y0+70);
                }
                
                if(player != null)
                {
                    // Display Gamepad
                    int x1 = x0+Common.PLAYERS_COL_W-Common.PLAYERS_BORDER_W-(int)(0.5*Common.GAMEPAD_SIZE);
                    int y1 = y0+Common.PLAYERS_ROW_H-Common.PLAYERS_BORDER_H-(int)(0.5*Common.GAMEPAD_SIZE);
                    g.drawImage(this.padImg, x1,y1,player.getColor());
                    g.setColor(Color.black);
                    g.drawString(Integer.toString(player.getControllerID()),x1+Common.GAMEPAD_SIZE/2-5,y1+Common.GAMEPAD_SIZE/2+5);
                    g.drawString(player.getControllerName(), x1, y0+20);
                    // Display Player
                    int x2 = x0 + 70;
                    int y2 = y0 + 70;
                    int index = (x + (y*Common.SHAPE_INDEX_NB))%Common.SHAPE_INDEX_NB; 
                    g.drawAnimation(this.animPlayers.get(index), x2, y2);
                }
            }
        }
        
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
            // Go back to start screen if we press ESC
            case Input.KEY_ESCAPE:
                this.goBackToStartMenu();
                break;
            // Do nothing for other keys 
            default:
                break;
        }
    }
    
        
    //------------------------------------------------
    // CONTROLLER METHODS
    //------------------------------------------------
    @Override
    public void controllerButtonPressed(int controller, int button)
    {   
        // Get button name index
        int buttonName = ControllerButtons.getButtonNameFromButtonID(controller,button);
        // check which action must be performed
        switch(buttonName)
        {
            // Try to Start game
            case ControllerButtons.BUTTON_START:
                this.startGame(controller);
                break;
            // Try to invalidate a player
            case ControllerButtons.BUTTON_BACK:
                this.goBackToStartMenu();
                // Check if all players are already invalidated : if yes, return to start menu
                /*
                if(this.isAllPlayersInvalidated())
                {
                    this.goBackToStartMenu();
                }
                else
                {
                    this.invalidatePlayer(controller);
                }
                //*/
                break;
            // Player selection by default when any other registered button is pressed
            case ControllerButtons.BUTTON_A:
            case ControllerButtons.BUTTON_B:
            case ControllerButtons.BUTTON_X:
            case ControllerButtons.BUTTON_Y:
            case ControllerButtons.BUTTON_L1:
            case ControllerButtons.BUTTON_R1:
                // then simply try to add a player (if not already registered)
                this.addPlayer(controller);
                // first try to validate a player (if already registered)
                this.validatePlayer(controller);
                break;           
            // No action for other buttons
            default:
                break;
        }
    }
    @Override
    public void controllerLeftPressed(int controller)
    {
        // Move selection (if current player has not validated its current selection)
//        this.moveSelection(controller, -1);
    }    
    @Override
    public void controllerRightPressed(int controller)
    {
        // Move selection (if current player has not validated its current selection)
//        this.moveSelection(controller, 1);
    }    
    @Override
    public void controllerUpPressed(int controller)
    {
        // Move selection (if current player has not validated its current selection)
//        this.moveSelection(controller, -Common.NB_PLAYERS_COLS);
    }    
    @Override
    public void controllerDownPressed(int controller)
    {
        // Move selection (if current player has not validated its current selection)
//        this.moveSelection(controller, Common.NB_PLAYERS_COLS);
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