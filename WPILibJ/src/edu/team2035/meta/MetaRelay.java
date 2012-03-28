/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author abbottk
 */
public class MetaRelay extends Relay implements MetaObject{
    
    private String name;
    
    public MetaRelay(final int moduileNumber, final int channel, Direction direction, String n) {
        super(moduileNumber, channel, direction);
        name = n;
    }

    public MetaRelay(final int channel, Direction direction, String n) {
        super(channel, direction);
        name = n;
    }

    public MetaRelay(final int moduleNumber, final int channel, String n) {
        super(moduleNumber, channel);
        name = n;
    }

    public MetaRelay(final int channel, String n) {
        super(channel);
        name = n;    
    }
       
    public void addToLog(){        
        MetaLog.addObject(this);
    }
    
    public String initialize() {
        
        return name;
    
    }
    
    public String update(){
        
        switch(get()){
            case(0):
                return "Off";

            case(1):
                return "On";

            case(2):
                return "Forward";

            case(3):
                return "Reverse";

        }        
        return "null";
    
    }
}
