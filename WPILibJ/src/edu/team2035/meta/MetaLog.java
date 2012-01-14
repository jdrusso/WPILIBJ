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
    
    public static void addObject( Object m ) {
        objects.addElement( m );
    }
    public static void update () {
        if(!MetaFileOpen){
            startNewLog();
            addColumn(objects);
            MetaFileOpen = true;
        }
        Enumeration e = objects.elements();
        int numElements = objects.capacity()-1;
        while( e.hasMoreElements() ){
            if (e.nextElement() instanceof MetaGyro){
                MetaPs.print(((MetaGyro) e.nextElement()).update());
            }
            if(numElements > 0){
                MetaPs.print(",");
                numElements--;
            }
            else {
                MetaPs.print("\n");
            }
        }
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
            //for (int i = 0; i < 10; i++)
            //{
            //    MetaPs.println("123\n");
            //}
            
        } catch(IOException e) {
            // TBD
        }
        
    }
    
    public static void addColumn( Vector MetaObjects){
        Enumeration e = MetaObjects.elements();
        int numElements = MetaObjects.capacity()-1;
        while( e.hasMoreElements() ){
            if (e.nextElement() instanceof MetaGyro){
                MetaPs.print(((MetaGyro) e.nextElement()).getName());
                if(numElements > 0){
                    MetaPs.print(",");
                    numElements--;
                }
                else {
                    MetaPs.print("\n");
                }
            }
        }
        
    }
    
    public static void close(){
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
