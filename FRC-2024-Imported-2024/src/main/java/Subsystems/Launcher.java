package Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
    private final PWMSparkMax m_frontMotor;
    private final PWMSparkMax m_backMotor;
    private final Spark m_liftMotor;

    public Launcher(int FrontLaunchMotorChannel, int BackLaunchMotorChannel, int LiftMotorChannel) {
        m_frontMotor = new PWMSparkMax(FrontLaunchMotorChannel);
        m_backMotor = new PWMSparkMax(BackLaunchMotorChannel);
        m_liftMotor = new Spark(LiftMotorChannel);
    }

    public Command AutoLaunchCommand(double frontLaunchMotorSpeed, double backLaunchMotorSpeed) {
        return run(
                () -> {
                    this.AutoLaunch(frontLaunchMotorSpeed, backLaunchMotorSpeed);
                });
    }

    public Command LaunchCommand(double speed) {
        return run(
                () -> {
                    this.Launch(speed);
                });
    }

    public Command LiftLauncherCommand(double speed) {
        return run(
                () -> {
                    this.LiftLauncher(speed);
                });
    }

    public void LiftLauncher(double speed) {
        System.out.println("Engaging The Lift Motor At % Speed");
        m_liftMotor.set(speed);
    }

    public void Launch(double speed) {
        System.out.println("Turning On The Launcher...");
        m_frontMotor.set(speed);
        Commands.waitSeconds(1.5);
        m_backMotor.set(speed);
    }

    public void AutoLaunch(double FrontMotorSpeed, double BackMotorSpeed) {
        m_frontMotor.set(FrontMotorSpeed);
        m_backMotor.set(BackMotorSpeed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

}