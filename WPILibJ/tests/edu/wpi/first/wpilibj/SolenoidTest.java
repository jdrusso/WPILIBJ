/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file ColenoidTest.java
 * Tests the solenoid class using cross connected, voltage-divided solenoid output and digital input
 * @author Ryan O'Meara
 */
public class SolenoidTest extends TestClass implements TestHarness
{

    //Expected Values
    private static final double ONVALUE = 10.0;
    private static final double OFFVALUE = 0.0;
    private static final double THRESHOLD = 1.0;

    private Solenoid output;
    private AnalogChannel input;

    public String getName()
    {
        return "Solenoid Test";
    }

    public void setup()
    {
        output = new Solenoid(SOLENOIDPORT);
        input = new AnalogChannel(SOLCROSSCHANNEL);
    }

    public void teardown()
    {
        output.free();
        input.free();
    }


    {
        new Test("Off")
        {

            public void run()
            {
                output.set(false);
                Timer.delay(1.0);

                assertEquals(OFFVALUE,input.getVoltage(), THRESHOLD);
            }
        };

        new Test("On")
        {

            public void run()
            {
                output.set(true);
                Timer.delay(1.0);

                assertEquals(ONVALUE, input.getVoltage(), THRESHOLD);

            }
        };
    }
}
