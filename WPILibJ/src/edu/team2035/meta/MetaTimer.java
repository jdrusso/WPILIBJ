/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import edu.wpi.first.wpilibj.Timer;
/**
 *
 * @author abbottk
 */
public class MetaTimer {
    
    private Timer MetaCal;
    private String name = "Timer";
    
    
    public MetaTimer(){

        MetaCal = new Timer();
    }
    
    public void assToLog(){
        MetaLog.addObject(this);
    }
    
    public String update() {
         
         return "" + MetaCal.get();
        
     }
     
     public String initialize() {
         
        MetaCal.start();
        return name;
    
     }
    
    
}
