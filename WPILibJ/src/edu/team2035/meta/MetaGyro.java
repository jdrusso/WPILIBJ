/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author abbottk
 */
public class MetaGyro extends Gyro implements MetaObject{
    
    private String name;
    
    public MetaGyro(int slot, int channel, String n) {
        super( slot, channel);
        name = n;
    }

    /**
     * Gyro constructor with only a channel.
     *
     * Use the default analog module slot.
     *
     * @param channel The analog channel the gyro is connected to.
     */
    public MetaGyro(int channel, String n) {
        super( channel);
        name = n;
    }
    /**
     * Gyro constructor with a procreated analog channel object.
     * Use this constructor when the analog channel needs to be shared. There
     * is no reference counting when an AnalogChannel is passed to the gyro.
     * @param channel The AnalogChannel object that the gyro is connected to.
     */
    public MetaGyro(AnalogChannel channel, String n) {
        super( channel);
        name = n;
    }
    
   
    public void addToLog(){        
        MetaLog.addObject(this);
    }
    
    public String initialize() {
        
        return name;
    
    }
    
    public String update(){
        
        return "" + getAngle();
        
    }
}
