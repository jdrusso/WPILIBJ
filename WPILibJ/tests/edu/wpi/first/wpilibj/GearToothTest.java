/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file GearToothTest.java
 * This tests that the gear tooth sensor is counting passing gear teeth, and
 * is doing so consistently
 * @author Ryan O'Meara
 */
public class GearToothTest extends TestClass implements TestHarness{

    private final int EXPECTEDCOUNT = 44;
    private GearTooth gear;
    private Victor motor;

    public String getName()
    {
        return "GearTooth Test";
    }

    public void setup()
    {

        gear = new GearTooth(GEARTOOTH, true);
        motor = new Victor(GTMOTORPORT);
    }

    public void teardown()
    {
        gear.free();
        motor.free();
    }

    
    {
        new Test("Operations")
        {

            public void run()
            {


                //Start/Reset the gt sensor
                gear.start();
                gear.reset();

                //Start the motor moving
                motor.set(0.5);

                //Wait
                Timer.delay(3.0);

                //Check to see if it registered
                assertTrue(Math.abs(gear.get()) > 10);

                motor.set(0);

                //test variables
                int first, second, third;

                //Start the motor moving
                motor.set(0.5);

                //Wait to get to speed
                Timer.delay(2.0);

                //Run first test
                //Start/Reset the gt sensor
                gear.reset();

                //Wait
                Timer.delay(2.0);

                //Record count
                first = gear.get();

                //Run second test
                //Start/Reset the gt sensor
                gear.reset();

                //Wait
                Timer.delay(2.0);

                //Record count
                second = gear.get();

                //Run third test
                //Start/Reset the gt sensor
                gear.reset();

                //Wait
                Timer.delay(2.0);

                //Record count
                third = gear.get();

                //Check to see if passed
                assertEquals(second, first, 2);
                assertEquals(second, third, 2);
                assertEquals(third, first, 2);


                //Go in reverse and see if sensor counts backward
                gear.reset();

                Timer.delay(2.0);

                //Check to see if negitive count registered
                assertTrue(gear.get() < 0);


                //Check for count correctness

                //Reverse motor
                motor.set(-0.5);

                //Wait to get to speed
                Timer.delay(2.0);

                //Reset counter
                gear.reset();

                //Count for two seconds
                Timer.delay(2.0);

                //See if count matches
                assertEquals(gear.get(), EXPECTEDCOUNT, 3);

                //Stop motor and sensor
                motor.set(0);
                gear.stop();
            }
        };
    }
}
