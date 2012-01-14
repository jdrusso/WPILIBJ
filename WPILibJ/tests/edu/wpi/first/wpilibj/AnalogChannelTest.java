/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file AnalogChannelTest.java
 * Tests the analog channel class using potentiometer and servo
 * @author Ryan O'Meara
 */
public class AnalogChannelTest extends TestClass implements TestHarness
{
    //Expected values
    public static final double TOLERANCE = .05;
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
    private final double ACTUALVOLTAGE = 5.0;
    
    private Servo output;
    private AnalogChannel input;
    private AnalogChannel channel;
    private AnalogModule module;

    private int averageBits;
    private int oversampleBits;

    public void setup()
    {
        output = new Servo(GYROSERVO);
        input = new AnalogChannel(POTCHANNEL);
        channel = new AnalogChannel(CROSSANALOGCHANNEL);
        module = channel.getModule();
        averageBits = channel.getAverageBits();
        oversampleBits = channel.getOversampleBits();
        Timer.delay(3.0);
    }

    public void teardown()
    {
        channel.setOversampleBits(oversampleBits);
        channel.setAverageBits(averageBits);
        output.free();
        input.free();
        channel.free();
        module.free();
    }

    public String getName()
    {
        return "AnalogChannel Test";
    }


    {
        new Test("Check baseline voltages")
        {

            public void run()
            {
                double voltage = channel.getVoltage();
                double averageVoltage = channel.getAverageVoltage();
                assertEquals(voltage, ACTUALVOLTAGE, TOLERANCE);
                assertEquals(averageVoltage, ACTUALVOLTAGE, TOLERANCE);

                voltage = module.getVoltage(CROSSANALOGCHANNEL);
                averageVoltage = module.getAverageVoltage(CROSSANALOGCHANNEL);
                assertEquals(voltage, ACTUALVOLTAGE, TOLERANCE);
                assertEquals(averageVoltage, ACTUALVOLTAGE, TOLERANCE);
            }
        };

        new Test("Check average bits")
        {

            public void run()
            {
                channel.setAverageBits(1);

                double voltage = channel.getVoltage();
                double averageVoltage = channel.getAverageVoltage();
                assertEquals(voltage, ACTUALVOLTAGE, TOLERANCE);
                assertEquals(averageVoltage, ACTUALVOLTAGE, TOLERANCE);

                channel.setAverageBits(10);

                voltage = channel.getVoltage();
                averageVoltage = channel.getAverageVoltage();
                assertEquals(voltage, ACTUALVOLTAGE, TOLERANCE);
                assertEquals(averageVoltage, ACTUALVOLTAGE, TOLERANCE);

                //TODO: this should give an error
                channel.setAverageBits(100);

                voltage = channel.getVoltage();
                averageVoltage = channel.getAverageVoltage();
                assertEquals(voltage, ACTUALVOLTAGE, TOLERANCE);
                assertEquals(averageVoltage, ACTUALVOLTAGE, TOLERANCE);
            }
        };

        new Test("Check oversample bits")
        {

            public void run()
            {
                output.set(0.5);
                Timer.delay(1.0);
                assertEquals(input.getVoltage(), FIVEEXP, TOLERANCE);

                input.setOversampleBits(1);
                Timer.delay(0.5);
                assertEquals(input.getVoltage(), FIVEEXP, TOLERANCE);

                input.setOversampleBits(10);
                Timer.delay(0.5);
                assertEquals(input.getVoltage(), FIVEEXP, TOLERANCE);

                input.setOversampleBits(100);
                Timer.delay(0.5);
                assertEquals(input.getVoltage(), FIVEEXP, TOLERANCE);

                output.set(0.0);
                Timer.delay(0.5);
                assertEquals(input.getVoltage(), ZEROEXP, TOLERANCE);

                output.set(0.5);
                input.setOversampleBits(1000);
                Timer.delay(0.5);
                assertEquals(input.getVoltage(), FIVEEXP, TOLERANCE);
            }
        };
    }
}

