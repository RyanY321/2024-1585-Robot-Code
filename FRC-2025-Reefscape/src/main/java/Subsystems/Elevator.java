package Subsystems;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import Commands.ElevatorCommand;

public class Elevator extends SubsystemBase {
    private final SparkMax m_elevatorMotor;

    private Elevator m_controller;

    public Elevator(int ElevatorMotorChannelCAN) {
        m_elevatorMotor = new SparkMax(ElevatorMotorChannelCAN, SparkLowLevel.MotorType.kBrushed);

        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig m_elevatorMotorConfig = new SparkMaxConfig();

        config
            .idleMode(IdleMode.kBrake);

        m_elevatorMotorConfig
            .inverted(false);

        m_elevatorMotor.configure(m_elevatorMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public Command ElevatorGuide(double ElevatorSpeed) {
        return run(
                () -> {
                    this.GuideElevator(ElevatorSpeed);
                });
    }

    public void GuideElevator(double ElevatorSpeed) {
        m_elevatorMotor.set(ElevatorSpeed);
    }

    public void periodic() {

    }

    public void simulationPeriodic() {
        
    }
}
