/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * 
 * @author Team 2035 Programmers
 */
public class MetaUltrasonic extends Ultrasonic implements MetaObject {
    
    private String name;
    
    public MetaUltrasonic(final int pingChannel, final int echoChannel, Unit units, String n) {
        super(pingChannel, echoChannel, units);
        name = n;
    }

    public MetaUltrasonic(final int pingChannel, final int echoChannel, String n) {
        super(pingChannel, echoChannel);
        name = n;
    }

    public MetaUltrasonic(DigitalOutput pingChannel, DigitalInput echoChannel, Unit units, String n) {
        super(pingChannel, echoChannel, units);
        name = n;
    }

    public MetaUltrasonic(DigitalOutput pingChannel, DigitalInput echoChannel, String n) {
        super(pingChannel,echoChannel);
        name = n;
    }

    public MetaUltrasonic(final int pingSlot, final int pingChannel,
            final int echoSlot, final int echoChannel, Unit units, String n) {
        super(pingSlot, pingChannel, echoSlot, echoChannel, units);
        name = n;
    }

    public MetaUltrasonic(final int pingSlot, final int pingChannel,
            final int echoSlot, final int echoChannel, String n) {
        super(pingSlot, pingChannel, echoSlot, echoChannel);
        name = n;
    }

    public void addToLog(){        
        MetaLog.addObject(this);
    }
    
    public String initialize() {
        
        return name;
    
    }
    
    public String update(){
        
        return "" + getRangeInches();
        
    }
}