/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.util.SquawkHashtable;
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
import javax.microedition.io.*;

/** MetaTCPVariables
 * For receiving variables from the dashboard.
 *
 * @author Team 2035 Programmers
 */
public class MetaTCPVariables {

    Vector connections;
    SquawkHashtable variables;
    int numberOfConnections;
    InputStream dashboardStream;
    InputStreamReader reader;
    BufferedReader buffRead;
    Thread connect;
    ServerSocketConnection server;
    UDPDatagramConnection server2;
    public static double[] dataMessage = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    
    SocketConnection socket;
    
    InputStreamReader isr;
    private int lastByte;
    private boolean updaterunning;

    public static final int PORT = 1130;  // TCP listen connection port
    
    public float x1, x2, x3, x4, y1, y2, y3, y4, range;
    
    /**
     * Starting the constructor causes it to accept new connections and
     * the open connections will be read for data.
     */
    public MetaTCPVariables()
    {
       server = null;
       server2 = null;
       connections = new Vector(); // Vector of open connections.
       variables = new SquawkHashtable(); // Hastable of key/value pairs to store values
       numberOfConnections = 0;
       buffRead = null;
       isr = null;
       updaterunning = false;
       x1 = x2 = x3 = x4 = y1 = y2 = y3 = y4 = range = (float)0.0;
       
       connect = new Thread() {
           public void run() {
               acceptConnections();
           }
       };
       connect.start();
       
//       new Thread() {
//           public void run() {
//               update();
//               try {
//                    Thread.sleep(2);
//                    System.out.println("update sleep");
//                } catch (InterruptedException ex1) {
//                    System.out.println("update except");
//                }
//           }
//       }.start(); 
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
    public Object getVariableValue(String s)
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
//        Object o = variables.get(s);
//        if (o instanceof Float)
//        {
//            return ((Float)o).floatValue();
//        }
        Float o = (Float)variables.get(s);
        if (o != null)
            return o.floatValue();
        return (float)-99.0;
    }

    /**
     * Accepts UDP connections to the robot on the specified port.
     */
    private synchronized void acceptConnections() 
    {
        System.out.println("acceptConnections");
        // Open the server
        while (true) {
            try {
                server = (ServerSocketConnection) Connector.open("socket://:" + PORT);
                //server2 = (UDPDatagramConnection) Connector.open("datagram://:1131");
                System.out.println("Connector open");
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
            //while (true) {
                // Wait for a connection
                socket = (SocketConnection) server.acceptAndOpen();

                //socket.setSocketOption(SocketConnection.LINGER, 0);
                //socket.setSocketOption(SocketConnection.KEEPALIVE, 100);
                //socket.setSocketOption(SocketConnection.RCVBUF, 4096);
                System.out.println("socket option");
                
            //}
        } catch (IOException ex) {
            System.out.println("MetaTCP: LOST SERVER!");
            ex.printStackTrace();
        }
        try {
            
            dashboardStream = socket.openInputStream();
            System.out.println("Input stream");
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "Listening on " + server.getLocalAddress());
            System.out.println("InputStream established");
        } catch (IOException ex){
            System.out.println("Input stream fail");
        }
        DriverStationLCD.getInstance().updateLCD();
        connections.addElement(server);
        numberOfConnections++;
        
        isr = new InputStreamReader(dashboardStream);
        buffRead = new BufferedReader(isr);
        
        
        System.out.println("buffRead created");
        if (updaterunning == false)
        {
            updaterunning = true;
            new Thread() {
            public void run() {
                while(true) {
                    update();
    //               try {
    //                    Thread.sleep(2);
    //                    System.out.println("update sleep");
    //                } catch (InterruptedException ex1) {
    //                    System.out.println("update except");
    //                }
                    //System.out.println("update 2");
                    }
                }
            }.start(); 
        }
        
    }
    
    public int getConnections(){
        //System.out.println("number of connections = " + numberOfConnections);        
        return numberOfConnections;
    }
    
    public int getCount() 
    {
        return variables.size();
    }
    
    public float getrange()
    {
        return this.range;
        
    }
    
    public float getx2()
    {
        return this.x2;
    }
    
    /**
     * Reads the UDP connection and updates the stored values for the key.
     * It is assumed that only one key is updated per UDP datagram.
     * This only handles float variables at the present time.
     */
    public void update()
    {
        //System.out.println("update");
        //Enumeration e = connections.elements();
        //while( e.hasMoreElements() )
        //{
            //Object Enum = e.nextElement();
            //if (Enum instanceof SocketConnection)
            //{
        if(buffRead != null)
        {
            try 
            {
                while (buffRead.ready())
                {
                //System.out.println("buffRead != null");
                    
                    String message;
                    //String message = readString();
                    //System.out.println("readLine");
                    while ((message = buffRead.readLine()) != null) 
                    {
                        StringTokenizer st = new StringTokenizer(message, " \n\r\t\f");
                        System.out.println("ST " + st.countTokens() + " " + message);
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
                                //System.out.println("for loop " + i + " " + f);
                                switch (i)
                                {
                                    case 0: 
                                        //newVariableValue("range", temp);
                                        //synchronized (this) {
                                        if (f < 5000)
                                            dataMessage[0] = f;
                                        else if (f > 5000)
                                            dataMessage[0] = 0;
                                        //}
                                        System.out.println("for loop " + i + " " + f + " " + dataMessage[0]); 
                                        break;
                                    case 1: 
                                        //newVariableValue("x1", temp); 
                                        dataMessage[1] = f; 
                                        break;
                                    case 2: 
                                        //newVariableValue("y1", temp); 
                                        dataMessage[2] = f;
                                        break;
                                    case 3: 
                                        //newVariableValue("x2", temp); 
                                        dataMessage[3] = f; 
                                        break;
                                    case 4: 
                                        //newVariableValue("y2", temp); 
                                        dataMessage[4] = f; 
                                        break;
                                    case 5: 
                                        //newVariableValue("x3", temp); 
                                        dataMessage[5] = f; 
                                        break;
                                    case 6: 
                                        //newVariableValue("y3", temp); 
                                        dataMessage[6] = f; 
                                        break;
                                    case 7: 
                                        //newVariableValue("x4", temp); 
                                        dataMessage[7] = f; 
                                        break;
                                    case 8: 
                                        //newVariableValue("y4", temp); 
                                        dataMessage[8] = f; 
                                        break;
                                }
                            }
                        }
                        
                        // TODO: If handling other data types than floats,
                        // those would be done here.
                    }
                }
                    
            } catch (IOException z) {
                // TBD: what to do?
                System.out.println("update fail");
            }
        }
            //}
        //}
        
    }

}
