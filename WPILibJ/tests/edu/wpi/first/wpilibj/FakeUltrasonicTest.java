/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file FakeEncoderTest.java
 * Test the fake encoder classes to verify they are valid for future test use
 * @author Ryan O'Meara
 */
public class FakeUltrasonicTest extends TestClass implements TestHarness
{

    private FakeUltrasonic fakeUltra;
    private DigitalOutput ping;

    public String getName()
    {
        return "FakeUltrasonic Test";
    }

    public void setup()
    {
        fakeUltra = new FakeUltrasonic(CROSSCONNECTAP2, CROSSCONNECTBP2, CROSSCONNECTBP1);
        ping = new DigitalOutput(CROSSCONNECTAP1);
    }

    public void teardown()
    {
        ping.free();
        fakeUltra.free();
    }


    {
        new Test("Operation")
        {

            public void run()
            {
                fakeUltra.setDistance(10);
                fakeUltra.start();

                Timer.delay(.1);
                ping.pulse(10 * 1e-6);

                fakeUltra.complete();

                //Wait some time
                Timer.delay(1.0);

                //Check counter to see if period matches
                assertEquals(fakeUltra.getDistanceInches(), fakeUltra.getMeasuredDistance(), fakeUltra.tolInches());

                fakeUltra.setDistance(25);
                fakeUltra.start();

                Timer.delay(.1);
                ping.pulse(10 * 1e-6);

                fakeUltra.complete();

                //Wait some time
                Timer.delay(1.0);

                //Check counter to see if period matches
                assertEquals(fakeUltra.getDistanceInches(), fakeUltra.getMeasuredDistance(), fakeUltra.tolInches());
            }
        };
    }
}
