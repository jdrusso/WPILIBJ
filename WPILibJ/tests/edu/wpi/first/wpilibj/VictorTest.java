/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file VictorTest.java
 * Checks the set and get methods, and motor movement, of the victor object
 * @author Ryan O'Meara
 */
public class VictorTest extends TestClass implements TestHarness{

    private Victor motor;
    private Encoder rotate;

    public String getName()
    {
        return "Victor Test";
    }

    public void setup()
    {
        rotate = new Encoder(QUADENCODERA, QUADENCODERB, false);
        motor = new Victor(ENDMOTORPORT);
    }

    public void teardown()
    {
        rotate.free();
        motor.free();
    }


    {
        new Test("Set and get")
        {

            public void run()
            {
                //Set victor to quarter speed
                motor.set(0.25);
                Timer.delay(2.0);

                //check to see if victor passed or failed
                assertEquals(motor.get(), 0.25, 0.008);

                //Continue test
                motor.set(-0.25);
                Timer.delay(2.0);
                assertEquals(motor.get(), -0.25, 0.008);

                motor.set(0);
                Timer.delay(1.0);

                //If final set/get test works, then test passed
                assertEquals(motor.get(), 0, 0.008);

                motor.set(0);
            }
        };

        new Test("Verify movement")
        {

            public void run()
            {
                //Set victor to quarter speed
                motor.set(0.25);
                Timer.delay(2.0);

                //check to see if victor passed or failed
                assertEquals(motor.get(), 0.25, 0.008);

                //Continue test
                motor.set(-0.25);
                Timer.delay(2.0);
                assertEquals(motor.get(), -0.25, 0.008);

                motor.set(0);
                Timer.delay(1.0);

                //If final set/get test works, then test passed
                assertEquals(motor.get(), 0, 0.008);

                motor.set(0);

                //Start/Reset encoder
                rotate.start();
                rotate.reset();

                //Start motor
                motor.set(0.5);

                //Wait
                Timer.delay(5.0);

                //Check to see if encoder moved
                assertTrue(Math.abs(rotate.get()) > 100);

                motor.set(0);
                rotate.reset();
                rotate.stop();
            }
        };
    }
}
