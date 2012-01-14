/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file JaguarTest.java
 * Checks the set and get methods, and motor movement, of the victor object
 * @author Ryan O'Meara
 */
public class RobotDriveTest extends TestClass implements TestHarness
{
    private Jaguar motor;
    private Victor motor2;

    private GearTooth gear;
    private Encoder encode;

    private RobotDrive testDrive;

    public String getName()
    {
        return "RobotDrive Test";
    }

    public void setup()
    {
        motor = new Jaguar(GTMOTORPORT);
        motor2 = new Victor(ENDMOTORPORT);
        gear = new GearTooth(GEARTOOTH,true);
        encode = new Encoder(QUADENCODERA, QUADENCODERB, false);

        testDrive = new RobotDrive(motor,motor2);
        testDrive.setSensitivity(1.0);
    }

    public void teardown()
    {
        motor.free();
        motor2.free();
        gear.free();
        encode.free();
        testDrive.free();
    }


    {
        new Test("Drive"){
            public void run(){
                gear.start();
                encode.start();
                testDrive.drive(1.0, 0.0);

                Timer.delay(5.0);

                testDrive.drive(0.0,0.0);

                assertTrue((encode.get()!=0)&&(gear.get()!=0));

                gear.stop();
                encode.stop();
            }
        };

    }
}


