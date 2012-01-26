/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import java.io.*;
import javax.microedition.io.*;
import com.sun.squawk.microedition.io.FileConnection;
import java.util.Vector;
import java.util.Enumeration;

/**
 *
 * @author abbottk
 */
public class MetaLog {
    
    private static Vector objects = new Vector( 20 , 1 );
    private static boolean MetaFileOpen = false;
    private static FileConnection MetaFile;
    private static OutputStream MetaOs;
    private static PrintStream MetaPs;
    
    public static void addObject( Object m ) {
        
        objects.addElement( m );
        
    }
    
    public synchronized static void update() {
        
        if(!MetaFileOpen){
            
            startNewLog();
            finalizeColumns(objects);
            MetaFileOpen = true; 
            
        }
        Enumeration e = objects.elements();
        int numElements = objects.capacity()-1;
        while( e.hasMoreElements() ){
            
            Object MetaEnum = e.nextElement();
            if (MetaEnum instanceof MetaGyro){
                
                MetaPs.print(((MetaGyro) MetaEnum).update());
                
            }            
            if (MetaEnum instanceof MetaTimer){
                
                MetaPs.print(((MetaTimer) MetaEnum).update());
                
            }
            if (MetaEnum instanceof MetaCommandLog){
                
                MetaPs.print(((MetaCommandLog) MetaEnum).update());
                
            }
            if(numElements > 0){
                
                MetaPs.print(",");
                numElements--;
                
            }
            
        }
        MetaPs.print("\n");
        
    }
    
    public static void startNewLog(){
        try {
            
                int logNum = 1;
                boolean openLogNum = false;
                while(!openLogNum) {
                
                    MetaFile = (FileConnection)Connector.open("file://log" + logNum + ".csv");
                    if(MetaFile.exists()){
                        
                        logNum++;
                        
                    }
                    else{
                        
                        openLogNum = true;
                        
                    }
                }
            MetaFile = (FileConnection)Connector.open("file://log" + logNum + ".csv");
            MetaFile.create();
            MetaOs = MetaFile.openOutputStream();
            MetaPs = new PrintStream(MetaOs);
            
        } catch(IOException e) {
            
            
        }
        
    }
    
    public static void finalizeColumns( Vector MetaObjects){
        
        Enumeration e = MetaObjects.elements();
        int numElements = MetaObjects.capacity()-1;
        while( e.hasMoreElements() ){
            
            Object MetaEnum = e.nextElement();
            if (MetaEnum instanceof MetaTimer){
                
                MetaPs.print(((MetaTimer) MetaEnum).initialize());
                if(numElements > 0){
                    
                    MetaPs.print(",");
                    numElements--;
                    
                }
                
            }
            if (MetaEnum instanceof MetaGyro){
                
                MetaPs.print(((MetaGyro) MetaEnum).initialize());
                if(numElements > 0){
                    
                    MetaPs.print(",");
                    numElements--;
                    
                }
                
            }
            if (MetaEnum instanceof MetaCommandLog){
                
                MetaPs.print(((MetaCommandLog) MetaEnum).initialize());
                if(numElements > 0){
                    
                    MetaPs.print(",");
                    numElements--;
                    
                }
                
            }
            
            
        }
        MetaPs.print("\n");
        
    }
    
    
    public synchronized static void closeLog(){
        try {
            
                if(MetaFileOpen){   
                
                    MetaPs.close();
                    MetaOs.close();
                    MetaFile.close();
                    MetaFileOpen = false;
                                
                }
            
            
        } catch(IOException e) {
            
            // TBD
        }
    }
    
}
