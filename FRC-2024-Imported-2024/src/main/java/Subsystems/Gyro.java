package Subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {
    private final ADXRS450_Gyro m_gyro;
    private double m_heading = 0.0;

    public double GetStartHeading() {
        return m_heading;
    }

    public Gyro() {
        m_gyro = new ADXRS450_Gyro();
        m_heading = m_gyro.getAngle();
    }

    public ADXRS450_Gyro GetGyro() {
        return m_gyro;
    }

    @Override

    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
