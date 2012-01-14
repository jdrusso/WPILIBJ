/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file JaguarTest.java
 * Checks the set and get methods, and motor movement, of the victor object
 * @author Ryan O'Meara
 */
public class PWMTest extends TestClass implements TestHarness
{

    private GearTooth geartooth;
    private Jaguar motor;

    public String getName()
    {
        return "PWM Test";
    }

    public void setup()
    {
        geartooth = new GearTooth(GEARTOOTH, true);
        motor = new Jaguar(GTMOTORPORT);
    }

    public void teardown()
    {
        geartooth.free();
        motor.free();
    }


    {
        new Test("Test pwm setters and getters")
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
    }
}

