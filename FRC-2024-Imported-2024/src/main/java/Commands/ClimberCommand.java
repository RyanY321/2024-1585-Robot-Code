package Commands;
import Subsystems.Climber;
import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ClimberCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public final Climber m_climberSubsystem;
    private final IO m_controller;

    private boolean isFinshed = false;

    public ClimberCommand(Climber climberSubsystem, IO controller) {
        m_climberSubsystem = climberSubsystem;
        m_controller = controller;

        addRequirements(climberSubsystem);
        addRequirements(controller);
    }

    public void initialize () {
        System.out.println("Climber Command initialized...");
    }

    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    public boolean isFinished() {
      return false;
    }
}