package Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
    // PWM SPARK MAX
    private final Talon m_frontMotor;
    private final Talon m_backMotor;

    // CAN SPARK MAX
    private final CANSparkMax m_feederMotor;
    public final CANSparkMax m_liftMotor;

    private Launcher m_controller;
    public DigitalInput m_lifterStop0;
    public DigitalInput m_lifterStop1;
    public boolean m_lifterStopped = false;

    public Launcher(int FrontLaunchMotorChannelPWM, int BackLaunchMotorChannelPWM, int LiftMotorChannelCAN, int FeederMotorChannelCAN) {
        // PWM TALON SR
        m_frontMotor = new Talon(FrontLaunchMotorChannelPWM);
        m_backMotor = new Talon(BackLaunchMotorChannelPWM);

        // CAN SPARK MAX
        m_liftMotor = new CANSparkMax(LiftMotorChannelCAN, CANSparkLowLevel.MotorType.kBrushed);
        m_feederMotor = new CANSparkMax(FeederMotorChannelCAN, CANSparkLowLevel.MotorType.kBrushed);

        // Set Inversions
        m_frontMotor.setInverted(true);
        m_backMotor.setInverted(true);
        m_liftMotor.setInverted(true);

        // Construct DIO
        m_lifterStop0 = new DigitalInput(0);
        m_lifterStop1 = new DigitalInput(1);
    }

    public Command AutoLaunchCommand(double frontLaunchMotorAutoSpeed, double backLaunchMotorAutoSpeed, double feederMotorAutoSpeed) {
        return run(
                () -> {
                    this.AutoLaunch(frontLaunchMotorAutoSpeed, backLaunchMotorAutoSpeed, feederMotorAutoSpeed);
                });
    }

    public Command AutoLiftCommand(double liftMotorAutoSpeed) {
        return run(
                () -> {
                    this.AutoLift(liftMotorAutoSpeed);
                });
    }

    public Command LaunchCommand(double frontLaunchMotorSpeed, double backLaunchMotorSpeed) {
        return run(
                () -> {
                    this.Launch(frontLaunchMotorSpeed, backLaunchMotorSpeed);
                });
    }

    public Command ReverseCommand() {
        return run(
                () -> {
                    this.Reverse();
                });

    }

    public Command LaunchCommand(double feederMotorSpeed) {
        return run(
                () -> {
                    this.Feeder(feederMotorSpeed);
                });
    }

    public Command LiftLauncherCommand(double liftMotorSpeed) {
        return run(
                () -> {
                    this.LiftLauncher(liftMotorSpeed);
                });

    }

    public void Reverse() {
        m_frontMotor.set(-0.40);
        m_backMotor.set(-0.40);
    }

    public void LiftLauncher(double liftMotorSpeed) {

        //System.out.println(m_lifterStopped);
        if (!m_lifterStopped) {
            // System.out.println("Engaging The Lift Motor At % Speed");
            m_liftMotor.set(liftMotorSpeed);
        }
    }

    public void Launch(double frontLaunchMotorSpeed, double backLaunchMotorSpeed) {
        m_frontMotor.set(frontLaunchMotorSpeed);
        m_backMotor.set(backLaunchMotorSpeed);
    }

    public void Feeder(double feederMotorSpeed) {
        m_feederMotor.set(feederMotorSpeed);
    }

    public void AutoLaunch(double FrontMotorAutoSpeed, double BackMotorAutoSpeed, double FeederMotorAutoSpeed) {
        m_frontMotor.set(FrontMotorAutoSpeed);
        m_backMotor.set(BackMotorAutoSpeed);
        m_feederMotor.set(FeederMotorAutoSpeed);

    }

    public void AutoLift(double liftMotorAutoSpeed) {
        LiftLauncher(liftMotorAutoSpeed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

}