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
    private final SparkMax m_beltMotorA;
    private final SparkMax m_beltMotorB;

    private Coral m_controller;
    
    @SuppressWarnings("deprecation")
    public Coral(int BeltMotorAChannelCAN, int BeltMotorBChannelCAN) {
        // Create New Spark Max Objects
        m_beltMotorA = new SparkMax(BeltMotorAChannelCAN, SparkLowLevel.MotorType.kBrushed);
        m_beltMotorB = new SparkMax(BeltMotorBChannelCAN, SparkLowLevel.MotorType.kBrushed);
        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig m_beltMotorBConfig = new SparkMaxConfig();

        // Creating The Invert Variables For Spark Max
        m_beltMotorA.setInverted(false);
        m_beltMotorB.setInverted(false);

        // Setting Config Parameters

        config
            .idleMode(IdleMode.kBrake);

        m_beltMotorBConfig
            .follow(m_beltMotorA);

        m_beltMotorB.configure(m_beltMotorBConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    //Crate A New Command
    public Command CoralGuide(double CoralGuideSpeed) {
        return run(
                () -> {
                    this.GuideCoral(CoralGuideSpeed);
                });
    }

    public void GuideCoral(double CoralGuideSpeed) {
        m_beltMotorA.set(CoralGuideSpeed);
    }


    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
