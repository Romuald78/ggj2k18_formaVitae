

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.launcher;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainLauncher extends StateBasedGame
{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    
    public static void fitScreen(GameContainer container, Graphics g)
    {
        // rendering is done : now try to scale it to fit the full screen
        float sx  = ((AppGameContainer)container).getScreenWidth()/(float)WIDTH;
        float sy  = ((AppGameContainer)container).getScreenHeight()/(float)HEIGHT;
        float sxy = Math.min(sx,sy); 
        float tx  = (((AppGameContainer)container).getScreenWidth() -(WIDTH*sxy))/2;
        float ty  = (((AppGameContainer)container).getScreenHeight()-(HEIGHT*sxy))/2;
        // Scale and translate to the center of screen
        g.scale(sxy, sxy);
        g.translate(tx, ty);
        
        g.setBackground(Color.white);
    }
    
    public static void main(String[] args) throws SlickException
    {
        // Full game HD
        AppGameContainer appGame = new AppGameContainer(new MainLauncher());
        appGame.setDisplayMode(appGame.getScreenWidth(), appGame.getScreenHeight(), true);
        appGame.start();
    }  
    
    public MainLauncher()
    {
        super("RPH Studio");
    }
     
    @Override
    public void initStatesList(GameContainer container) throws SlickException
    {
        // Remove or Display FPS
        container.setShowFPS(false);
        // Modify Title and Icon
        AppGameContainer appContainer = (AppGameContainer) container;
        appContainer.setVSync(true);
        
        // First set icons
        if(!container.isFullscreen())
        {
            String[] icons = { "icon32x32.png", "icon16x16.png"};
            container.setIcons(icons);
        }
        
        // Add Start and Select states
        this.addState( new State01Start() );
        this.addState( new State02Menu () );
        // Other states like ingame, pause and end, will be created on-the-fly depending on the data to handle
    }
  
}

