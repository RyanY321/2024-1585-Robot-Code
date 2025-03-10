package Commands;

import edu.wpi.first.wpilibj2.command.Command;
import Subsystems.IO;

import Subsystems.Algae;

public class AlgaeCommand extends Command {
    public final Algae m_algaeSubsystem;
    private final IO m_controller;

    private boolean isFinished = false;

    public AlgaeCommand(Algae algaeSubsystem, IO controller) {
        m_algaeSubsystem = algaeSubsystem;
        m_controller = controller;
        addRequirements(algaeSubsystem);
        // addRequirements(controller);
    }

    public void initialize() {
        // System.out.println("Algae Command initialized...");
    }

    @Override
    public void execute() {
        Shooter();

        isFinished = true;
    }

    private void Shooter() {
        if (m_controller.GetRightBumper()) {
            m_algaeSubsystem.ShootAlgae(.8);
        } else if (m_controller.GetLeftBumper()) {
            m_algaeSubsystem.ShootAlgae(-.8);
        } else {
            m_algaeSubsystem.ShootAlgae(0);
        }
    }

    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    public boolean isFinished() {
        return false;
    }
}
