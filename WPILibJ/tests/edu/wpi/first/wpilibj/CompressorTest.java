/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file CompressorTest.java
 * Tests the compresor class using cross wired relay port and digital input port
 * @author Ryan O'Meara
 */
public class CompressorTest extends TestClass implements TestHarness
{

    private Compressor testcomp;
    private DigitalOutput pressure;
    private DigitalInput comp;

    public String getName()
    {
        return "Compressor Test";
    }

    public void setup()
    {
        testcomp = new Compressor(CROSSCONNECTAP2, RELAYPORT);
        pressure = new DigitalOutput(CROSSCONNECTAP1);
        comp = new DigitalInput(RELAYCROSSA);
        testcomp.start();
    }

    public void teardown()
    {
        testcomp.stop();
        testcomp.free();
        pressure.free();
        comp.free();
    }


    {
        new Test("Off")
        {
            public void run()
            {

                pressure.set(false);
                Timer.delay(1.0);

                assertTrue(!comp.get());
            }
        };

        new Test("On")
        {
            public void run()
            {

                pressure.set(true);
                Timer.delay(1.0);

                assertTrue(comp.get());
            }
        };

        new Test("GetPressureSwitchValue"){
            public void run(){
                pressure.set(true);

                Timer.delay(1.0);
                
                assertTrue(!testcomp.getPressureSwitchValue());

                pressure.set(false);

                Timer.delay(1.0);
                
                assertTrue(testcomp.getPressureSwitchValue());
            }
        };
    }
}
