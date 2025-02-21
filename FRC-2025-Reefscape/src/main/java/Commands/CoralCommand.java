package Commands;

import edu.wpi.first.wpilibj2.command.Command;
import Subsystems.IO;

import Subsystems.Coral;

public class CoralCommand extends Command {
    public final Coral m_coralSubsystem;
    private final IO m_controller;

    private boolean isFinished = false;

    public CoralCommand(Coral coralSubsystem, IO controller) {
        m_coralSubsystem = coralSubsystem;
        m_controller = controller;
        addRequirements(coralSubsystem);
        addRequirements(controller);
    }

    public void initialize() {
        // System.out.println("Coral Command initialized...");
    }

    @Override
    public void execute() {
        CoralBelt();

        isFinished = true;
    }

    private void CoralBelt() {
        if (m_controller.GetButtonY()) {
            m_coralSubsystem.GuideCoral(.5);
        } else if (m_controller.GetButtonA()) {
            m_coralSubsystem.GuideCoral(-.5);
        } else {
            m_coralSubsystem.GuideCoral(0);
        }
    }


    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    public boolean isFinished() {
        return false;
    }
}
