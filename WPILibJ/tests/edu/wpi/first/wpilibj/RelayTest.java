/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file RelayTest.java
 * Tests the rleay class for correct operation
 * @author Ryan O'Meara
 */
public class RelayTest extends TestClass implements TestHarness
{

    private Relay rel;
    private DigitalInput a;
    private DigitalInput b;

    public String getName()
    {
        return "Relay Test";
    }

    public void setup()
    {
        rel = new Relay(RELAYPORT, Relay.Direction.kBoth);
        a = new DigitalInput(RELAYCROSSA);
        b = new DigitalInput(RELAYCROSSB);
    }

    public void teardown()
    {
        rel.free();
        a.free();
        b.free();
    }

    private static boolean forward(DigitalInput a, DigitalInput b){
        return ((a.get()) && (!b.get()));
    }

    private static boolean reverse(DigitalInput a, DigitalInput b){
        return ((!a.get()) && (b.get()));
    }

    private static boolean off(DigitalInput a, DigitalInput b){
        return ((!a.get()) && (!b.get()));
    }

    {
        new Test("Both direction relay")
        {

            public void run()
            {
                //Both Directions
                rel.setDirection(Relay.Direction.kBoth);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));

                rel.set(Relay.Value.kForward);
                Timer.delay(.010);
                assertTrue(forward(a, b));

                rel.set(Relay.Value.kReverse);
                Timer.delay(.010);
                assertTrue(reverse(a, b));

                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));

                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);

                assertTrue(off(a, b));

                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kForward);
                Timer.delay(.010);
                assertTrue(forward(a, b));

                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kForward);
                Timer.delay(.010);
                assertTrue(forward(a, b));

                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kReverse);
                Timer.delay(.010);
                assertTrue(reverse(a, b));

                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kReverse);
                Timer.delay(.010);
                assertTrue(reverse(a, b));
            }

        };

        new Test("Forward direction relay")
        {

            public void run()
            {
                //Direction Change
                rel.setDirection(Relay.Direction.kForward);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));

                rel.set(Relay.Value.kForward);
                Timer.delay(.010);
                assertTrue(forward(a, b));

                rel.set(Relay.Value.kOn);
                Timer.delay(.010);
                assertTrue(forward(a, b));

                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));

                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));

                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kForward);
                Timer.delay(.010);

                assertTrue(forward(a, b));

                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kForward);
                Timer.delay(.010);

                assertTrue(forward(a, b));

                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kOn);
                Timer.delay(.010);
                assertTrue(forward(a, b));

                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kForward);
                rel.set(Relay.Value.kOn);
                Timer.delay(.010);
                assertTrue(forward(a, b));
            }

        };

         new Test("Reverse direction relay")
        {

            public void run()
            {
                //Direction Change
                rel.setDirection(Relay.Direction.kReverse);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));


                rel.set(Relay.Value.kReverse);
                Timer.delay(.010);
                assertTrue(reverse(a, b));


                rel.set(Relay.Value.kOn);
                Timer.delay(.010);
                assertTrue(reverse(a, b));

                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));


                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kOff);
                Timer.delay(.010);
                assertTrue(off(a, b));

                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kReverse);
                Timer.delay(.010);
                assertTrue(reverse(a, b));

                rel.set(Relay.Value.kOn);
                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kReverse);
                Timer.delay(.010);
                assertTrue(reverse(a, b));

                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kOn);
                Timer.delay(.010);
                assertTrue(reverse(a, b));

                rel.set(Relay.Value.kOff);
                rel.set(Relay.Value.kReverse);
                rel.set(Relay.Value.kOn);
                Timer.delay(.010);
                assertTrue(reverse(a, b));

            }

        };

        new Test("Exceptions"){
          public void run(){
                rel.setDirection(Relay.Direction.kBoth);

                try{
                    rel.set(Relay.Value.kOn);
                    assertFail("Both direction relay did not throw exception when set to \"On\"");
                }catch(Exception e){}

                rel.setDirection(Relay.Direction.kForward);

                try{
                    rel.set(Relay.Value.kReverse);
                    assertFail("Forward direction relay did not throw exception when set to \"Reverse\"");
                }catch(Exception e){}

                rel.setDirection(Relay.Direction.kReverse);
                
                try{
                    rel.set(Relay.Value.kForward);
                    assertFail("reverse direction relay did not throw exception when set to \"Forward\"");
                }catch(Exception e){}
          }
        };
    }
}

