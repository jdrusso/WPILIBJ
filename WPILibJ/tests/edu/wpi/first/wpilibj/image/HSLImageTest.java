/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.image;

import edu.wpi.first.testing.TestClass;

/**
 *
 * @author dtjones
 */
public class HSLImageTest extends TestClass {

    public String getName() {
        return "HSLImageTest";
    }

    {
        new Test("readTest") {

            Image img1 = null;
            Image img2 = null;

            public void teardown() {
                try {
                    if (img1 != null) {
                        img1.free();
                    }
                    if (img2 != null) {
                        img2.free();
                    }
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    img1 = new HSLImage("/test.png");
                } catch (NIVisionException e) {
                    System.out.println(e);
                    assertFail("Should be able to read file");
                }
                try {
                    img2 = new HSLImage("/ImNotAFile.jpg");
                    assertFail("Should not be able to read file");
                } catch (NIVisionException e) {
                }
            }
        };

        new Test("thresholdHSLTest") {

            ColorImage image = null;
            BinaryImage maskRed = null;
            BinaryImage maskBlue = null;
            BinaryImage maskGreen = null;

            public void teardown() {
                try {
                    if (image != null) {
                        image.free();
                    }
                    if (maskRed != null) {
                        maskRed.free();
                    }
                    if (maskBlue != null) {
                        maskBlue.free();
                    }
                    if (maskGreen != null) {
                        maskGreen.free();
                    }
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    image = new HSLImage("/test.png");
                    maskRed = image.thresholdHSL(214, 255, 0, 255, 0, 255);
                    assertEquals(4, maskRed.getNumberParticles());
                    maskBlue = image.thresholdHSL(119, 186, 0, 255, 0, 255);
                    assertEquals(35, maskBlue.getNumberParticles());
                    maskGreen = image.thresholdHSL(59, 130, 0, 255, 0, 255);
                    assertEquals(3, maskGreen.getNumberParticles());
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        new Test("thresholdHSVTest") {

            ColorImage image = null;
            BinaryImage maskRed = null;
            BinaryImage maskBlue = null;
            BinaryImage maskGreen = null;

            public void teardown() {
                try {
                    if (image != null) {
                        image.free();
                    }
                    if (maskRed != null) {
                        maskRed.free();
                    }
                    if (maskBlue != null) {
                        maskBlue.free();
                    }
                    if (maskGreen != null) {
                        maskGreen.free();
                    }
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    image = new HSLImage("/test.png");
                    maskRed = image.thresholdHSV(214, 255, 0, 255, 0, 255);
                    assertEquals(4, maskRed.getNumberParticles());
                    maskBlue = image.thresholdHSV(119, 186, 0, 255, 0, 255);
                    assertEquals(35, maskBlue.getNumberParticles());
                    maskGreen = image.thresholdHSV(59, 130, 0, 255, 0, 255);
                    assertEquals(3, maskGreen.getNumberParticles());
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        new Test("thresholdHSITest") {

            ColorImage image = null;
            BinaryImage maskRed = null;
            BinaryImage maskBlue = null;
            BinaryImage maskGreen = null;

            public void teardown() {
                try {
                    if (image != null) {
                        image.free();
                    }
                    if (maskRed != null) {
                        maskRed.free();
                    }
                    if (maskBlue != null) {
                        maskBlue.free();
                    }
                    if (maskGreen != null) {
                        maskGreen.free();
                    }
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    image = new HSLImage("/test.png");
                    maskRed = image.thresholdHSI(214, 255, 0, 255, 0, 255);
                    assertEquals(4, maskRed.getNumberParticles());
                    maskBlue = image.thresholdHSI(119, 186, 0, 255, 0, 255);
                    assertEquals(35, maskBlue.getNumberParticles());
                    maskGreen = image.thresholdHSI(59, 130, 0, 255, 0, 255);
                    assertEquals(3, maskGreen.getNumberParticles());
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        new Test("thresholdRGBTest") {

            ColorImage image = null;
            BinaryImage maskRed = null;
            BinaryImage maskBlue = null;
            BinaryImage maskGreen = null;

            public void teardown() {
                try {
                    if (image != null) {
                        image.free();
                    }
                    if (maskRed != null) {
                        maskRed.free();
                    }
                    if (maskBlue != null) {
                        maskBlue.free();
                    }
                    if (maskGreen != null) {
                        maskGreen.free();
                    }
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    image = new HSLImage("/test.png");
                    maskRed = image.thresholdRGB(150, 250, 0, 250, 0, 250);
                    assertEquals(4, maskRed.getNumberParticles());
                    maskBlue = image.thresholdRGB(0, 250, 0, 250, 150, 250);
                    assertEquals(35, maskBlue.getNumberParticles());
                    maskGreen = image.thresholdRGB(0, 250, 150, 250, 0, 250);
                    assertEquals(3, maskGreen.getNumberParticles());
                    maskBlue.write("/bluemask.png");
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        new Test("particleTest that doesn't test anything") {

            ColorImage image = null;
            BinaryImage maskRed = null;
            BinaryImage maskBlue = null;
            BinaryImage maskGreen = null;

            public void teardown() {
                try {
                    if (image != null) {
                        image.free();
                    }
                    if (maskRed != null) {
                        maskRed.free();
                    }
                    if (maskBlue != null) {
                        maskBlue.free();
                    }
                    if (maskGreen != null) {
                        maskGreen.free();
                    }
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    image = new HSLImage("/test.png");
                    maskRed = image.thresholdHSL(214, 255, 0, 255, 0, 255);
                    maskBlue = image.thresholdHSL(119, 186, 0, 255, 0, 255);
                    maskGreen = image.thresholdHSL(59, 130, 0, 255, 0, 255);
                    ParticleAnalysisReport[] reports = maskRed.getOrderedParticleAnalysisReports();
//                for (int i = 0; i < reports.length; i++)
//                    System.out.println(reports[i]);
                    reports = maskBlue.getOrderedParticleAnalysisReports(5);
//                for (int i = 0; i < reports.length; i++)
//                    System.out.println(reports[i]);
                    reports = maskGreen.getOrderedParticleAnalysisReports();
//                for (int i = 0; i < reports.length; i++)
//                    System.out.println(reports[i]);
                } catch (NIVisionException e) {
                    assertFail(e.getMessage());
                }
            }
        };

        new Test("replaceColorPlaneTest") {

            ColorImage image = null;
            MonoImage red = null;
            MonoImage green = null;
            MonoImage blue = null;

            public void setup() {
                try {
                    image = new HSLImage("/test.png");
                    red = image.getRedPlane();
                    green = image.getGreenPlane();
                    blue = image.getBluePlane();
                } catch (NIVisionException e) {
                }
            }

            public void teardown() {
                try {
                    image.free();
                    red.free();
                    green.free();
                    blue.free();
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    image.replaceBluePlane(red).replaceGreenPlane(blue).replaceRedPlane(green);
                    image.write("/replaceColorPlaneTest-rotatedColors.png");
                } catch (NIVisionException e) {
                    assertFail("NIVision exception : " + e.getMessage());
                }
            }
        };

        new Test("equalizeTest") {

            ColorImage imageone = null;
            ColorImage imagetwo = null;

            public void setup() {
                try {
                    imageone = new HSLImage("/test.png");
                    imagetwo = new HSLImage("/test.png");
                } catch (NIVisionException e) {
                }
            }

            public void teardown() {
                try {
                    imageone.free();
                    imagetwo.free();
                } catch (NIVisionException e) {
                }
            }

            public void run() {
                try {
                    imageone.colorEqualize().write("/equalizeTest-all.png");
                    imagetwo.colorEqualize().write("/equalizeTest-luminance.png");
                } catch (NIVisionException e) {
                    assertFail("NIVision exception : " + e.getMessage());
                }
            }
        };
    }
}
