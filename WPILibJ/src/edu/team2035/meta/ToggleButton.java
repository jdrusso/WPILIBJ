/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Team 2035 Programmers
 */
public class ToggleButton extends Button{
    
    private boolean state;
    private Joystick JoyObject;
    private int buttonNumber;
    private boolean toggled;
    
    public ToggleButton ( Joystick j, int bn ) {
        
        JoyObject = j;
        buttonNumber = bn;
        state = false;
        toggled = false;
        
    }
    
    public void update (){
        boolean newState = JoyObject.getRawButton(buttonNumber);
        toggled = state ^ newState;
        state = newState;
    }
    
    public boolean isOn () {
        return state;
    }
    
    public boolean isOff () {
        return !state;
    }
   
    public boolean isToggled () {
        
        return toggled;

    }

}