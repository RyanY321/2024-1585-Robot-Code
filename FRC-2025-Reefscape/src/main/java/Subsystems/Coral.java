package Subsystems;

import Subsystems.IO;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import Commands.CoralAutoCommand;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Coral extends SubsystemBase {
    private final SparkMax m_motorGroupA;
    private final SparkMax m_motorGroupB;

    private Coral m_controller;
    
    public Coral(int MotorGroupAChannelCAN, int MotorGroupBChannelCAN) {
        // Create New Spark Max Objects
        m_motorGroupA = new SparkMax(MotorGroupAChannelCAN, SparkLowLevel.MotorType.kBrushed);
        m_motorGroupB = new SparkMax(MotorGroupBChannelCAN, SparkLowLevel.MotorType.kBrushed);
        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig motorGroupAConfig = new SparkMaxConfig();
        SparkMaxConfig motorGroupBConfig = new SparkMaxConfig();

        config
            .idleMode(IdleMode.kBrake);

        motorGroupAConfig
            .inverted(false);

        m_motorGroupA.configure(motorGroupAConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_motorGroupB.configure(motorGroupBConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    //Crate A New Command
    public Command GroupAGuide(double GroupASpeed) {
        return run(
                () -> {
                    this.GroupA(GroupASpeed);
                });
    }

    public void GroupA(double GroupASpeed) {
        m_motorGroupA.set(GroupASpeed);
    }

    public Command GroupBGuide(double GroupBSpeed) {
        return run(
                () -> {
                    this.GroupB(GroupBSpeed);
                });
    }

    public void GroupB(double GroupBSpeed) {
        m_motorGroupB.set(GroupBSpeed);
    }


    public Command AutoCoralGuide(double CoralGuideGroupASpeed, double CoralGuideGroupBSpeed) {
        return run(
                () -> {
                    this.AutoGuideCoral(CoralGuideGroupASpeed, CoralGuideGroupBSpeed);
                });
    }

    public void AutoGuideCoral(double CoralGuideGroupASpeed, double CoralGuideGroupBSpeed) {
        m_motorGroupA.set(CoralGuideGroupASpeed);
        m_motorGroupB.set(CoralGuideGroupBSpeed);
    }

    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
