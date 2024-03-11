package Commands;

import Subsystems.Launcher;
import Subsystems.IO;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LauncherCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public final Launcher m_LauncherSubsystem;
    private final IO m_controller;

    private boolean liftStopped = false;
    private Boolean stopLifter0;
    private Boolean stopLifter1;

    private boolean isFinished = false;

    // Controlller Buttons
    // Y = high speed
    // A = Low speed
    // X = Raise Launcher
    // Y = Lower Launcher
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
        if (m_controller.GetButtonY()) {
            m_LauncherSubsystem.Launch(-1.00, -1.00);
        }

        else if (m_controller.GetLeftBumper()) {
            m_LauncherSubsystem.Reverse();
        } else {
            m_LauncherSubsystem.Launch(0.00, 0.00);
        }

        if (m_controller.GetButtonA()) {
            m_LauncherSubsystem.Feeder(1.00);
        } else {
            m_LauncherSubsystem.Feeder(0.00);
        }

        if (!m_LauncherSubsystem.m_lifterStopped) {
            if (m_controller.GetButtonB()) {
                if(stopLifter0)
                {
                    // call function to Lower the launcher
                    m_LauncherSubsystem.LiftLauncher(-0.30);
                }
            }

            else if (m_controller.GetButtonX()) {
                // Call function to raise the launcher
                if(stopLifter1)
                {
                    m_LauncherSubsystem.LiftLauncher(0.30);
                }

            } else {
                m_LauncherSubsystem.LiftLauncher(0.00);
            }
        }
    }

    private void CheckDeadStop() {
        if (m_LauncherSubsystem.m_lifterStop0 != null && m_LauncherSubsystem.m_lifterStop1 != null) {

            // System.out.println("checking stops");
            stopLifter0 = m_LauncherSubsystem.m_lifterStop0.get();
            stopLifter1 = m_LauncherSubsystem.m_lifterStop1.get();

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
                // liftStopped = true;

                // if(!liftStopped)
                // {
                // m_LauncherSubsystem.m_liftMotor.stopMotor();
                // liftStopped = true;
                // }
                // else
                // {

                // //If back stop triggered
                // if(!stopLifter0)
                // {
                // m_LauncherSubsystem.m_liftMotor.set(.25);
                // }

                // if(!stopLifter1)
                // {
                // m_LauncherSubsystem.m_liftMotor.set(-.25);
                // }
                // }

                // new WaitCommand(3.0);
                // _LauncherSubsystem.m_liftMotor.set(-0.10);
                // new WaitCommand(1.5);
                // m_LauncherSubsystem.m_liftMotor.stopMotor();
                // System.out.println("Lifter Motor Stopped...");
                // m_LauncherSubsystem.m_lifterStopped = true;
            } else if ((stopLifter0 && stopLifter1) && liftStopped) {
                m_LauncherSubsystem.m_liftMotor.stopMotor();
                // liftStopped = false;
            }

            // else if (stopLifter0 == true && stopLifter1 == true) {
            // m_LauncherSubsystem.m_lifterStopped = false;
            // }

        }

    }

    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    public boolean isFinished() {
        return false;
    }
}