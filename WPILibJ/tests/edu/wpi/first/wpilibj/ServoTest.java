/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file CounterTest.java
 * Tests the counter class functionality, using pulses
 * @author Ryan O'Meara
 */
public class ServoTest extends TestClass implements TestHarness
{

    private Servo serv;
    private Gyro pointer;
    private AnalogChannel potent;

    public String getName()
    {
        return "Servo Test";
    }

    public void setup()
    {
        serv = new Servo(GYROSERVO);
        pointer = new Gyro(GYROSLOT, GYROCHANNEL);
        potent = new AnalogChannel(POTCHANNEL);
    }

    public void teardown()
    {
        serv.free();
        pointer.free();
        potent.free();
    }


    {
        new Test("SetAngle")
        {

            public void run()
            {

                Timer.delay(3.0);

                pointer.reset();

                serv.setAngle(0);
                Timer.delay(3.0);
                double potstart = potent.getVoltage();

                for (int i = 1; i <= 15; i++)
                {
                    serv.setAngle(i);
                    Timer.delay(.5);
                }

                Timer.delay(3.0);

                double potend = potent.getVoltage();

                double diff = potend - potstart;

                double expected = potstart + diff;

                for (int i = 15; i <= 30; i++)
                {
                    serv.setAngle(i);
                    Timer.delay(.5);
                }

                assertEquals(expected, potent.getVoltage(), 0.125);

                serv.setAngle(0);
            }
        };
    }
}
