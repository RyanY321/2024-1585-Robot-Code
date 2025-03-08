package Commands;

import Subsystems.Coral;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CoralAutoCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Coral m_coralSubsystem;
    private boolean m_coral = false;
    private boolean m_isFinished = false;
    private double m_groupASpeed;
    private double m_groupBSpeed;

    public CoralAutoCommand(Coral coralSubsystem, double GroupA, double GroupB) {
        m_coralSubsystem = coralSubsystem;
        m_groupASpeed = GroupA;
        m_groupBSpeed = GroupB;

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
            m_coralSubsystem.AutoGuideCoral(m_groupASpeed, m_groupBSpeed);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
