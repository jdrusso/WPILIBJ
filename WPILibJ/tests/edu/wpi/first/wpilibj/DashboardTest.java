/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj;

import edu.wpi.first.testing.TestClass;

import edu.wpi.first.wpilibj.communication.FRCControl;

/**
 * @file DashboardTest.java
 * Checks the set and get methods, and motor movement, of the victor object
 * @author Ryan O'Meara
 */
public class DashboardTest extends TestClass implements TestHarness {

    private byte[] userDataBuffer;
    private Dashboard db;

    public String getName() {
        return "Dashboard Test";
    }

    public void setup() {
        userDataBuffer = new byte[FRCControl.USER_STATUS_DATA_SIZE];
        db = new Dashboard(userDataBuffer);
    }

    public void teardown() {
    }

    {
        new Test("Primitive Data") {

            public void run() {
/*                db.addInt(37);
                db.addByte((byte) -42);
                db.addDouble(2.71828);
                db.addFloat((float) 3.1415);
                db.addByte((byte) 54);
                db.addBoolean(true);
                db.addInt(-9666034);
                db.addShort((short) 37765);
                db.addBoolean(false);
                db.addShort((short) 5433);
                int size = db.commit();
                byte expected[] = {
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x25,
                    (byte) 0xD6, (byte) 0x40, (byte) 0x05, (byte) 0xBF, (byte) 0x09, (byte) 0x95, (byte) 0xAA, (byte) 0xF7,
                    (byte) 0x90, (byte) 0x40, (byte) 0x49, (byte) 0x0E, (byte) 0x56, (byte) 0x36, (byte) 0x01, (byte) 0xFF,
                    (byte) 0x6C, (byte) 0x82, (byte) 0x0E, (byte) 0x93, (byte) 0x85, (byte) 0x00, (byte) 0x15, (byte) 0x39
                };

                if (!arraycompare(userDataBuffer, expected, size)) {
                    assertFail("First Write/Pack");
                } */


            }
        };
        new Test("Complex Data") {

            public void run() {
/*                db.addArray();
                db.addShort((short) 50);
                db.addShort((short) 55);
                db.addShort((short) 60);
                db.addShort((short) 65);
                db.addShort((short) 70);
                db.addShort((short) 75);
                db.addShort((short) 80);
                db.finalizeArray();
                db.addCluster();
                db.addCluster();
                db.addInt(24);
                db.addShort((short) 48);
                db.finalizeCluster();
                db.addByte((byte) 99);
                db.addInt(-553425654);
                db.addArray();
                db.finalizeArray();
                db.addArray();
                db.addByte((byte) 5);
                db.addByte((byte) 7);
                db.finalizeArray();
                db.finalizeCluster();
                int size = db.commit();
                byte expected[] = {
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x27, 0x00, 0x00, 0x00, 0x07,
                    0x00, 0x32, 0x00, 0x37, 0x00, 0x3C, 0x00, 0x41,
                    0x00, 0x46, 0x00, 0x4B, 0x00, 0x50, 0x00, 0x00,
                    (byte) 0x00, (byte) 0x18, (byte) 0x00, (byte) 0x30, (byte) 0x63, (byte) 0xDF, 0x03, 0x65,
                    0x0A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x02, 0x05, 0x07
                };

                if (!arraycompare(userDataBuffer, expected, size)) {
                    assertFail("Second Write/Pack");
                } */
            }
        };

    }

    private boolean arraycompare(byte[] has, byte[] expected, int size) {
        if (expected.length == size - 1) {
            for (int traverse = 0; traverse < expected.length; traverse++) {
                if (!(has[traverse + 1] == expected[traverse])) {
                    System.out.println("Has : " + has[traverse + 1] + " Expected : " + expected[traverse] + " At index : " + (traverse + 1));
                    return false;
                }
            }
        } else {
            System.out.println("Has length : " + size + " Expected length : " + (expected.length + 1));
            return false;
        }

        return true;

    }
}
