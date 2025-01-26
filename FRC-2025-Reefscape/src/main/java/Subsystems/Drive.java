package Subsystems;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.spark.SparkMax;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class Drive extends SubsystemBase {

    private DifferentialDrive m_driveController;
    private final SparkMax m_leftMotorA;
    private final SparkMax m_leftMotorB;
    private final SparkMax m_rightMotorA;
    private final SparkMax m_rightMotorB;
    private final Gyro m_gyro;
    private double kP = 1;


    @SuppressWarnings("deprecation")
    public Drive(Gyro gyro) {
        m_gyro = gyro;
        // m_leftMotor = new PWMSparkMax(leftMotorChannel);
        m_leftMotorA = new SparkMax(1, SparkLowLevel.MotorType.kBrushed);
        m_leftMotorB = new SparkMax(2, SparkLowLevel.MotorType.kBrushed);
        m_rightMotorA = new SparkMax(3, SparkLowLevel.MotorType.kBrushed);
        m_rightMotorB = new SparkMax(4, SparkLowLevel.MotorType.kBrushed);
        SparkMaxConfig globalConfig = new SparkMaxConfig();
        
        // m_leftMotorA.setInverted(true);
        // m_rightMotorA.setInverted(false);
        m_leftMotorA.setInverted(false);
        m_rightMotorA.setInverted(true);

        SparkMaxConfig m_rightMotorBConfig = new SparkMaxConfig();
        SparkMaxConfig m_leftMotorBConfig = new SparkMaxConfig();

        m_rightMotorBConfig
            .follow(m_rightMotorA);

        m_leftMotorBConfig
            .follow(m_leftMotorA);

        // m_rightMotor = new PWMSparkMax(rightMotorChannel);
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
        // On the base the left and right sides are inverted, LeftSpeed means
        // RightSpeed, RightSpeed means LeftSpeed
        m_driveController.tankDrive(leftSpeed + kP * error, rightSpeed - kP * error);
    }

    public void MoveTankAuto(double leftSpeed, double rightSpeed) {
        m_driveController.tankDrive(leftSpeed, rightSpeed);
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
