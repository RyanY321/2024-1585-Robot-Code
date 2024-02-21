package Commands;

import Subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.Command;

public class LauncherAutoCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Launcher m_LauncherSubsystem;
    private boolean m_launch = false;
    private boolean m_isFinished = false;
    private double m_FrontMotorSpeed;
    private double m_BackMotorSpeed;

    public LauncherAutoCommand(Launcher launcherSubsystem, double frontLaunchMotorSpeed, double backLaunchMotorSpeed) {
        m_LauncherSubsystem = launcherSubsystem;
        m_FrontMotorSpeed = frontLaunchMotorSpeed;
        m_BackMotorSpeed = backLaunchMotorSpeed;
        addRequirements(launcherSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Launcher Auto Command Has Been Initialized...");
    }

    @Override
    public void execute() {
        if (m_launch = true) {
            System.out.println("Engaging The Auto Launcher...");
            m_LauncherSubsystem.AutoLaunch(m_FrontMotorSpeed, m_BackMotorSpeed);
            m_isFinished = true;
        }
    }
}
