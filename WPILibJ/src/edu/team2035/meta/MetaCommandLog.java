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

    private  String Subsystem;
    private  String commandName;
    private  String inputs;
    private  String inputValues;    
    private  String outputs;
    private  String outputValues;
    
    public MetaCommandLog(String sub, String input, String output){
        MetaLog.addObject(this);
        Subsystem = sub;
        inputs = input;
        outputs = output;
    }

    public String initialize(){


        return Subsystem + "," + inputs + "," + outputs;
    
    }

    public String update(){

        return commandName + "," + inputValues + "," +outputValues;

    }
    
    public synchronized void setCommand(String s){
        commandName = s;
    }
    
    
    public synchronized void setInputs(String inputValue){
        inputValues = inputValue;
    }
    
    public synchronized void setOutputs(String outputValue){
        outputValues = outputValue;
    }



}
