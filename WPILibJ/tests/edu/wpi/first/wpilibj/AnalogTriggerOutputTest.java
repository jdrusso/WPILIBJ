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
public class AnalogTriggerOutputTest extends TestClass implements TestHarness
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
    private AnalogTriggerOutput trigout;

    public String getName()
    {
        return "AnalogTriggerOutput Test";
    }

    public void setup()
    {
        output = new Servo(GYROSERVO);
        chan = new AnalogChannel(POTCHANNEL);
        trig = new AnalogTrigger(chan);
        trigout =new AnalogTriggerOutput(trig,AnalogTriggerOutput.Type.kInWindow);
    }

    public void teardown()
    {
        output.free();
        chan.free();
        trig.free();
        trigout.free();
    }


    {
        new Test("State")
        {

            public void run()
            {
                trig.setLimitsVoltage(ONEEXP, SEVENEXP);
                trig.setAveraged(false);

                output.set(0.5);
                Timer.delay(0.5);

                assertTrue(trigout.get());
            }
        };

        new Test("Window 1")
        {

            public void run()
            {
                output.set(0.0);
                Timer.delay(0.5);

                assertTrue(!trigout.get());

                output.set(0.5);
                Timer.delay(0.5);

                assertTrue(trigout.get());
            }
        };

        new Test("Window 2")
        {

            public void run()
            {
                output.set(1.0);
                Timer.delay(0.5);

                assertTrue(!trigout.get());

                output.set(0.5);
                Timer.delay(0.5);

                assertTrue(trigout.get());

            }
        };

        new Test("Exception"){
          public void run(){
              
            try{
                trigout.get();
            }catch(Exception e){assertFail("Get for in window cause exception");}

            trigout.free();
            trigout = new AnalogTriggerOutput(trig, AnalogTriggerOutput.Type.kTypeState);

            try{
                trigout.get();
            }catch(Exception e){assertFail("Get for type state cause exception");}

            trigout.free();
            trigout = new AnalogTriggerOutput(trig, AnalogTriggerOutput.Type.kFallingPulse);

            try{
                trigout.get();
                assertFail("Falling pulse did not cause exception");
            }catch(Exception e){trigout.free();
            trigout = new AnalogTriggerOutput(trig, AnalogTriggerOutput.Type.kInWindow);}

            trigout.free();
            trigout = new AnalogTriggerOutput(trig, AnalogTriggerOutput.Type.kRisingPulse);

            try{
                trigout.get();
                assertFail("Rising pulse did not cause exception");
            }catch(Exception e){trigout.free();
            trigout = new AnalogTriggerOutput(trig, AnalogTriggerOutput.Type.kInWindow);}

            trigout.free();
            trigout = new AnalogTriggerOutput(trig, AnalogTriggerOutput.Type.kInWindow);

          }
        };

    }
}

