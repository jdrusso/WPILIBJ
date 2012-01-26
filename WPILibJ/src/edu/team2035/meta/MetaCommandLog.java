/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.team2035.meta;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author abbottk
 */
public class MetaCommandLog {

    private Subsystem MetaSub;
    private static String commandName;
    
    public MetaCommandLog(Subsystem s){
        MetaSub = s;
        MetaLog.addObject(this);
    }

    public String initialize(){


        return MetaSub.getName();
    
    }

    public String update(){

        return commandName;

    }
    
    public static synchronized void setCommand(String s){
        commandName = s;
    }



}
