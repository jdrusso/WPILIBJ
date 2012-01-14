/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file GyroTest.java
 * Tests the gyro's heading tolerence using a servo
 * @author Ryan O'Meara
 */
public class GyroTest extends TestClass implements TestHarness
{

    private final int GYROTOL = 5;
    private final double SERVOSTEP = .004;
    private final double DEGREESSEC = 80.0;

    private Servo servo;
    private Gyro gyro;

    public String getName()
    {
        return "Gyro Test";
    }

    public void setup()
    {
        servo = new Servo(GYROSERVO);
        gyro = new Gyro(GYROSLOT, GYROCHANNEL);
    }

    public void teardown()
    {
        servo.free();
        gyro.free();
    }


    {
        new Test("Drift")
        {

            public void run()
            {
                servo.set(0.0);
                Timer.delay(3.0);
                //Check for drift
                gyro.reset();
                Timer.delay(0.1);

                int i = 0;

                while ((i < 10) && (gyro.getAngle() < 3.0))
                {
                    Timer.delay(5.0);
                    i++;
                }

                assertEquals(0.0, gyro.getAngle(), GYROTOL);
            }
        };

        new Test("Heading changes")
        {

            public void run()
            {
                //Test Heading Changes
                gyro.reset();
                Timer.delay(.1);
                for (double x = 0.0; x <= 1.0; x += SERVOSTEP)
                {
                    servo.set(x);
                    Timer.delay(1.0 / DEGREESSEC);
                }
                assertEquals(165.0, gyro.getAngle(), GYROTOL);

                Timer.delay(2.0);

                // now turn back to zero
                for (double x = 1.0; x >= 0.0; x -= SERVOSTEP)
                {
                    servo.set(x);
                    Timer.delay(1.0 / DEGREESSEC);
                }
                assertEquals(0.0, gyro.getAngle(), GYROTOL);
            }
        };
    }
}
