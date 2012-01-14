/*
2
 * To change this template, choose Tools | Templates
3
 * and open the template in the editor.
4
 */
package edu.team2035.meta;
import java.util.Vector;
import java.util.Enumeration;
/**
 *
 * @author Robot
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