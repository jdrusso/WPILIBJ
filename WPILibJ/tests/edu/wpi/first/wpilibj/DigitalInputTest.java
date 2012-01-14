/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

/**
 * @file DigitalInputTest.java
 * Tests the allocation of digital inputs
 * @author Ryan O'Meara
 */
public class DigitalInputTest extends TestClass implements TestHarness
{

    public String getName()
    {
        return "DigitalInput Test";
    }

    {
        new Test("Allocation")
        {

            public void run()
            {
                {
                    DigitalInput digitalInput = new DigitalInput(CROSSCONNECTAP1);
                    digitalInput.get();
                    digitalInput.free();
                }
                DigitalInput digitalInput = new DigitalInput(CROSSCONNECTAP1);
                digitalInput.get();
                digitalInput.free();

                try{
                    DigitalInput digIn = new DigitalInput(CROSSCONNECTAP1);
                    digIn.free();
                    assertFail("No exception thrown");
                }catch(Exception e){}


            }
        };

        new Test("Correct readings"){
            private DigitalInput digInput;
            private DigitalOutput digOutput;

            public void setup(){
                digInput = new DigitalInput(CROSSCONNECTAP1);
                digOutput = new DigitalOutput(CROSSCONNECTAP2);
            }

            public void teardown(){
                digInput.free();
                digOutput.free();
            }

            public void run(){
                digOutput.set(false);
                Timer.delay(1.0);

                assertTrue(digInput.get());

                digOutput.set(true);

                Timer.delay(1.0);

                assertTrue(!digInput.get());
            }
        };

    }
}
