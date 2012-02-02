/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import com.sun.squawk.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.SocketConnection;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.ServerSocketConnection;
import edu.wpi.first.wpilibj.DriverStationLCD;
import java.io.InputStream;
import java.io.InputStreamReader;

/** MetaTCPVariables
 * For receiving variables from the dashboard.
 *
 * @author Team 2035 Programmers
 */
public class MetaTCPVariables {

    Vector connections;
    Hashtable variables;
    int numberOfConnections;
    InputStream dashboardStream;
    InputStreamReader reader;
    BufferedReader buffRead;
    Thread connect;
    ServerSocketConnection server;
    SocketConnection socket;

    static final int PORT = 1130;  // TCP listen connection port
    
    /**
     * Starting the constructor causes it to accept new connections and
     * the open connections will be read for data.
     */
    public MetaTCPVariables()
    {
       server = null;
       connections = new Vector(); // Vector of open connections.
       variables = new Hashtable(); // Hastable of key/value pairs to store values
       numberOfConnections = 0;
       buffRead = null;
       
       connect = new Thread() {
           public void run() {
               acceptConnections();
           }
       };
       connect.start();
       
       new Thread() {
           public void run() {
               update();
//               try {
//                    //Thread.sleep(200);
//                } catch (InterruptedException ex1) {
//                    // TBD: what to do here?
//                }
           }
       }.start(); 
    }
    
    /** newVariableValue
     * Called internally to update the key pair in the Hashtable
     * @param s called the key, this is String such as "positionx"
     * @param o called the value, this is an Object that stores its content
     */
    private void newVariableValue(String s, Object o)
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
    public float getVariableFloatValue(String s)
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
    private void acceptConnections() {

        // Open the server
        while (true) {
            try {
                server = (ServerSocketConnection)Connector.open("socket://:" + PORT);
                System.out.println("Attempted to Connect");
                numberOfConnections++;
                break;
            } catch (IOException ex) {
                //ex.printStackTrace();
                try {
                    
                    Thread.sleep(2000);
                } catch (InterruptedException ex1) {
                    ex1.printStackTrace();
                }
            }
        }

        try {
            while (true) {
                // Wait for a connection
                socket = (SocketConnection) server.acceptAndOpen();
                System.out.println("Accepted and opened ;)");
                socket.setSocketOption(SocketConnection.KEEPALIVE, 1);
            }
        } catch (IOException ex) {
            System.out.println("MetaTCP: LOST SERVER!");
        }
        try {
            
            dashboardStream = socket.openInputStream();
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "Listening on " + server.getLocalAddress());
            System.out.println("InputStream established");
        } catch (IOException ex){
            System.out.println("input stream open failed - shit.");
        }
        DriverStationLCD.getInstance().updateLCD();
        connections.addElement(server);
        numberOfConnections++;
            
        buffRead = new BufferedReader(new InputStreamReader(dashboardStream));
                   
    }
    
    public int getConnections(){
        
        return numberOfConnections;
    }
    
    /**
     * Reads the UDP connection and updates the stored values for the key.
     * It is assumed that only one key is updated per UDP datagram.
     * This only handles float variables at the present time.
     */
    public void update()
    {
        //Enumeration e = connections.elements();
        //while( e.hasMoreElements() )
        //{
            //Object Enum = e.nextElement();
            //if (Enum instanceof SocketConnection)
            //{
            if(buffRead != null)
            {
                try {
                    
                    String message = buffRead.readLine();
                    
                    StringTokenizer st = new StringTokenizer(message, " \n\r\t\f");
                    
                    System.out.println(st.tokenCount);
                    if (st.tokenCount == 9)
                    {
//                        String cmd = st.nextToken();
//                        if ((cmd.toLowerCase().compareTo("float") == 0))
//                        {
//                            String v = st.nextToken();
//                            float f = Float.valueOf(st.nextToken()).floatValue();
//                            newVariableValue(v, new Float(f));
//                        }
                    
                        /**values are passed through UDP in the format
                         * (Distance, x1, y1, x2, y2, x3, y3, x4, y4)
                         * This handles that using the switch statement for each
                         * different value to store them in a hashtable
                         */
                        for (int i = 0; i < 9; i++)
                        {
                            String value = st.nextToken();
                            Float temp = Float.valueOf(value);
                            float f = Float.valueOf(value).floatValue();
                            switch (i)
                            {
                                case 0: newVariableValue("range", temp); break;
                                case 1: newVariableValue("x1", temp); break;
                                case 2: newVariableValue("y1", temp); break;
                                case 3: newVariableValue("x2", temp); break;
                                case 4: newVariableValue("y2", temp); break;
                                case 5: newVariableValue("x3", temp); break;
                                case 6: newVariableValue("y3", temp); break;
                                case 7: newVariableValue("x4", temp); break;
                                case 8: newVariableValue("y4", temp); break;
                            }
                        }
                                    
                            

                        
                        // TODO: If handling other data types than floats,
                        // those would be done here.
                    }
                    
                } catch (IOException z) {
                    // TBD: what to do?
                }
            }
            //}
        //}
        
    }
    

}
