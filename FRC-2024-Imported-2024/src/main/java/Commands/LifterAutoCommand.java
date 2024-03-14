package Commands;

import Subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LifterAutoCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Launcher m_LauncherSubsystem;
    private boolean m_launch = false;
    private boolean m_isFinished = false;
    private double m_lifterSpeed = 0.0;

    public LifterAutoCommand(Launcher launcherSubsystem, double lifterSpeed) {
        m_LauncherSubsystem = launcherSubsystem;
        m_lifterSpeed = lifterSpeed;
        addRequirements(launcherSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Launcher Auto Command Has Been Initialized...");
    }

    @Override
    public void execute() {
        if (m_launch = true) {
            // System.out.println("Engaging The Auto Launcher...");
            m_LauncherSubsystem.AutoLift(m_lifterSpeed);
            // var wait = new WaitCommand(3);
            // wait.execute();
            m_isFinished = true;
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
