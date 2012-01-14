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
public class FakeUltrasonic
{

    private DigitalInput m_pingChan;
    private DigitalOutput m_echoChan;
    private double m_distance;
    private double m_period;
    private FakeUltraThread m_task;
    private Counter read;
    private boolean m_allocatedChannels;
    private static final double kSpeedOfSoundInchesPerSec = 1130.0 * 12.0;

    private class FakeUltraThread extends Thread
    {

        FakeUltrasonic m_ultra;
        Counter grab;

        FakeUltraThread(FakeUltrasonic ultra)
        {
            m_ultra = ultra;
            grab = new Counter(ultra.m_pingChan);
            grab.reset();
            grab.start();
        }

        private void free() {
            if (grab != null)
                grab.free();
            grab = null;
        }

        public void run()
        {
            try
            {
                m_ultra.read.reset();
                m_ultra.read.start();
                double time = 10;


                while (grab.get() <= 0)
                {
                    Thread.sleep((long) 1);
                }
    
                //Wait specified time
                grab.reset();
                time = m_ultra.m_distance / (kSpeedOfSoundInchesPerSec / 2.0);


                for (int gen = 0; gen <= 11; gen++)
                {
                    Thread.sleep((long) (1000 * time));
                    //Send Pulse on echo channel
                    m_ultra.m_echoChan.pulse(.01);

                    if ((gen > 5) && (gen < 11))
                    {
                        m_ultra.m_period = read.getPeriod() - .0016;
                    }

                }


            } catch (InterruptedException e)
            {
            } finally {
                free();
            }
        }
    }

    public FakeUltrasonic(int pingChan, int echoChan, int echoListen)
    {
        m_pingChan = new DigitalInput(pingChan);
        m_echoChan = new DigitalOutput(echoChan);
        m_allocatedChannels = true;
        read = new Counter(echoListen);
        m_task = new FakeUltraThread(this);
    }

    public FakeUltrasonic(DigitalInput pingChan, DigitalOutput echoChan, DigitalInput echoListen)
    {
        m_pingChan = pingChan;
        m_echoChan = echoChan;
        m_allocatedChannels = false;
        read = new Counter(echoListen);
        m_task = new FakeUltraThread(this);
    }

    protected void free()
    {
        m_task.free();
        m_task = null;
        read.stop();
        read.free();
        if (m_allocatedChannels)
        {
            m_pingChan.free();
            m_echoChan.free();
        }

    }

    public void setDistance(double inches)
    {
        m_distance = inches;
    }

    public double getDistanceInches()
    {
        return m_distance;
    }

    public double getDistanceMM()
    {
        return (m_distance * 25.4);
    }

    public void start()
    {
        m_task.start();
    }

    public double getMeasuredDistance()
    {
        return m_period * kSpeedOfSoundInchesPerSec / 2.0;
    }

    public void complete()
    {
        try
        {
            m_task.join();
        } catch (InterruptedException e)
        {
        }
        m_task = new FakeUltraThread(this);

        Timer.delay(.5);
    }

    public double tolInches()
    {
        return 6;
    }

    public double tolMM()
    {
        return 6 * 25.4;
    }
}

