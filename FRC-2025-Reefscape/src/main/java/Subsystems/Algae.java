package Subsystems;

import Subsystems.IO;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Algae extends SubsystemBase{
    private final SparkMax m_shootMotor;

    private Algae m_controller;

    public Algae(int ShootMotorChannelCAN) {
        m_shootMotor = new SparkMax(ShootMotorChannelCAN, SparkLowLevel.MotorType.kBrushed);

        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig m_shootMotorConfig = new SparkMaxConfig();

        config
            .idleMode(IdleMode.kBrake);

        m_shootMotorConfig
            .inverted(false);

        m_shootMotor.configure(m_shootMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

     public Command Shoot(double ShootSpeed) {
        return run(
                () -> {
                    this.ShootAlgae(ShootSpeed);
                });
    }

    public void ShootAlgae(double ShootSpeed) {
        m_shootMotor.set(ShootSpeed);
    }

    public Command AutoShoot(double ShootAutoSpeed) {
        return run(
                () -> {
                    this.AutoShootAlgae(ShootAutoSpeed);
                });
    }

    public void AutoShootAlgae(double ShootAutoSpeed) {
        m_shootMotor.set(ShootAutoSpeed);
    }


    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
