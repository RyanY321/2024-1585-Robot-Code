package Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase
{
    private final PWMSparkMax m_shooter;

    public Shooter(int 
    ShooterMotorChannel)
    {
        m_shooter = new PWMSparkMax(ShooterMotorChannel);
    }

    public Command ShooterCommand() {
        return run(
            () -> {
                this.Shoot(0);
            });
        }

    public Command StopShooterCommand() {
        return runOnce(
            () -> {
                this.StopShoot();
            });
    }

    public Command ReverseShooterCommand() {
        return run(
            () -> {
                this.ReverseShoot(0);
            });
    }

    public Command AmpShooterCommand() {
    return run(
        () -> {
            this.AmpShooting();
        });
    }

    public void Shoot(double speed)
    {
        System.out.println("Turning On Shooter...");
        m_shooter.set(speed);
    }

    public void ReverseShoot(double speed)
    {
        System.out.println("Reversing The Shooter...");
        m_shooter.set(speed);
    }

    private void StopShoot()
    {
        System.out.println("Stopping The Shooter...");
        m_shooter.set(0.00);
    }

    private void AmpShooting()
    {
        System.out.println("Shooting At Amp...");
        m_shooter.set(0.50);
    }

    public void periodic() {
      // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
    
}