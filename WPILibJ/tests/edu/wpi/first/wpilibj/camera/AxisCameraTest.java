/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.camera;

import edu.wpi.first.testing.TestClass;
import edu.wpi.first.wpilibj.TestHarness;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.Image;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class AxisCameraTest extends TestClass implements TestHarness {

    AxisCamera camera;

    public String getName() {
        return "AxisCameraTest";
    }

    void writeImage(String name) throws AxisCameraException, NIVisionException {
        Image image = AxisCamera.getInstance().getImage();
        image.write(name);
        image.free();
    }

    public void setup() {
        boolean cont = false;
        Timer timer = new Timer();
        timer.start();
        camera = AxisCamera.getInstance();
        while (!cont) {
            try {
                camera.getImage().free();
                cont = true;
            } catch (AxisCameraException e) {
            } catch (NIVisionException e) {
            }
        }
        System.out.println("    Camera initialized in " + timer.get() + " seconds");
    }

    {
        new Test("writeResolutionTest") {

            public void run() {
                for (int i = 0; i < AxisCamera.ResolutionT.allValues.length; i++) {
                    try {
                        AxisCamera.ResolutionT resolution = AxisCamera.ResolutionT.allValues[i];
                        camera.writeResolution(resolution);
                        Timer.delay(5.0);
                        Image image = camera.getImage();
                        assertEquals(resolution.height, image.getHeight());
                        assertEquals(resolution.width, image.getWidth());
                        image.free();
                    } catch (AxisCameraException e) {
                        assertFail(e.getMessage());
                    } catch (NIVisionException e) {
                        assertFail(e.getMessage());
                    }
                }
            }
        };

        new Test("writeRotationTest - manually check the images on the crio") {

            public void run() {
                try {
                    camera.writeRotation(AxisCamera.RotationT.k180);
                    Timer.delay(5.0);
                    writeImage("/writeRotationTest-upSideDown.jpg");
                    camera.writeRotation(AxisCamera.RotationT.k0);
                    Timer.delay(5.0);
                    writeImage("/writeRotationTest-rightSideUp.jpg");
                } catch (AxisCameraException e) {
                    assertFail(e.getMessage());
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        new Test("writeBrightnessTest - manually check the images on the crio") {

            public void run() {
                try {
                    camera.writeBrightness(0);
                    Timer.delay(5.0);
                    writeImage("/writeBrightnessTest-lessBright.jpg");
                    camera.writeBrightness(100);
                    Timer.delay(5.0);
                    writeImage("/writeBrightnessTest-moreBright.jpg");
                } catch (AxisCameraException e) {
                    assertFail(e.getMessage());
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        new Test("writeColorLevelTest - manually check the images on the crio") {

            public void run() {
                try {
                    camera.writeColorLevel(0);
                    Timer.delay(5.0);
                    writeImage("/writeColorLevelTest-lowColor.jpg");
                    camera.writeColorLevel(100);
                    Timer.delay(5.0);
                    writeImage("/writeColorLevelTest-highColor.jpg");
                    camera.writeColorLevel(50);
                    Timer.delay(5.0);
                    writeImage("/writeColorLevelTest-midColor.jpg");
                } catch (AxisCameraException e) {
                    assertFail(e.getMessage());
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        //TODO add get tests - will fail for now - make sure to test every get

        new Test("axisCameraTest") {

            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        ColorImage image = AxisCamera.getInstance().getImage();
                        image.thresholdHSL(0, 255, 0, 255, 0, 255).free();
                        image.thresholdHSL(0, 255, 0, 255, 0, 255).free();
                        image.free();
                    } catch (AxisCameraException e) {
                        assertFail(e.getMessage());
                    } catch (NIVisionException e) {
                        assertFail(e.getMessage());
                    }
                }
            }
        };
    }
}
