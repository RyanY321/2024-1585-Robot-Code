package Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase
{
    private final PWMSparkMax m_climber;

    public Climber(int 
    ClimberMotorChannel)
    {
        m_climber = new PWMSparkMax(ClimberMotorChannel);
    }

    public Command StopClimberCommand() {
        return runOnce(
            () -> {
                this.StopClimb();
            });
    }

    private void StopClimb()
    {
        System.out.println("Stopping The Climber...");
        m_climber.set(0.00);
    }

    public void periodic() {
      // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
    
}
