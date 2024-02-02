package Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter {
    private final PWMSparkMax m_shooter;

    public Shooter(int 
    ShooterMotorChannel)
    {
        m_shooter = new PWMSparkMax(ShooterMotorChannel);
    }

    public Command ShootCommand() {
        return run(
            () -> {
                this.Shoot();
            });
    }

        public Command PickUpCommand() {
        return run(
            () -> {
                this.PickUp();
            });
    }

        public Command AmpShootCommand() {
        return run(
            () -> {
                this.AmpShoot();
            });
    }

    public void Shoot()
    {
        System.out.println("Turning On Shooter...");
        m_shooter.set(0.50);
    }

    private void PickUp()
    {
        System.out.println("Turning On PickUp..");
        m_shooter.set(-0.50);
    }

    private void AmpShoot()
    {
        System.out.println("Shooting At Amp...");
        m_shooter.set(0.50);
    }
}