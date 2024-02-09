package Commands;
import Subsystems.Shooter;
import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ShooterCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public final Shooter m_shooterSubsystem;
    private final IO m_controller;

    private boolean isFinshed = false;

    public ShooterCommand(Shooter shooterSubsystem, IO controller) {
        m_shooterSubsystem = shooterSubsystem;
        m_controller = controller;

        addRequirements(shooterSubsystem);
        addRequirements(controller);
    }

    public void initialize () {
        System.out.println("Shooter Command initialized...");
    }

    
    public void ForwardShoot() {
        double val = m_controller.GetShooterValue();
        if(val > 0)
        {
            m_shooterSubsystem.Shoot(val);
        }
        isFinshed = true;
    }

        public void ReverseShoot() {
        double val = m_controller.GetReverseShooterValue();
        if(val > 0)
        {
            m_shooterSubsystem.ReverseShoot(val);
        }
        isFinshed = true;
    }

    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    public boolean isFinished() {
      return false;
    }
}