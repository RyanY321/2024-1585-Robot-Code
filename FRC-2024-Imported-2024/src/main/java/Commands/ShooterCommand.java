package Commands;
import Subsystems.Shooter;
import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ShooterCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
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

     // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //m_driveSubsystem.MoveArcade(-m_controller.GetY(), -m_controller.GetX());
        isFinished = true;
    }
  

    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    public boolean isFinished() {
      return false;
    }
}