package Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
    // PWM SPARK MAX
    private final Talon m_launchMotorA;
    private final Talon m_launchMotorB;

    // CAN SPARK MAX
    private final CANSparkMax m_guidingMotor;
    private final CANSparkMax m_feedingMotor;

    private Launcher m_controller;

    public Launcher(int FrontLaunchMotorChannelPWM, int BackLaunchMotorChannelPWM, int LiftMotorChannelCAN,
            int FeederMotorChannelCAN) {
        // PWM TALON SR
        m_launchMotorA = new Talon(FrontLaunchMotorChannelPWM);
        m_launchMotorB = new Talon(BackLaunchMotorChannelPWM);

        // CAN SPARK MAX
        m_feedingMotor = new CANSparkMax(LiftMotorChannelCAN, CANSparkLowLevel.MotorType.kBrushed);
        m_guidingMotor = new CANSparkMax(FeederMotorChannelCAN, CANSparkLowLevel.MotorType.kBrushed);

        // Set Inversions
        m_launchMotorA.setInverted(true);
        m_launchMotorB.setInverted(true);
        m_feedingMotor.setInverted(true);

        // Set Logic
        m_launchMotorB.addFollower(m_launchMotorA);
    }

    public Command AutoLaunchCommand(double launchMotorAutoSpeed, double feederMotorAutoSpeed) {
        return run(
                () -> {
                    this.AutoLaunch(launchMotorAutoSpeed, feederMotorAutoSpeed);
                });
    }

    public Command AutoFeedCommand(double feedMotorAutoSpeed) {
        return run(
                () -> {
                    this.AutoFeed(feedMotorAutoSpeed);
                });
    }

    public Command LaunchCommand(double launchMotorSpeed) {
        return run(
                () -> {
                    this.Launch(launchMotorSpeed);
                });
    }

    public Command ReverseCommand() {
        return run(
                () -> {
                    this.Reverse();
                });

    }

    public Command GuidingCommand(double guidingMotorSpeed) {
        return run(
                () -> {
                    this.Guiding(guidingMotorSpeed);
                });
    }

    public Command FeedLauncherCommand(double feedMotorSpeed) {
        return run(
                () -> {
                    this.FeedLauncher(feedMotorSpeed);
                });

    }

    public void Reverse() {
        m_launchMotorA.set(-0.40);
        m_guidingMotor.set(-0.40);
    }

    public void FeedLauncher(double liftMotorSpeed) {
            m_feedingMotor.set(liftMotorSpeed);
    }

    public void Launch(double launchMotorSpeed) {
        m_launchMotorA.set(launchMotorSpeed);
    }

    public void Guiding(double guidingMotorSpeed) {
        m_guidingMotor.set(guidingMotorSpeed);
    }

    public void AutoLaunch(double launchMotorSpeed, double FeederMotorAutoSpeed) {
        m_launchMotorA.set(launchMotorSpeed);
        m_guidingMotor.set(FeederMotorAutoSpeed);

    }

    public void AutoFeed(double feedMotorAutoSpeed) {
        FeedLauncher(feedMotorAutoSpeed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

}