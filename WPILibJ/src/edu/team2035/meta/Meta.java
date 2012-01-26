/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST Team 2035, 2012. All Rights Reserved.                  */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.team2035.meta;
import java.util.Vector;
import java.util.Enumeration;
/**
 *
 * @author Team 2035 Programmers
 */
public class Meta  {
    private static Vector objects = new Vector( 10 , 1 );
    public static void addObject( MetaObject m ) {
        objects.addElement( m );
    }
    public static void update () {
        Enumeration e = objects.elements();
        MetaObject o;
        while( e.hasMoreElements() ){
            o = (MetaObject)e.nextElement();
            if (o instanceof Button) {
               Button b = (Button) o;
            b.update();
            }
        }
    }
}