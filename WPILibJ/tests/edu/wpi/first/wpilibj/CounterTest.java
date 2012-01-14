/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

import java.util.Vector;

/**
 * @file CounterTest.java
 * Tests the counter class functionality, using pulses
 * @author Ryan O'Meara
 */
public class CounterTest extends TestClass implements TestHarness {

    private final int MAXCOUNT = 1000;
    private DigitalInput qA;
    private DigitalInput qB;
    private FakeQuadEncoder fakeEncoder;

    public String getName() {
        return "Counter Test";
    }

    public void setup() {
        qA = new DigitalInput(CROSSCONNECTAP1);
        qB = new DigitalInput(CROSSCONNECTBP1);
        fakeEncoder = new FakeQuadEncoder(CROSSCONNECTAP2, CROSSCONNECTBP2);
    }

    public void teardown() {
        qA.free();
        qB.free();
        fakeEncoder.free();
    }

    {
        new Test("Single 1x and 2x counters") {
            private Counter count1x;
            private Counter count2x;

            public void setup() {
                count1x = new Counter(Counter.EncodingType.k1X, qA, qB, false);
                count2x = new Counter(Counter.EncodingType.k2X, qA, qB, false);
            }

            public void teardown() {
                count1x.free();
                count2x.free();
            }

            public void run() {
                fakeEncoder.setCount(MAXCOUNT);
                fakeEncoder.setForward(false);
                fakeEncoder.setRate(1);
                count1x.start();
                count2x.start();

                fakeEncoder.execute();

                Timer.delay(.25);

                assertEquals(2 * MAXCOUNT, count2x.get());
                assertEquals(MAXCOUNT, count1x.get());
                
                count1x.stop();
                count2x.stop();
                
                count1x.reset();
                count2x.reset();

                fakeEncoder.setCount(MAXCOUNT);
                fakeEncoder.setForward(false);
                fakeEncoder.setRate(1);
                count1x.start();
                count2x.start();

                fakeEncoder.execute();

                Timer.delay(.25);

                assertEquals(2 * MAXCOUNT, count2x.get());
                assertEquals(MAXCOUNT, count1x.get());

                count1x.stop();
                count2x.stop();
            }
        };

        new Test("Generic Counter"){
            private Counter count;

            public void setup(){
                count = new Counter(qA);
            }

            public void teardown(){
                count.free();
            }

            public void run(){
                fakeEncoder.setForward(true);
                fakeEncoder.setCount(MAXCOUNT);
                fakeEncoder.setRate(1);
                count.start();
                fakeEncoder.execute();

                assertEquals(MAXCOUNT, count.get());

                count.stop();
            }
        };

        new Test("8 simultaneous counters") {

            private Vector vec1x = new Vector();
            private Vector vec2x = new Vector();

            public void setup() {
                for (int i = 0; i < 4; i++) {
                    vec1x.addElement(new Counter(Counter.EncodingType.k1X, qA, qB, false));
                }
                for (int i = 0; i < 4; i++) {
                    vec2x.addElement(new Counter(Counter.EncodingType.k2X, qA, qB, false));
                }
            }

            public void teardown() {
                for (int i = 0; i < vec1x.size(); i++) {
                    ((Counter) vec1x.elementAt(i)).free();
                }
                for (int i = 0; i < vec2x.size(); i++) {
                    ((Counter) vec2x.elementAt(i)).free();
                }
            }

            public void run() {
                for (int i = 0; i < vec1x.size(); i++) {
                    ((Counter) vec1x.elementAt(i)).reset();
                    ((Counter) vec1x.elementAt(i)).start();
                }
                for (int i = 0; i < vec2x.size(); i++) {
                    ((Counter) vec2x.elementAt(i)).reset();
                    ((Counter) vec2x.elementAt(i)).start();
                }

                fakeEncoder.setCount(MAXCOUNT);
                fakeEncoder.setForward(false);

                fakeEncoder.execute();

                String output = "";

                for (int i = 0; i < vec1x.size(); i++) {
                    output += "\t\t" + i + ": " + ((Counter) vec1x.elementAt(i)).get() + "\n";
                }
                for (int i = 0; i < vec2x.size(); i++) {
                    output += "\t\t" + i + ": " + ((Counter) vec2x.elementAt(i)).get() + "\n";
                }

                for (int i = 0; i < vec1x.size(); i++) {
                    if (((Counter) vec1x.elementAt(0)).get() != MAXCOUNT) {
                        assertFail("8 simultaneous counters: \n" + output);
                    }
                }
                for (int i = 0; i < vec2x.size(); i++) {
                    if (((Counter) vec2x.elementAt(0)).get() != (2 * MAXCOUNT)) {
                        assertFail("8 simultaneous counters: \n" + output);
                    }
                }
            }
        };

        new Test("Constructors"){
          private Counter construct;

          public void teardown(){
              try{
                  construct.free();
              }catch(Exception e){}
          }

          public void run(){
              construct = new Counter();
              construct.free();

              construct = new Counter(qA);
              construct.free();

              construct = new Counter(new AnalogTrigger(1));

              construct.free();
          }
        };

        new Test("Too Many Counters Exception"){
            public void run(){
                Vector excp = new Vector();
                try{
                    for (int i = 0; i < 100; i++) {
                        excp.addElement(new Counter(Counter.EncodingType.k1X, qA, qB, false));
                    }

                    assertFail("No exception generated by too many counters");
                }catch(Exception e){}

                for(int i =0; i< excp.size(); i++){
                    ((Counter)excp.elementAt(i)).free();
                }

            }
        };
    }
}
