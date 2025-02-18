package Commands;

import Subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.Command;

public class ElevatorAutoCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Elevator m_elevatorSubsystem;
    private boolean m_elevator = false;
    private boolean m_isFinished = false;
    private double m_elevatorSpeed;

    public ElevatorAutoCommand(Elevator elevatorSubsystem, double elevatorSpeed) {
        m_elevatorSubsystem = elevatorSubsystem;
        m_elevatorSpeed = elevatorSpeed;

        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Elevator Auto Command Has Been Initialized...");
    }

    @Override
    public void execute() {
        if (m_elevator = true) {
            System.out.println("Engaging The Auto Elevator Command...");
            m_elevatorSubsystem.AutoGuideElevator(m_elevatorSpeed);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
