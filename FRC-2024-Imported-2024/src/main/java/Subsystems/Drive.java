package Subsystems;

import com.revrobotics.CANSparkLowLevel;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.CANSparkMax;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

    private DifferentialDrive m_driveController;
    private final CANSparkMax m_leftMotorA;
    private final CANSparkMax m_leftMotorB;
    private final CANSparkMax m_rightMotorA;
    private final CANSparkMax m_rightMotorB;
    private final Gyro m_gyro;
    private double kP = 1;

    public Drive(Gyro gyro) {
        m_gyro = gyro;
        //m_leftMotor = new PWMSparkMax(leftMotorChannel);
        m_leftMotorA = new CANSparkMax(1, CANSparkLowLevel.MotorType.kBrushed);
        m_leftMotorB = new CANSparkMax(2, CANSparkLowLevel.MotorType.kBrushed);
        m_rightMotorA = new CANSparkMax(3, CANSparkLowLevel.MotorType.kBrushed);
        m_rightMotorB = new CANSparkMax(4, CANSparkLowLevel.MotorType.kBrushed);

        m_leftMotorA.setInverted(true);
        m_rightMotorA.setInverted(false);

        m_leftMotorB.follow(m_leftMotorA);
        m_rightMotorB.follow(m_rightMotorA);

        //m_rightMotor = new PWMSparkMax(rightMotorChannel);
        m_driveController = new DifferentialDrive(m_leftMotorA, m_rightMotorA);
        m_driveController.setSafetyEnabled(false);
        m_driveController.setExpiration(0.1);
        m_driveController.setMaxOutput(1);
    }

    public Command moveArcadeCommand(double xSpeed, double zRotation) {
        // Inline construction of command goes here.
        // Subsystem::RunOnce implicitly requires `this` subsystem.
        return run(
                () -> {
                    this.MoveArcade(xSpeed, zRotation);
                });
    }

    /**
     * @implNote Move the robot using arcade single joystick
     * @param xSpeed    motor speed
     * @param zRotation motor speed
     */
    public void MoveArcade(double xSpeed, double zRotation) {
        // This executes every 20ms so this fills up logs, only use when needed
        // System.out.println(String.format("Robot Move, speed %.2f, rotation %.2f",
        // xSpeed,zRotation));
        m_driveController.arcadeDrive(xSpeed, zRotation);
    }

    public void MoveTank(double leftSpeed, double rightSpeed) {
        double error = m_gyro.GetStartHeading() - m_gyro.GetGyro().getAngle();
        // m_driveController.tankDrive(leftSpeed, rightSpeed);
        // On the base the left and right sides are inverted, LeftSpeed means RightSpeed, RightSpeed means LeftSpeed
        m_driveController.tankDrive(leftSpeed + kP * error, rightSpeed - kP * error);
    }

    public double GetLeft() {
        return m_leftMotorA.get();
    }

    public double GetRight() {
        return m_rightMotorA.get();
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
