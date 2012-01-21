/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

        MetaLog.addObject(this);
        MetaCal = new Timer();
    }
    
     public String update() {
         
         return "" + MetaCal.get();
        
     }
     
     public String initialize() {
         
        MetaCal.start();
        return name;
    
     }
    
    
}
