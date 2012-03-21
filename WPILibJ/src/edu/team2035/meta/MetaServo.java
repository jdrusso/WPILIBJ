/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author abbottk
 */
public class MetaServo extends Servo implements MetaObject{
    
    private String name;
    
    public MetaServo(final int channel, String n) {
        super(channel);
        name = n;
    }

    public MetaServo(final int slot, final int channel, String n) {
        super(slot, channel);
        name = n;
    }
    
   
    public void addToLog(){        
        MetaLog.addObject(this);
    }
    
    public String initialize() {
        
        return name;
    
    }
    
    public String update(){
        
        return "" + get();
        
    }
}
