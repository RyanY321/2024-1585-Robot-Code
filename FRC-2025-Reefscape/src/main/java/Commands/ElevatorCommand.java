package Commands;

import edu.wpi.first.wpilibj2.command.Command;
import Subsystems.IO;

import Subsystems.Elevator;

public class ElevatorCommand extends Command {
    public final Elevator m_elevatorSubsystem;
    private final IO m_controller;

    private boolean isFinished = false;

    public ElevatorCommand(Elevator elevatorSubsystem, IO controller) {
        m_elevatorSubsystem = elevatorSubsystem;
        m_controller = controller;
        addRequirements(m_elevatorSubsystem);
        addRequirements(controller);
    }

    public void initialize() {
        // System.out.println("Elevator Command initialized...");
    }

    @Override
    public void execute() {
        ElevatorDrive();

        isFinished = true;
    }

    private void ElevatorDrive() {
        if (m_controller.DPadUp()) {
            m_elevatorSubsystem.ElevatorGuide(.5);
        } else if (m_controller.DPadDown()) {
            m_elevatorSubsystem.ElevatorGuide(-.5);
        } else {
            m_elevatorSubsystem.ElevatorGuide(0);
        }
    }

    public void end(boolean interrupted) {
    }

    public boolean isFinished() {
        return false;
    }
}