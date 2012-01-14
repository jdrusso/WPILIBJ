/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file QuadEncoderTest.java
 * Tests various functionality points of the quad encoders
 * @author Ryan O'Meara
 */
public class EncoderTest extends TestClass implements TestHarness
{

    private final int EXPECTED_TICKS = 16200;

    private DigitalInput qA;
    private DigitalInput qB;
    private Counter count;
    private Victor motor;

    private Encoder encoder4X;
    private Encoder encoder2X;
    private Encoder encoder1X;

    private Counter count2;


    public String getName()
    {
        return "Encoder Test";
    }

    public void setup()
    {
        qA = new DigitalInput(QUADENCODERA);
        qB = new DigitalInput(QUADENCODERB);
        count = new Counter(qA);
        motor = new Victor(ENDMOTORPORT);
        count2 = new Counter(qB);

        encoder4X = new Encoder(qA, qB, true);
        encoder2X = new Encoder(qA, qB, true, CounterBase.EncodingType.k2X);
        encoder1X = new Encoder(qA, qB, true, CounterBase.EncodingType.k1X);
    }

    public void teardown()
    {
        qA.free();
        qB.free();
        count.free();
        motor.free();
        count2.free();

        encoder4X.free();
        encoder2X.free();
        encoder1X.free();
    }


    {
   
        new Test("Quad encoders 1x,2x,4x")
        {
            FakeQuadEncoder test;

            public void setup(){
                test = new FakeQuadEncoder(CROSSCONNECTAP2, CROSSCONNECTBP2);
            }

            public void teardown(){
                test.free();
            }

            public void run()
            {

                count.start();
                count.reset();
                count2.start();
                count2.reset();

                motor.set(0.5);

                Timer.delay(3.0);

                assertTrue(Math.abs(count.get()) > 0 && Math.abs(count2.get()) > 0);

                motor.set(0);
                count.stop();
                count2.stop();

                int position4x, position2x, position1x;
                motor.set(-1.0);
                Timer.delay(2.0); // wait for motor to get up to speed
                encoder4X.reset();
                encoder2X.reset();
                encoder1X.reset();
                encoder4X.start();
                encoder2X.start();
                encoder1X.start();
                Timer.delay(5.0);
                position4x = encoder4X.get(); // check position
                position2x = encoder2X.get();
                position1x = encoder1X.get();

                assertTrue(position4x >= EXPECTED_TICKS);
                assertTrue(Math.abs(position4x - position1x) <= 4);
                assertTrue(Math.abs(position4x - position2x) <= 4);

                motor.set(1.0);
                Timer.delay(3.0); // wait to get to speed
                encoder4X.reset();
                encoder2X.reset();
                encoder1X.reset();
                Timer.delay(5.0);
                position4x = encoder4X.get(); // check position
                position2x = encoder2X.get();
                position1x = encoder1X.get();

                assertEquals(position4x, position1x, 4);
                assertEquals(position4x, position2x, 4);
                assertTrue(position4x <= -EXPECTED_TICKS);

                motor.set(0);

                Timer.delay(3.0);

                encoder4X.free();
                encoder2X.free();
                encoder1X.free();

                qA.free();
                qB.free();

                qA = new DigitalInput(CROSSCONNECTAP1);
                qB = new DigitalInput(CROSSCONNECTBP1);

                encoder4X = new Encoder(qA, qB, true);
                encoder2X = new Encoder(qA, qB, true, CounterBase.EncodingType.k2X);
                encoder1X = new Encoder(qA, qB, true, CounterBase.EncodingType.k1X);

                test.setCount(2000);
                test.setRate(1);
                test.setForward(true);

                test.execute();

                assertEquals(2000, encoder4X.get());
                assertEquals(2000, encoder2X.get());
                assertEquals(2000, encoder1X.get());

                test.setForward(false);
                test.execute();

                assertEquals(0, encoder4X.get());
                assertEquals(0, encoder2X.get());
                assertEquals(0, encoder1X.get());

                encoder4X.stop();
                encoder2X.stop();
                encoder1X.stop();
            }
        };
    }
}

