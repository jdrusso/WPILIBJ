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
public class AccelerometerTest extends TestClass implements TestHarness
{
    //Expected values
    private final double ONEEXPECT = 8.730174038461547E-4;
    private final double HALFEXPECT = -3.637506249999993E-4;
    private final double ZEROEXPECT = -0.0010068700000000002;
    private final double TOL = 0.25E-4;

    private Servo servo;

    public void setup(){
        servo = new Servo(ACCELSERVO);
    }

    public void teardown(){
        servo.free();
    }

    public String getName()
    {
        return "Accelerometer Test";
    }

    public boolean inTolerance(double expected, double actual, double tolerence){
        return (Math.abs(tolerence) <= Math.abs(expected-actual));
    }


    {
        new Test("Return expected values")
        {
            private Accelerometer accel;

            public void setup(){
                accel = new Accelerometer(ACCELSLOT, ACCELCHANNEL);
                accel.setZero(2.5);
                accel.setSensitivity(312);
            }

            public void teardown(){
                accel.free();
            }

            public void run(){
                int i = 0;

                servo.set(1.0);
                Timer.delay(2.0);
                
                while((i< 5) && (!inTolerance(accel.getAcceleration(), ONEEXPECT, TOL)))
                {
                    Timer.delay(5.0);
                    i++;
                }

                assertEquals(ONEEXPECT, accel.getAcceleration(), TOL);
                i=0;

                servo.set(0.5);
                Timer.delay(2.0);
                while((i< 5) && (!inTolerance(accel.getAcceleration(), HALFEXPECT, TOL)))
                {
                    Timer.delay(5.0);
                    i++;
                }

                assertEquals(HALFEXPECT, accel.getAcceleration(), TOL);
                i=0;

                servo.set(0.0);
                Timer.delay(2.0);
                while((i< 5) && (!inTolerance(accel.getAcceleration(), ZEROEXPECT, TOL)))
                {
                    Timer.delay(5.0);
                    i++;
                }

                assertEquals(ZEROEXPECT, accel.getAcceleration(), TOL);
            }
        };

        new Test("Constructors"){
            Accelerometer acccon;
            AnalogChannel chan;

            public void teardown(){
                try{
                    acccon.free();
                    chan.free();
                }catch(Exception e){}
            }

            public void run(){
                servo.set(0.0);
                
                acccon = new Accelerometer(ACCELSLOT, ACCELCHANNEL);
                acccon.setZero(2.5);
                acccon.setSensitivity(312);

                Timer.delay(5.0);

                double channelslot = acccon.getAcceleration();

                acccon.free();

                chan = new AnalogChannel(ACCELSLOT, ACCELCHANNEL);

                acccon = new Accelerometer(chan);
                acccon.setZero(2.5);
                acccon.setSensitivity(312);

                Timer.delay(5.0);
                
                double anachan = acccon.getAcceleration();

                assertEquals(channelslot, anachan, 0.1);
            }
        };


    }
}

