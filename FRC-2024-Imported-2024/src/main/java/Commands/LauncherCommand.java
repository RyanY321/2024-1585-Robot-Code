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
    private boolean reversing = false;

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

        reversing = (m_controller.GetButtonY() || m_controller.GetButtonA() == false) && m_controller.GetLeftBumper() == true;

        /*
         * Note Feeding Function
         * When you press down the 'B' Button on the Xbox controller the
         * Feeding Motor on the bottom of the robot goes into reverse
         * at -0.3 speed and when you press down the 'X' Button on the
         * Xbox controller the Feeding Motor on the bottom of the robot
         * goes forward at 0.3 speed, when none of the buttons are being
         * pressed the Feeding Motor on the bottom of the robot is set
         * to turn off.
         */
        if (m_controller.GetButtonB()) {
            m_LauncherSubsystem.FeedLauncher(-0.3);
        } else if (m_controller.GetButtonX()) {
            m_LauncherSubsystem.FeedLauncher(0.3);;
        } else {
            m_LauncherSubsystem.FeedLauncher(0.00);
        }

        /*--------------------------------------------------------- */


        //TODO: Remove this when finished
        /*
         * Launching and Launching Reverse Function
         * When the 'Y' Button on the Xbox controller is being pressed
         * the 2 top wheel motors on the launching carriage will go forward
         * at 1.00 speed and when the Left Bumper on the Xbox controller
         * is being pressed the top two motors on the launching carriage
         * are set to go into reverse at -0.40 speed and the guiding motor
         * on the bottom of the launch carriage is set to go into reverse
         * at -0.40 or if the left bumper on the Xbox controller is not
         * being pressed both the launching motors and guiding motors
         * will be set to off, or else if the 'Y' button on the Xbox
         * controller is not being pressed then both the Launching
         * and Guiding motors will be set to off
         */



        /*
         * If the 'Y' Button on the Xbox conroller is being pressed
         * then the two top launching motors are being set to 1.00
         * Percent speed.
         */
        if (m_controller.GetButtonY()) {
            m_LauncherSubsystem.Launch(1.00);
        } 

        /*
         * if the Left Bumper on the Xbox controller is being
         * pressed the top two lauching motors are being set to
         * -0.40 and the bottom launching motor is set to -0.40
         * speed.
         */
        else if (reversing == false) {
            if (m_controller.GetLeftBumper()) {
                m_LauncherSubsystem.Launch(-0.40);
                m_LauncherSubsystem.Guiding(0.40);
                reversing = true;
            } else {
                m_LauncherSubsystem.Launch(0.00);
                m_LauncherSubsystem.Guiding(0.00);
            }
        } 
        
        //TODO : Comment this
        else {
            m_LauncherSubsystem.Launch(0.00);
            m_LauncherSubsystem.Guiding(0.00);
        }


        //TODO : Comment this
        if (m_controller.GetButtonA()) {
            m_LauncherSubsystem.Guiding(-1.00);
            reversing = false;
        } 
        
        //TODO : Comment this
        else {
            m_LauncherSubsystem.Guiding(0.00);
            reversing = false;
        }

        /*
         * Fail Safe Function
         * If the Launching Motors on the robot are not turning off this function
         * here is set to turn on and turn the motors off without the code throwing
         * out an error.
         */
        if (reversing == true) {
            m_LauncherSubsystem.Launch(0.00);
        }

        /* ---------------------------------------------------------------- */
    }
    

    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    public boolean isFinished() {
        return false;
    }
}