package Commands;

import Subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

public class ShooterAutoCommand extends Command 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_ShooterSubsystem;
    private boolean m_isFinished = false;
    private boolean m_highShooter = false;
    private double m_speed;

    public ShooterAutoCommand(Shooter shooterSubsystem, double speed, boolean highShooter) {
        m_ShooterSubsystem = shooterSubsystem;
        m_speed = speed;
        m_highShooter = false;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Shooter Auto Command Has Been Initialized...");
    }

    @Override
    public void execute() {
        if (m_highShooter = true)
        {
            System.out.println("Auto Shooter Was Set To High...");
            m_ShooterSubsystem.Shoot(m_speed);
        }
    }
}
