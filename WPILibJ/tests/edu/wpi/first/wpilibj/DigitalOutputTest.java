/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file DigitalOutputTest.java
 * Tests the digitl output class functionality, using pulses
 * @author Ryan O'Meara
 */
public class DigitalOutputTest extends TestClass implements TestHarness
{

    private DigitalOutput digOut;
    private Counter count;

    public String getName()
    {
        return "DigitalOutput Test";
    }

    public void setup()
    {
        digOut = new DigitalOutput(CROSSCONNECTAP1);
        count = new Counter(CROSSCONNECTAP2);
    }

    public void teardown()
    {
        digOut.free();
        count.free();
    }


    {
        new Test("Count pulses")
        {

            public void run()
            {
                //Count to watch for/expect
                final int MAXCOUNT = 500;

                //Alot objects

                //start the counter
                count.reset();
                count.start();

                //send specified number of pulses
                for (int i = 0; i < MAXCOUNT; i++)
                {
                    digOut.set(true);
                    Timer.delay(.01);
                    digOut.set(false);
                    Timer.delay(.01);
                }

                //Check that counter received all pulses
                assertEquals(count.get(), MAXCOUNT);
                count.stop();

            }
        };

        new Test("Double allocation exception"){
            private DigitalOutput out2;

            public void teardown(){
                try{
                    out2.free();
                }catch(Exception e){}
            }

            public void run(){
                try{
                    out2 = new DigitalOutput(CROSSCONNECTAP1);
                    out2.free();
                    assertFail("Did not throw exception on double alloc");
                }catch(Exception e){}
            }
        };
    }
}
