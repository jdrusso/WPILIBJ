/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file PIDTest.java
 * Tests the PID control loop components of the library
 * @author Ryan O'Meara
 */
public class PIDTest extends TestClass implements TestHarness {

    private DigitalInput qA;
    private DigitalInput qB;
    private Victor rightMotor;

    public String getName() {
        return "PID Test";
    }

    public void setup() {
        qA = new DigitalInput(QUADENCODERA);
        qB = new DigitalInput(QUADENCODERB);
        rightMotor = new Victor(ENDMOTORPORT);
    }

    public void teardown() {
        qA.free();
        qB.free();
        rightMotor.set(0.0);
        rightMotor.free();
    }

    {
         class PIDEncoder extends Encoder implements PIDSource {

                public PIDEncoder(DigitalInput a, DigitalInput b, boolean c) {
                    super(a, b, c);
                }

                public double pidGet() {
                    return -this.get();
                }
         }

        new Test("PID operation") {

            private PIDEncoder encoder4X;
            private PIDController controller;

            public void setup() {
                encoder4X = new PIDEncoder(qA, qB, true);
                
                //Create PIDController
                controller = new PIDController(.0016, 0.0, 0.0,
                        encoder4X,
                        rightMotor,
                        0.05);
            }

            public void teardown(){
                controller.disable();
                rightMotor.set(0);

                controller.free();
                encoder4X.free();
            }

            public void run() {
                encoder4X.reset();
                encoder4X.start();

                controller.setSetpoint(16200);

                controller.enable();

                //Look at results
                Timer.delay(12.0);

                assertEquals(-encoder4X.get(), 16200, 50);
            }
        };

        new Test("PIDController onTarget"){
             private PIDEncoder encoder4X;
             private PIDController controller;

            public void setup() {
                encoder4X = new PIDEncoder(qA, qB, true);
                //Create PIDController
                controller = new PIDController(.0016, 0.0, 0.0,
                        encoder4X,
                        rightMotor,
                        0.05);
            }
            
            public void teardown(){
                controller.disable();
                rightMotor.set(0);

                controller.free();
                encoder4X.free();
            }

            public void run(){
                encoder4X.reset();
                encoder4X.start();

                controller.setSetpoint(16200);
                controller.setTolerance(15.0);
                controller.setOutputRange(-0.5, 0.5);
                
                controller.enable();

                //Look at results
                Timer.delay(12.0);

                assertTrue(controller.onTarget());
            }
        };
    }
}
