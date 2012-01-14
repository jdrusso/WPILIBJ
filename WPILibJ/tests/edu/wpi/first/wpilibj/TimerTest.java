/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file TimerTest.java
 * Tests the timer class functionality
 * @author Ryan O'Meara
 */
public class TimerTest extends TestClass implements TestHarness
{
    private Timer timer;

    public String getName()
    {
        return "Timer Test";
    }

    public void setup(){
        timer = new Timer();
    }

    public void teardown(){
        timer.stop();
    }
    
    {
        new Test("Single wait"){
            public void teardown(){
                timer.stop();
            }

            public void run(){
                timer.start();
                Timer.delay(1.0);

                assertEquals(1, timer.get(), .01);
            }
        };

        new Test("Multiple waits"){
            public void teardown(){
                timer.stop();
            }

            public void run(){
                // start timer and compare it with the Wait times
                timer.start();
                Timer.delay(5.0);
                for (double i = 1.0; i < 10.0; i++)
                {
                    assertEquals((i * 5.003), timer.get(), 0.01);
                    Timer.delay(5.0);
                }
            }
        };

        new Test("Stop"){
            public void teardown(){
                timer.stop();
            }

            public void run(){
                // Stop timer and make sure it stays stopped
                timer.stop();
                double savedTime = timer.get();
                double stoppedTime;
                for (int i = 0; i < 2; i++)
                {
                    if ((stoppedTime = timer.get()) != savedTime)
                    {
                        assertFail("Timer did not stop.  Saved Time: " + savedTime + ", current: " + stoppedTime);
                    }
                    Timer.delay(1.0);
                }
            }
        };

        new Test("Restart"){
            public void teardown(){
                timer.stop();
            }

            public void run(){
                timer.start();
                double savedTime = timer.get();
                Timer.delay(1.0);

                if(savedTime == timer.get()){
                    assertFail("Timer did not start");
                }
                
                timer.stop();

                Timer.delay(1.0);

                // Restart timer and make sure it restarts
                timer.start();
                for (double i = 0; i < 3; i++)
                {
                    double currentTime = timer.get();
                    assertEquals(savedTime + i, currentTime, 0.02);
                    Timer.delay(1.0);
                }
            }
        };

        new Test("Reset"){
            public void teardown(){
                timer.stop();
            }

            public void run(){
                timer.start();
                double savedTime = timer.get();
                Timer.delay(1.0);

                if(savedTime == timer.get()){
                    assertFail("Timer did not start");
                }

                timer.stop();

                Timer.delay(1.0);

                // Reset timer and make sure it goes back to 0
                timer.reset();

                if (Math.abs(savedTime = timer.get()) > 10)
                {
                    assertFail("Timer did not reset correctly, expected: 0 got " + savedTime);
                }
            }
        };
    }
}
