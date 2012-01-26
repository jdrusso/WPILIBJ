/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.UDPDatagramConnection;


/** MetaUDPVariables
 * For receiving variables from the dashboard.
 *
 * @author Team 2035 Programmers
 */
public class MetaUDPVariables {

    Vector connections;
    Hashtable variables;

    static final int PORT = 7000;  // UDP connection port
    
    /**
     * Starting the constructor causes it to accept new connections and
     * the open connections will be read for data.
     */
    public MetaUDPVariables()
    {
       connections = new Vector(); // Vector of open connections.
       variables = new Hashtable(); // Hastable of key/value pairs to store values
       
       new Thread() {
           public void run() {
               acceptConnections();
           }
       }.start();   
       
       new Thread() {
           public void run() {
               update();
           }
       }.start(); 
    }
    
    /** newVariableValue
     * Called internally to update the key pair in the Hashtable
     * @param s called the key, this is String such as "positionx"
     * @param o called the value, this is an Object that stores its content
     */
    private synchronized void newVariableValue(String s, Object o)
    {
        variables.put(s, o);
    }
    
    /**
     * 
     * @param s called the key, this is the String identifer of the object of interest such as "rectanglelength" 
     * @return The Object.  The Object will need to be cast in its use.
     */
    public synchronized Object getVariableValue(String s)
    {
        return variables.get(s);
    }

    /**
     * 
     * @param s called the key, this is the String identifer of the object of interest such as "rectanglelength" 
     * @return a float value of the object or -99 if there is an error.
     */
    public synchronized float getVariableFloatValue(String s)
    {
        Object o = variables.get(s);
        if (o instanceof Float)
        {
            return ((Float)o).floatValue();
        }
        return (float)-99.0;
    }

    /**
     * Accepts UDP connections to the robot on the specified port.
     */
    private synchronized void acceptConnections() {
        UDPDatagramConnection server = null;

        // Open the server
        while (true) {
            try {
                server = (UDPDatagramConnection)Connector.open("datagram://:" + PORT);
                connections.addElement(server);
                break;
            } catch (IOException ex) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex1) {
                    // TBD: what to do here?
                }
            }
        }

    }
    
    /**
     * Reads the UDP connection and updates the stored values for the key.
     * It is assumed that only one key is updated per UDP datagram.
     * This only handles float variables at the present time.
     */
    public synchronized void update()
    {
        Enumeration e = connections.elements();

        while( e.hasMoreElements() )
        {
            Object Enum = e.nextElement();
            if (Enum instanceof UDPDatagramConnection)
            {
            
                try {
                    UDPDatagramConnection conn = (UDPDatagramConnection)Enum;

                    Datagram g = conn.newDatagram(1024);
                    conn.receive(g);  

                    String message = g.readUTF();
                    StringTokenizer st = new StringTokenizer(message, " \n\r\t\f");
                    if (st.tokenCount == 3)
                    {
                        String cmd = st.nextToken();
                        if ((cmd.toLowerCase().compareTo("float") == 0))
                        {
                            String v = st.nextToken();
                            float f = Float.valueOf(st.nextToken()).floatValue();
                            newVariableValue(v, new Float(f));
                        }
                        
                        // TODO: If handling other data types than floats,
                        // those would be done here.
                    }
                    
                } catch (IOException z) {
                    // TBD: what to do?
                }
            }
        }
        
    }
    

}
