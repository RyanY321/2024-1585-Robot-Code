package Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
    private final PWMSparkMax m_frontMotor;
    private final PWMSparkMax m_backMotor;
    private final PWMSparkMax m_spoolMotor;

    public final Spark m_liftMotor;
    public DigitalInput m_lifterStop0;
    public DigitalInput m_lifterStop1;
    public boolean m_lifterStopped = false;

    public Launcher(int FrontLaunchMotorChannel, int BackLaunchMotorChannel, int LiftMotorChannel, int SpoolMotorChannel) {
        m_frontMotor = new PWMSparkMax(FrontLaunchMotorChannel);
        m_backMotor = new PWMSparkMax(BackLaunchMotorChannel);
        m_spoolMotor = new PWMSparkMax(SpoolMotorChannel);
        m_liftMotor = new Spark(LiftMotorChannel);
        m_lifterStop0 = new DigitalInput(0);
        m_lifterStop1 = new DigitalInput(1);
    }

    public Command AutoLaunchCommand(double frontLaunchMotorAutoSpeed, double backLaunchMotorAutoSpeed, double spoolMotorAutoSpeed) {
        return run(
                () -> {
                    this.AutoLaunch(frontLaunchMotorAutoSpeed, backLaunchMotorAutoSpeed, spoolMotorAutoSpeed);
                });
    }

    public Command LaunchCommand(double launchSpeed, double spoolSpeed) {
        return run(
                () -> {
                    this.Launch(launchSpeed, spoolSpeed);
                });
    }

    public Command LiftLauncherCommand(double liftSpeed) {
        return run(
                () -> {
                    this.LiftLauncher(liftSpeed);
                });

    }

    public void LiftLauncher(double liftSpeed) {
        // System.out.println(m_lifterStopped);
        if (!m_lifterStopped) {
            // System.out.println("Engaging The Lift Motor At % Speed");
            m_liftMotor.set(liftSpeed);
        }
    }

    public void Launch(double launchSpeed, double spoolSpeed) {
        m_spoolMotor.set(spoolSpeed);
        m_frontMotor.set(launchSpeed);
        m_backMotor.set(launchSpeed);
    }

    public void AutoLaunch(double FrontMotorAutoSpeed, double BackMotorAutoSpeed, double SpoolMotorAutoSpeed) {
        m_frontMotor.set(FrontMotorAutoSpeed);
        m_backMotor.set(BackMotorAutoSpeed);
        m_spoolMotor.set(SpoolMotorAutoSpeed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

}