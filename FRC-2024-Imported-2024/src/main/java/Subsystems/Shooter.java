package Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private final PWMSparkMax m_frontMotor;
    private final PWMSparkMax m_backMotor;
    private final Spark m_liftMotor;

    public Shooter(int FrontShootMotorChannel, int BackShootMotorChannel, int LiftMotorChannel) {
        m_frontMotor = new PWMSparkMax(FrontShootMotorChannel);
        m_backMotor = new PWMSparkMax(BackShootMotorChannel);
        m_liftMotor = new Spark(LiftMotorChannel);
    }

    public Command ShootCommand(double speed) {
        return run(
                () -> {
                    this.Shoot(speed);
                });
    }

    public Command LiftCommand(double speed) {
        return run(
                () -> {
                    this.Lift(speed);
                });
    }

    public void Lift(double speed) {
        System.out.println("Engaging The Lift Motor At % Speed");
        m_liftMotor.set(speed);
    }

    public void Shoot(double speed) {
        System.out.println("Shooter Starting At % Speed:");
        System.out.println(speed);
        m_frontMotor.set(speed);
        m_backMotor.set(speed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

}