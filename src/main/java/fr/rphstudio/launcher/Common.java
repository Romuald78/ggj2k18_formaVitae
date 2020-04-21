/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.launcher;

import org.newdawn.slick.Color;

/**
 *
 * @author GRIGNON FAMILY
 */
public class Common
{
    //---------------------------------------------------------
    // PROGRAM CONSTANTS
    //---------------------------------------------------------
    // Screen default dimensions (automatically resized, if the screen has a different resolution)
    public static final float   SCREEN_WIDTH    = 1920.0f;
    public static final float   SCREEN_HEIGHT   = 1080.0f;    
    // Colors used during game state transitions
    public static final Color   COLOR_FADE_IN   = Color.orange;
    public static final Color   COLOR_FADE_OUT  = Color.yellow;
    // Timings used during game state transitions
    public static final int     TIME_FADE_IN    = 125*2;
    public static final int     TIME_FADE_OUT   = 125*2;
    // Colors used for players
    public static final Color[] PLAYER_COLORS   =
    {
        new Color(192,  0,  0), // Red
        new Color(  0,  0,192), // Blue
        new Color(  0,192,  0), // Green
        new Color(192,192,  0), // Yellow
        new Color(  0,192,192), // Cyan
        new Color(192,  0,192), // Purple
        /*
        new Color(255,255,255), // White
        new Color(128,128,128), // Gray
        //*/
    };
    public static final int     NB_MAX_PLAYERS   = PLAYER_COLORS.length;
    public static final int     NB_PLAYERS_COLS  = 2;
    public static final int     NB_PLAYERS_ROWS  = 3;
    public static final int     PLAYERS_COL_W    = 180;
    public static final int     PLAYERS_ROW_H    = 180;
    public static final int     PLAYERS_BORDER_W = 0;
    public static final int     PLAYERS_BORDER_H = 0;
    public static final int     PLAYERS_OFFSET_X = 275;
    public static final int     PLAYERS_OFFSET_Y = 280;
    
    // Controller identifiers
    public static final int     CONTROLLER_NONE     = -1;
    public static final int     CONTROLLER_KEYBOARD = 100000;
    
    // Game pad image size
    public final static int     GAMEPAD_SIZE        = 64;
    
    // Physic-Display relationships
    public final static int     NB_PIXELS_PER_METER = 64;
    public final static float   SPRITE_PHYSIC_DISPLAY_RATIO = 0.75f; 
    
    
    // PLAYER MANUAL MOVE PARAMETERS
    public final static float   PLAYER_MOVE_SPEED_FACTOR = 0.0007f;
    public final static float   PLAYER_MOVE_DAMPENING    = 0.95f; // The greater,  the lower :-)
    public final static float   PLAYER_ROTATE_SPEED_MIN  = 2; 
    public final static float   PLAYER_ROTATE_SPEED_MAX  = 15; 
    public final static float   PLAYER_ROTATE_SPEED_INC  = 1.003f;
    
    // SHAPE NUMBERS
    public final static int     SHAPE_INDEX_CIRCLE   = 3;
    public final static int     SHAPE_INDEX_SQUARE   = 2;
    public final static int     SHAPE_INDEX_TRIANGLE = 1;
    public final static int     SHAPE_INDEX_CROSS    = 0;
    public final static int     SHAPE_INDEX_NB       = 4;
    
    // SCALE PARAMETERS
    public final static float   SCALE_COEF = 0.001f;
    public final static float   SCALE_MIN  = 1.0f;
    public final static float   SCALE_MAX  = 1.0f;
    
    // PLAYER LIMIT BOX BORDERS
    public final static int     LIMIT_BOX_PIXELS_LEFT   = 0;     
    public final static int     LIMIT_BOX_PIXELS_RIGHT  = 1920;     
    public final static int     LIMIT_BOX_PIXELS_TOP    = 0;     
    public final static int     LIMIT_BOX_PIXELS_BOTTOM = 1080;
    
    //Scrolling
    static public final float   SCROLLING_SPEED = 0.1f;

    // Enemy generation parameters
    static public final long    GENERATION_MAX_INTERVAL_MS  = 50*100;
    static public final long    GENERATION_MIN_INTERVAL_MS  = 10*100;
    static public final long    GENERATION_STEP_INTERVAL_MS = 10;       //  Sn = (n+1).(U0+Un)/2
    
    // Multi shape parameters
    static public final float   MULTI_SHAPE_RADIUS       = 48;
    static public final float   MULTI_SHAPE_DELTA_RADIUS = 24;
    
    // GAME LIFE LEVEL
    static public final int     LIFE_LEVEL = 2000;
    
    // TEXT COLOR
    static public final Color   TEXT_COLOR = new Color(237,59,42);

    // MATCHING SHAPE, POSITION, ROTATION
    static public final float   MATCH_DELTA_POSITION_X = 16.0f;
    static public final float   MATCH_DELTA_POSITION_Y = 16.0f;
    static public final float   MATCH_DELTA_ANGLE      = 15.0f;
    
    // END GAME CRITERIA
    static public final int     END_GAME_NB_CONTACTS   = 10;

}