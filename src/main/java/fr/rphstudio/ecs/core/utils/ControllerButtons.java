/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.utils;


public class ControllerButtons
{
    //------------------------------------------------
    // PUBLIC CONSTANTS
    //------------------------------------------------
    // Controller names as reported by the LWJGL
    public static final String CFG_XBOX360           = "xbox";       //Controller (Xbox 360 Wireless Receiver for Windows)";
    public static final String CFG_MAYFLASH_GAMECUBE = "gamecube";   //"MAYFLASH GameCube Controller Adapter";
    // Default controller index to use (in case we don't find the one reported by LWJGL)
    public static final int    CFG_INDEX_DEFAULT     = 0;
    // Button names
    public static final int    BUTTON_NONE      = -1;
    public static final int    BUTTON_A         =  0;
    public static final int    BUTTON_B         =  1;
    public static final int    BUTTON_X         =  2;
    public static final int    BUTTON_Y         =  3;
    public static final int    BUTTON_L1        =  4;
    public static final int    BUTTON_R1        =  5;
    public static final int    BUTTON_BACK      =  6;
    public static final int    BUTTON_START     =  7;
    public static final int    BUTTON_MAX       =  8;
    
    public static final int    AXIS_ANALOG_X    =  0;
    public static final int    AXIS_ANALOG_Y    =  1;
    public static final int    AXIS_ANALOG_MAX  =  2;
    
    // MAX buttons per controller
    public static final int    NB_BUTTONS_PER_CONTROLLER = 10;
    
    
    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private static final String[] CONFIG_NAMES =
    {
        ControllerButtons.CFG_XBOX360,
        ControllerButtons.CFG_MAYFLASH_GAMECUBE,
    };
    private static final int[][] CONFIG_BUTTONS =
    {
        {1,2,3,4,5,6,7, 8}, // XBOX 360 Wireless
        {2,3,1,4,5,6,8,10}, // MayFlash GameCube
    };
    private static final int[][] CONFIG_AXIS =
    {
        {1,0}, // XBOX 360 Wireless
        {3,2}, // MayFlash GameCube
    };
    private static String[] controllerNames = null; 
    
    
    //------------------------------------------------
    // PRIVATE STATIC METHODS
    //------------------------------------------------
    static private int lookForControllerConfig(String ctrlNam)
    {
        // Look for each entry in the config table
        for(int i=0;i<ControllerButtons.CONFIG_NAMES.length;i++)
        {
            // If we have found the controller name in it
            //if( ControllerButtons.CONFIG_NAMES[i].equalsIgnoreCase(ctrlNam) )
            if( ctrlNam.toLowerCase().indexOf(ControllerButtons.CONFIG_NAMES[i]) >= 0 )
            {
                // Return its index
                return i;
            }
        }
        // We haven't found the controller name in the table : return default entry
        return ControllerButtons.CFG_INDEX_DEFAULT;
    }
    
    
    //------------------------------------------------
    // PUBLIC STATIC METHODS
    //------------------------------------------------
    static public void initControllerNames(String[] names)
    {
        int i=0;
        for(i=0;i<names.length;i++)
        {
            if(names[i] == null)
            {
                break;
            }
        }
        if(i > 0)
        {
            ControllerButtons.controllerNames = new String[i];
            for(i=0;i<ControllerButtons.controllerNames.length;i++)
            {
                ControllerButtons.controllerNames[i] = names[i];
            }
        }
    }
    static public int getButtonNameFromButtonID(int ctrlID, int buttonID)
    {
        // Check if controller is in the list
        int cfgIndex = ControllerButtons.lookForControllerConfig(ControllerButtons.controllerNames[ctrlID]);
        // Get value from the table and return it
        for(int i=0;i<ControllerButtons.BUTTON_MAX;i++)
        {
            if(ControllerButtons.CONFIG_BUTTONS[cfgIndex][i] == buttonID)
            {
                return i;
            }
        }
        // Return default button value
        return ControllerButtons.BUTTON_NONE;
    }
    static public int getAxisIDFromAxisName(int ctrlID, int axisName)
    {
        // Check if controller is in the list
        int cfgIndex = ControllerButtons.lookForControllerConfig(ControllerButtons.controllerNames[ctrlID]);
        // Get value from the table and return it
        for(int i=0;i<ControllerButtons.AXIS_ANALOG_MAX;i++)
        {
            if(i == axisName)
            {
                return ControllerButtons.CONFIG_AXIS[cfgIndex][i];
            }
        }
        // Return default button value
        return ControllerButtons.BUTTON_NONE;
    }
    
    
}



/*
Liste des boutons avec leurs numeros en fonction de la manette
             0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18   19   20
XBOX 360          A    B    X    Y   LU   RU   BK   ST                                                             "Controller (Xbox 360 Wireless Receiver for Windows)"   
GAMECUBE          X    A    B    Y   LD   RD         Z        ST        UP  RIG  DOW  LEF                          "MAYFLASH GameCube Controller Adapter"  

Pseudo PS         1    2    3    4   LU   RU   LD   RD   SL   ST                                                   "Generic   USB  Joystick"
PS4 DualShock     X    A    B    Y   LU   RU             SH   OP                                                   "Wireless Controller"

PS3                                                                                                                "PLAYSTATION(R)3 Controller"
WII U

XBOX ONE         
PS2
PS3
PS4
//*/
