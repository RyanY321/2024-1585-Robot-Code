package Commands;

import Subsystems.Launcher;
import Subsystems.IO;
import edu.wpi.first.math.proto.Controller;
import edu.wpi.first.wpilibj2.command.Command;

public class LauncherCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public final Launcher m_LauncherSubsystem;
    private final IO m_controller;

    private boolean isFinished = false;

    // Controlller Buttons
    // Y = high speed
    // A = Low speed
    // LeftBumper = reverse

    public LauncherCommand(Launcher launcherSubsystem, IO controller) {
        m_LauncherSubsystem = launcherSubsystem;
        m_controller = controller;

        addRequirements(launcherSubsystem);
        addRequirements(controller);
    }

    public void initialize() {
        System.out.println("Launcher Command initialized...");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        CheckDeadStop();
        CheckButtons();

        isFinished = true;
    }

    private void CheckButtons() {
        if (!m_LauncherSubsystem.m_lifterStopped) {
            if (m_controller.GetB()) {
                // call function to Lower the launcher
                m_LauncherSubsystem.LiftLauncher(-0.2);
            }

            else if (m_controller.GetXBool()) {
                // Call function to raise the launcher
                m_LauncherSubsystem.LiftLauncher(0.5);
            }
            else {
                m_LauncherSubsystem.LiftLauncher(0);
            }
        }
    }

    private void CheckDeadStop() {
        if (m_LauncherSubsystem.m_lifterStop0 != null && m_LauncherSubsystem.m_lifterStop1 != null) {

            System.out.println("checking stops");
            Boolean stopLifter0 = m_LauncherSubsystem.m_lifterStop0.get();
            Boolean stopLifter1 = m_LauncherSubsystem.m_lifterStop1.get();

            // Debugger, uncomment for debugging
            // =======================================================
            // String message = "Lifter Stop 0 " + stopLifter0 + "\n" + "\n" + "Lifter Stop
            // 1 " + stopLifter1 + "\n";
            // System.out.println(message);
            // =======================================================

            // False is when the switch is depressed
            if (stopLifter0 == false || stopLifter1 == false) {
                // Stops the lifting motor
                m_LauncherSubsystem.m_liftMotor.stopMotor();
                System.out.println("Lifter Motor Stopped...");
                m_LauncherSubsystem.m_lifterStopped = true;
            } else if (stopLifter0 == true && stopLifter1 == true) {
                m_LauncherSubsystem.m_lifterStopped = false;
            }
        }

    }

    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    public boolean isFinished() {
        return false;
    }
}