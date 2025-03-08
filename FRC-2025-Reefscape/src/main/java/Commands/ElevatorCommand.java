package Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import Subsystems.IO;

import Subsystems.Elevator;

public class ElevatorCommand extends Command {
    public final Elevator m_elevatorSubsystem;
    private final IO m_controller;

    private boolean isFinished = false;
    private double m_distance = 0;

    public ElevatorCommand(Elevator elevatorSubsystem, IO controller) {
        m_elevatorSubsystem = elevatorSubsystem;
        m_controller = controller;
        addRequirements(elevatorSubsystem);
    }

    public void initialize() {
        // System.out.println("Elevator Command Initialized...");
    }

    @Override
    public void execute() {

        m_elevatorSubsystem.SetHeight();
        SmartDashboard.putNumber("Elevator Height", m_elevatorSubsystem.GetHeight());
        SmartDashboard.putBoolean("Top Limit", m_elevatorSubsystem.GetTopLimit());
        SmartDashboard.putBoolean("Bottom Limit", m_elevatorSubsystem.GetBottomLimit());

        ElevatorDrive();

        isFinished = true;
    }

    private void ElevatorDrive() {

        if (m_controller.DPadUp()) {
            m_elevatorSubsystem.GuideElevator(1.00);
        } else if (m_controller.DPadDown()) {
            m_elevatorSubsystem.GuideElevator(-1.00);
        } else {
            m_elevatorSubsystem.GuideElevator(0.05);
        }
    }

    public void end(boolean interrupted) {
    }

    public boolean isFinished() {
        return false;
    }
}