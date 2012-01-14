/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file AnalogTriggerTest.java
 * Tests the analog trigger class using potentiometer and servo
 * @author Ryan O'Meara
 */
public class AnalogTriggerTest extends TestClass implements TestHarness
{
    //Expected values
    private static final double TOLERENCE = .0125;
    private static final double ZEROEXP = 0.5575;
    private static final double ONEEXP = 0.6750;
    private static final double TWOEXP = 0.7950;
    private static final double THREEEXP = 0.9200;
    private static final double FOUREXP = 1.0450;
    private static final double FIVEEXP = 1.1575;
    private static final double SIXEXP = 1.2800;
    private static final double SEVENEXP = 1.3925;
    private static final double EIGHTEXP = 1.5175;
    private static final double NINEEXP = 1.6325;
    private static final double TENEXP = 1.7525;

    private Servo output;
    private AnalogChannel chan;
    private AnalogTrigger trig;

    public String getName()
    {
        return "AnalogTrigger Test";
    }

    public void setup()
    {
        output = new Servo(GYROSERVO);
        chan = new AnalogChannel(POTCHANNEL);
        trig = new AnalogTrigger(chan);

        trig.setLimitsVoltage(ONEEXP, SEVENEXP);
        trig.setAveraged(false);
        trig.setAveraged(false);
        trig.setFiltered(false);
    }

    public void teardown()
    {
        output.free();
        chan.free();
        trig.free();
    }


    {
        new Test("Start state")
        {

            public void run()
            {
                output.set(0.5);
                Timer.delay(0.5);

                assertTrue(trig.getInWindow());
            }
        };

        new Test("Window 1")
        {

            public void run()
            {
                output.set(0.0);
                Timer.delay(0.5);

                assertTrue(!trig.getInWindow());

                output.set(0.5);
                Timer.delay(0.5);

                assertTrue(trig.getInWindow());
            }
        };

        new Test("Window 2")
        {

            public void run()
            {
                output.set(1.0);
                Timer.delay(0.5);

                assertTrue(!trig.getInWindow());

                output.set(0.5);
                Timer.delay(0.5);

                assertTrue(trig.getInWindow());

            }
        };

        new Test("Filtered/averaged exception"){
            boolean averaged, filtered;

            public void teardown(){
                trig.setAveraged(false);
                trig.setFiltered(false);
            }

            public void run(){
                trig.setAveraged(false);
                trig.setFiltered(false);

                //Should cause exception
                try{
                    trig.setAveraged(true);
                    trig.setFiltered(true);
                    assertFail("Analog Trigger did not throw exception when averaging and filtering both turned on");
                  }catch(Exception e){}

                trig.setAveraged(false);
                trig.setFiltered(false);

                //Should cause exception
                try{
                    trig.setFiltered(true);
                    trig.setAveraged(true);
                    assertFail("Analog Trigger did not throw exception when filtering and averaging both turned on");
                }catch(Exception e){}
            }
        };

        new Test("GetTriggeredState")
        {
            public void run()
            {
                output.set(1.0);
                Timer.delay(0.5);

                assertTrue(trig.getTriggerState());

                output.set(0.0);
                Timer.delay(0.5);

                assertTrue(!trig.getTriggerState());

            }
        };

        
    }
}
