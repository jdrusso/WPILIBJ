/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.team2035.meta;

import java.util.TimerTask;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;
import edu.wpi.first.wpilibj.Timer;
/**
 *
 * @author abbottk
 */
public class MetaTimer {
    //private static int seconds;
    private Timer MetaCal;
    private long StartTime;
    private long CurrentTime;
    private long TimeChange;
    
    
    public MetaTimer(){
        //seconds = 0;
        MetaLog.addObject(this);
       // MetaTimerTask MetaTask =  new MetaTimerTask();
       // Timer time = new Timer();
        //time.schedule( MetaTask, 0, 10);
        MetaCal = new Timer();
        
       //StartTime = MetaCal.getTime();
    }
    
     public String update() {
         //MetaCal= new Date();
         //CurrentTime = MetaCal.getTime();
         //TimeChange = CurrentTime -  StartTime;
         return "" + MetaCal.get();
        }
     
     public String getName() {
        MetaCal.start();
        String name = "Timer";
        return name;
    }
    
    //private class MetaTimerTask extends TimerTask{
        
        //public MetaTimerTask() {
           // super();
            
        //}
        //public void run(){
         //   seconds += 10;
        //    
       // }
       
        
   // }
    
}
