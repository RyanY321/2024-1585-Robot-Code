package Commands;

import Subsystems.Algae;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AlgaeAutoCommand extends Command {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Algae m_algaeSubsystem;
    private boolean m_algae = false;
    private boolean m_isFinished = false;
    private double m_shootMotorSpeed;

    public AlgaeAutoCommand(Algae algaeSubsystem, double shootMotorSpeed) {
        m_algaeSubsystem = algaeSubsystem;
        m_shootMotorSpeed = shootMotorSpeed;

        addRequirements(algaeSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Algae Auto Command Has Been Initialized...");
    }

    @Override
    public void execute() {
        if (m_algae = true) {
            System.out.println("Engaging The Auto Algae Command...");
            m_algaeSubsystem.AutoShoot(m_shootMotorSpeed);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
