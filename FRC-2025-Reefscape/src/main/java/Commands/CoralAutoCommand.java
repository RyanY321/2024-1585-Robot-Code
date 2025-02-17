package Commands;

import Subsystems.Coral;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CoralAutoCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Coral m_coralSubsystem;
    private boolean m_coral = false;
    private boolean m_isFinished = false;
    private double m_beltMotorSpeed;

    public CoralAutoCommand(Coral coralSubsystem, double beltMotorSpeed) {
        m_coralSubsystem = coralSubsystem;
        m_beltMotorSpeed = beltMotorSpeed;

        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Coral Auto Command Has Been Initialized...");
    }

    @Override
    public void execute() {
        if (m_coral = true) {
            System.out.println("Engaging The Auto Coral Command...");
            m_coralSubsystem.AutoGuideCoral(m_beltMotorSpeed);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
