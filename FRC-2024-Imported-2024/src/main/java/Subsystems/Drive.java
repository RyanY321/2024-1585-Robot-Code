package Subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase
{ 

    private DifferentialDrive m_driveController;
    private final PWMSparkMax m_leftMotor;
    private final PWMSparkMax m_rightMotor;
    
    public Drive(int leftMotorChannel, 
                int rightMotorChannel)
    {
        m_leftMotor = new PWMSparkMax(leftMotorChannel);
        m_rightMotor = new PWMSparkMax(rightMotorChannel);
        m_driveController = new DifferentialDrive(m_leftMotor, m_rightMotor);
        //
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
     * @param xSpeed motor speed
     * @param zRotation motor speed
     */
    public void MoveArcade(double xSpeed, double zRotation)
    {
        //This executes every 20ms so this fills up logs, only use when needed
        //System.out.println(String.format("Robot Move, speed %.2f, rotation %.2f", xSpeed,zRotation));
        m_driveController.arcadeDrive(xSpeed, zRotation); 
    }

    public void MoveTank(double leftSpeed, double rightSpeed)
    {
        m_driveController.tankDrive(leftSpeed, rightSpeed);
    }

    public double GetLeft()
    {
        return m_leftMotor.get();
    }

    public double GetRight()
    {
        return m_rightMotor.get();
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
