/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.team2035.meta;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author abbottk
 */
public class MetaGyro extends Gyro{
    
    private String name = "Gyro";
    
    public MetaGyro(int slot, int channel) {
        super( slot, channel);
        MetaLog.addObject(this);
    }

    /**
     * Gyro constructor with only a channel.
     *
     * Use the default analog module slot.
     *
     * @param channel The analog channel the gyro is connected to.
     */
    public MetaGyro(int channel) {
        super( channel);
        MetaLog.addObject(this);
    }
    /**
     * Gyro constructor with a precreated analog channel object.
     * Use this constructor when the analog channel needs to be shared. There
     * is no reference counting when an AnalogChannel is passed to the gyro.
     * @param channel The AnalogChannel object that the gyro is connected to.
     */
    public MetaGyro(AnalogChannel channel) {
        super( channel);
        MetaLog.addObject(this);
        }
    
   
    
    public String initialize() {
        
        return name;
    
    }
    
    public String update(){
        
        return "" + getAngle();
        
    }
}
