package Commands;
import Subsystems.Shooter;
import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;

public class ShooterCommand {
    public final Shooter m_shooterSubsystem;
    private final IO m_controller;

    private boolean isFinished = false;

    public ShooterCommand(Shooter shooterSubsystem, IO controller) {
        m_shooterSubsystem = shooterSubsystem;
        m_controller = controller;

        addRequirements(shooterSubsystem);
        addRequirements(controller);
    }

    public void initialize () {
        System.out.println("Shooter Command initialized...");
    }

    
    public void execute() {
        double val = m_controller.GetShooterValue();
        if(val > 0)
        {
            m_shooterSubsystem.Shoot(val);
        }
        isFinished = true;

        
    }

    public void execute() {
        double val = m_controller.GetPickUpValue();
        if (val > 0)
        {
            m_shooterSubsystem.PickUp(val);
        }
        isFinished = true;
    }

    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    public boolean isFinished() {
      return false;
    }
}