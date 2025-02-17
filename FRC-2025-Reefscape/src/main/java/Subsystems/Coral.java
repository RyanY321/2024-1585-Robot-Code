package Subsystems;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Coral extends SubsystemBase {
    private final SparkMax m_beltMotor;

    private Coral m_controller;
    
    public Coral(int BeltMotorChannelCAN) {
        // Create New Spark Max Objects
        m_beltMotor = new SparkMax(BeltMotorChannelCAN, SparkLowLevel.MotorType.kBrushed);
        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig m_beltMotorConfig = new SparkMaxConfig();

        // Creating The Invert Variables For Spark Max


        // Setting Config Parameters

        config
            .idleMode(IdleMode.kBrake);

        m_beltMotorConfig
            .inverted(false);

        m_beltMotor.configure(m_beltMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    //Crate A New Command
    public Command CoralGuide(double CoralGuideSpeed) {
        return run(
                () -> {
                    this.GuideCoral(CoralGuideSpeed);
                });
    }

    public void GuideCoral(double CoralGuideSpeed) {
        m_beltMotor.set(CoralGuideSpeed);
    }


    public Command AutoCoralGuide(double CoralGuideAutoSpeed) {
        return run(
                () -> {
                    this.AutoGuideCoral(CoralGuideAutoSpeed);
                });
    }

    public void AutoGuideCoral(double CoralGuideAutoSpeed) {
        m_beltMotor.set(CoralGuideAutoSpeed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
