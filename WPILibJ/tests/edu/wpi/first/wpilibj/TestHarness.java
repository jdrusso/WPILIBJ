/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

/**
 *
 * @author brad
 */
public interface TestHarness {
    //Port connections
    /** Port of motor with gear tooth sensor attached */
    public static final int GTMOTORPORT = 1;
    /** Port of motor with encoder attached */
    public static final int ENDMOTORPORT = 2;
    /** Port of servo with gyro attached */
    public static final int GYROSERVO = 5;
    /** Port of servo with accelerometer attached */
    public static final int ACCELSERVO = 6;

    /** Port attached to ultrasonic ping channel*/
    public static final int ULTRAPING = 1;
    /** Port attached to ultrasonic echo channel*/
    public static final int ULTRAECHO = 2;
    /** First port of cross-connected set A */
    public static final int CROSSCONNECTAP1 = 3;
    /** Second port of cross-connected set A */
    public static final int CROSSCONNECTAP2 = 4;
     /** First port of cross-connected set B */
    public static final int CROSSCONNECTBP1 = 5;
    /** Second port of cross-connected set B */
    public static final int CROSSCONNECTBP2 = 6;
    /** Port of gear tooth sensor */
    public static final int GEARTOOTH = 7;
    /** Port of encoder channel A */
    public static final int QUADENCODERA = 8;
    /** Port of encoder channel B */
    public static final int QUADENCODERB = 9;
    /** Port of relay cross-connect A */
    public static final int RELAYCROSSA = 10;
    /** Port of relay cross-connect B */
    public static final int RELAYCROSSB = 11;

     /** Slot gyro is connected to */
    public static final int GYROSLOT = 1;
    /** Port gyro attached to */
    public static final int GYROCHANNEL = 1;
    /** Slot cross-connected analog is in */
    public static final int CROSSANALOGSLOT = 1;
    /** Analog port that is jumpered */
    public static final int CROSSANALOGCHANNEL = 2;
    /** Slot of accelerometer */
    public static final int ACCELSLOT = 1;
    /** Channel of accelerometer */
    public static final int ACCELCHANNEL = 3;
    /** Slot of potentiometer */
    public static final int POTSLOT = 1;
    /** Channel of potentiometer */
    public static final int POTCHANNEL = 4;
    /** Slot of solenoid cross-connect */
    public static final int SOLCROSSSLOT = 1;
    /** Channel of solenoid cross-connect */
    public static final int SOLCROSSCHANNEL = 5;

    /** Relay port that is cross-connected */
    public static final int RELAYPORT = 1;

    /** Solenoid port that is cross-connected */
    public static final int SOLENOIDPORT = 8;

    /** Is there a camera attatched */
    public static final boolean hasCamera = false;
}
