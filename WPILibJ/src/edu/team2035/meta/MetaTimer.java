/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.team2035.meta;

import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author abbottk
 */
public class MetaTimer {
    private static int seconds;
    
    public void MetaTimer(){
        seconds = 0;
        MetaLog.addObject(this);
        MetaTimerTask MetaTask =  new MetaTimerTask();
        Timer time = new Timer();
        time.schedule( MetaTask, 0, 10);
    }
    
     public String update() {
            return "" + seconds;
        }
     
     public String getName() {
        String name = "Timer";
        return name;
    }
     
    private class MetaTimerTask extends TimerTask{
        
        public MetaTimerTask() {
            super();
            
        }
        public void run(){
            seconds += 10;
            
        }
       
        
    }
    
}
