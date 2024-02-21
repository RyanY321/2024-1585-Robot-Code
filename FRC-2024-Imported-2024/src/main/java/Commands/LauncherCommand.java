package Commands;

import Subsystems.Launcher;
import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;

public class LauncherCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public final Launcher m_LauncherSubsystem;

    private boolean isFinished = false;

    public LauncherCommand(Launcher launcherSubsystem, IO controller) {
        m_LauncherSubsystem = launcherSubsystem;

        addRequirements(launcherSubsystem);
        addRequirements(controller);
    }

    public void initialize() {
        System.out.println("Launcher Command initialized...");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        isFinished = true;
    }

    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    public boolean isFinished() {
        return false;
    }
}