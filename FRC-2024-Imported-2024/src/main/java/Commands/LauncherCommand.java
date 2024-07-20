package Commands;

import Subsystems.Launcher;
import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LauncherCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public final Launcher m_LauncherSubsystem;
    private final IO m_controller;

    // private boolean liftStopped = false;
    // private Boolean stopLifter0;
    // private Boolean stopLifter1;

    private SequentialCommandGroup m_lifterCommands = new SequentialCommandGroup();
    private SequentialCommandGroup m_launchCommands = new SequentialCommandGroup();

    private boolean isFinished = false;

    // Controller Buttons
    // Y = Launch The Note
    // A = Feed In the Note
    // X = Raise Launcher
    // Y = Lower Launcher
    // LeftBumper = reverse

    public LauncherCommand(Launcher launcherSubsystem, IO controller) {
        m_LauncherSubsystem = launcherSubsystem;
        m_controller = controller;

        // Not Currently In Use
        m_lifterCommands.addCommands(
                new LifterAutoCommand(launcherSubsystem, -.30),
                new WaitCommand(1.2),
                new LifterAutoCommand(launcherSubsystem, -.08));

        // Sequential Commands For The Speaker Launching
        m_launchCommands.addCommands(
                new LauncherAutoCommand(launcherSubsystem,
                        100,
                        0),
                new WaitCommand(1.5),
                new LauncherAutoCommand(launcherSubsystem,
                        100,
                        -100),
                new WaitCommand(2),
                new LauncherAutoCommand(launcherSubsystem, 0, 0));

        addRequirements(launcherSubsystem);
        addRequirements(controller);
    }

    public void initialize() {
        System.out.println("Launcher Command initialized...");
        // When Y Button Is Pressed Call launchCommands In Scheduler
        // m_controller.m_controller.y().onTrue(m_launchCommands);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // CheckDeadStop();
        CheckButtons();

        isFinished = true;
    }

    private void CheckButtons() {
        if (m_controller.GetButtonB()) {
            m_LauncherSubsystem.FeedLauncher(-0.3);
        } else if (m_controller.GetButtonX()) {
            m_LauncherSubsystem.FeedLauncher(0.3);
        } else {
            m_LauncherSubsystem.FeedLauncher(0.00);
        }

        if (m_controller.GetButtonY()) {
            m_LauncherSubsystem.Launch(1.00);
            m_LauncherSubsystem.Guiding(0.00);
        } else if (m_controller.GetLeftBumper()) {
            m_LauncherSubsystem.Launch(-0.40);
            m_LauncherSubsystem.Guiding(0.40);
        } else if (m_controller.GetButtonA()) {
            m_LauncherSubsystem.Launch(0.00);
            m_LauncherSubsystem.Guiding(-1.00);
        } else {
            m_LauncherSubsystem.Launch(0.00);
            m_LauncherSubsystem.Guiding(0.00);
        }
    }

    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    public boolean isFinished() {
        return false;
    }
}