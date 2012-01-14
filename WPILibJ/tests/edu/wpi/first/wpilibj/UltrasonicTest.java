/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file UltrasonicTest.java
 * Tests the ultrasonic class functionality, using pulses, round robin, and other features
 * @author Ryan O'Meara
 */
public class UltrasonicTest extends TestClass implements TestHarness
{

    public String getName()
    {
        return "Ultrasonic test";
    }


    {
        new Test("Operation")
        {

            private Ultrasonic ultra;

            public void setup()
            {
                ultra = new Ultrasonic(ULTRAPING, ULTRAECHO, Ultrasonic.Unit.kInches);
            }

            public void teardown()
            {
                ultra.free();
            }

            public void run()
            {

                Timer timer = new Timer();
                final int SAMPLE_SIZE = 20;
                double[] samples = new double[SAMPLE_SIZE];

                // make sure there is no data yet
                assertTrue(!ultra.isRangeValid());

                ultra.setAutomaticMode(true);
                timer.start();

                // wait for some ultrasonic data to become valid
                //TODO: this is returning valid ranges before it's ready!

                while (timer.get() < 0.5 && !ultra.isRangeValid())
                {
                    Timer.delay(0.005);
                }
                
                assertTrue(ultra.isRangeValid());

                // start reading ranges
                double minRange, maxRange;
                Timer.delay(1.0);
                minRange = maxRange = ultra.getRangeInches();
                for (int i = 0; i < SAMPLE_SIZE; i++)
                {
                    double range = ultra.getRangeInches();
                    samples[i] = range;
                    if (range > maxRange)
                    {
                        maxRange = range;
                    }
                    if (range < minRange)
                    {
                        minRange = range;
                    }
                    Timer.delay(0.5);
                }

                String output = "";

                if (Math.abs(maxRange - minRange) > 0.5)
                {
                    for (int i = 0; i < samples.length; i++)
                    {
                        output += "\t\t" + samples[i] + "\n";
                    }
                    assertFail("Min - Max spread too big, spread of: " + Math.abs(maxRange - minRange) + " Samples: \n" + output);
                }

                assertEquals(maxRange, 8.25, 0.25 + Math.abs(maxRange - minRange));

                // turn off round robin mode
                ultra.setAutomaticMode(false);

                // make sure no data valid after turning off round robin
                assertTrue(!ultra.isRangeValid());

                // Test a single ping
                timer.reset();
                ultra.ping();
                Timer.delay(1.0);
                assertTrue(ultra.isRangeValid());
            }
        };

        new Test("Fake signals")
        {

            Ultrasonic ultra;
            DigitalInput fakepingChan;
            DigitalOutput fakeechoChan;
            DigitalInput echoChan;
            DigitalOutput pingChan;
            FakeUltrasonic fakeultra;

            public void setup()
            {
                fakepingChan = new DigitalInput(CROSSCONNECTAP1);
                fakeechoChan = new DigitalOutput(CROSSCONNECTBP1);
                echoChan = new DigitalInput(CROSSCONNECTBP2);
                pingChan = new DigitalOutput(CROSSCONNECTAP2);
                fakeultra = new FakeUltrasonic(fakepingChan, fakeechoChan, echoChan);
                ultra = new Ultrasonic(pingChan, echoChan, Ultrasonic.Unit.kInches);
            }

            public void teardown()
            {
                fakeultra.free();
                ultra.free();
                fakepingChan.free();
                fakeechoChan.free();
                pingChan.free();
                echoChan.free();
            }

            public void run()
            {
                fakeultra.setDistance(10);
                fakeultra.start();
                ultra.ping();
                Timer.delay(1.0);
                assertEquals(10, ultra.getRangeInches(), 2);
            }
        };
    }
}
