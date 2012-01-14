/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.team2035.meta;
import edu.wpi.first.wpilibj.Joystick;
/**
 *
 * @author Robot
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