package Commands;

import Subsystems.Launcher;
import Subsystems.IO;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LauncherCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public final Launcher m_LauncherSubsystem;
    private final IO m_controller;

    private boolean liftStopped = false;
    private Boolean stopLifter0;
    private Boolean stopLifter1;

    private SequentialCommandGroup m_lifterCommands = new SequentialCommandGroup();
    private SequentialCommandGroup m_launchCommands = new SequentialCommandGroup();

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

        // Not Currently In Use
        m_lifterCommands.addCommands(
                new LifterAutoCommand(launcherSubsystem, -.30),
                new WaitCommand(1.2),
                new LifterAutoCommand(launcherSubsystem, -.08));

        // Sequential Commands For The Speaker Launching
        m_launchCommands.addCommands(
            new LauncherAutoCommand(launcherSubsystem, 100,
             100,
              0),
            new WaitCommand(1.5),
            new LauncherAutoCommand(launcherSubsystem, 100, 
            100, 
            100),
            new WaitCommand(2),
            new LauncherAutoCommand(launcherSubsystem, 0, 0, 0)
        );

        addRequirements(launcherSubsystem);
        addRequirements(controller);
    }

    public void initialize() {
        System.out.println("Launcher Command initialized...");
        //When Y Button Is Pressed Call launchCommands In Scheduler
        m_controller.m_controller.y().onTrue(m_launchCommands);
        // m_controller.m_controller.x().onTrue(m_lifterCommands);
        // m_controller.m_controller.b().onTrue(m_LauncherSubsystem.LiftLauncherCommand(.08));
        // m_controller.m_controller.b().onFalse(m_LauncherSubsystem.LiftLauncherCommand(-.08));
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        CheckDeadStop();
        CheckButtons();

        isFinished = true;
    }

    private void CheckButtons() {
        // if (m_controller.GetButtonY()) {
        //     m_LauncherSubsystem.Launch(1.00, 1.00);
        // }

        if (m_controller.GetLeftBumper()) {
            m_LauncherSubsystem.Reverse();
        } else {
            m_LauncherSubsystem.Launch(0.00, 0.00);
        }

        // if (m_controller.GetButtonA()) {
        //    // m_LauncherSubsystem.Feeder(1.00);
        // } else {
        //     m_LauncherSubsystem.Feeder(0.00);
        // }

        //If The B Button Is Pressed Then Lower The Launcher
        if (!m_LauncherSubsystem.m_lifterStopped) {
            if (m_controller.GetButtonB()) {
                // System.out.println("B button pushed");
                // System.out.println(stopLifter1);
                if (stopLifter1) {
                    // call function to Lower the launcher
                    m_LauncherSubsystem.LiftLauncher(0.3);
                    // var cmd = new LifterAutoCommand(m_LauncherSubsystem, -.3);
                    // cmd.execute();
                }
            }

            //If The X Button Is Pressed Then Lift The Launcher
            else if (m_controller.GetButtonX()) {
                // Call function to raise the launcher
                // System.out.println("X button pushed");
                // System.out.println(stopLifter0);

                if (stopLifter0) {
                    m_LauncherSubsystem.LiftLauncher(-0.4);

                }

            } else {
                m_LauncherSubsystem.LiftLauncher(-0.10);
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