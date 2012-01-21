/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    private static Vector objects = new Vector( 10 , 1 );
    private static boolean MetaFileOpen = false;
    private static FileConnection MetaFile;
    private static OutputStream MetaOs;
    private static PrintStream MetaPs;
    private static Thread Meta_Thread;
    
    public static void addObject( Object m ) {
        
        objects.addElement( m );
        
    }
    
    public synchronized static void update () {
        
        if(!MetaFileOpen){
            
            startNewLog();
            addColumn(objects);
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
            
            // TBD
        }
        
    }
    
    public static void addColumn( Vector MetaObjects){
        
        Enumeration e = MetaObjects.elements();
        int numElements = MetaObjects.capacity()-1;
        while( e.hasMoreElements() ){
            
            Object MetaEnum = e.nextElement();
            if (MetaEnum instanceof MetaTimer){
                
                MetaPs.print(((MetaTimer) MetaEnum).getName());
                if(numElements > 0){
                    
                    MetaPs.print(",");
                    numElements--;
                    
                }
                
            }
            if (MetaEnum instanceof MetaGyro){
                
                MetaPs.print(((MetaGyro) MetaEnum).getName());
                if(numElements > 0){
                    
                    MetaPs.print(",");
                    numElements--;
                    
                }
                
            }
            
            
        }
        MetaPs.print("\n");
        
    }
    
    public static void close(){
        
        MetaFileOpen = false;
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
    
    public static void start(){
        
        MetaFileOpen = true;
        Meta_Thread = new MetaThread();
        Meta_Thread.run();          
        
    }
    
    private static class MetaThread extends Thread {     

        public MetaThread () {
            
        }
        
        public void run() {
            
            startNewLog();
            addColumn(objects);
            
            while (MetaFileOpen) {
                update();
                //try {
                    //Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //}
               
            }
            closeLog();
        }
    }
    
}
