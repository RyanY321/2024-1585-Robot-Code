package Subsystems;

import Subsystems.IO;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import Subsystems.IO;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import Commands.ElevatorCommand;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotController;

public class Elevator extends SubsystemBase {
    private final SparkMax m_elevatorMotor;
    private final AnalogInput m_elevatorHeight;
    private final DigitalInput m_heightLimitTop;
    private final DigitalInput m_heightLimitBottom;

    private double voltageFactor = 0;
    private double m_elevatorHeightCm = 0;

    private Elevator m_controller;

    public Elevator(int ElevatorMotorChannelCAN, 
                    int elevatorHeightPin, 
                    int heightLimitPinTop,
                    int heightLimitPinBotton) {
        m_elevatorMotor = new SparkMax(ElevatorMotorChannelCAN, SparkLowLevel.MotorType.kBrushed);

        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig m_elevatorMotorConfig = new SparkMaxConfig();

        m_elevatorHeight = new AnalogInput(elevatorHeightPin);
        m_heightLimitTop = new DigitalInput(heightLimitPinTop);
        m_heightLimitBottom = new DigitalInput(heightLimitPinBotton);


        config
            .idleMode(IdleMode.kBrake);

        m_elevatorMotorConfig
            .inverted(false);

        m_elevatorMotor.configure(m_elevatorMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public Command ElevatorGuide(double ElevatorSpeed) {
        return run(
                () -> {
                    this.GetHeight();
                    this.GuideElevator(ElevatorSpeed);
                });
    }

    public void GuideElevator(double ElevatorSpeed) {

        //Top limit switch hit
        if(m_heightLimitTop.get())
        {
            m_elevatorMotor.set(0);
            return;
        }

        //bottom limit switch hit
        else if(m_heightLimitBottom.get())
        {
            m_elevatorMotor.set(0);
            return;
        }

        m_elevatorMotor.set(ElevatorSpeed);
    }

    public Command AutolevatorGuide(double ElevatorAutoSpeed) {
        return run(
                () -> {
                    this.AutoGuideElevator(ElevatorAutoSpeed);
                });
    }

    public void AutoGuideElevator(double ElevatorAutoSpeed) {
        m_elevatorMotor.set(ElevatorAutoSpeed);
    }

    public double GetHeight()
    {
        return m_elevatorHeightCm;
    }

    public boolean GetTopLimit()
    {
        return m_heightLimitTop.get();
    }

    public boolean GetBottomLimit()
    {
        return m_heightLimitBottom.get();
    }


    public void SetHeight()
    {
        voltageFactor = 5/RobotController.getVoltage5V();
        double rangeDistance = m_elevatorHeight.getValue()*voltageFactor*0.125;
        m_elevatorHeightCm = Math.round(rangeDistance);
    }

    public void periodic() {

    }

    public void simulationPeriodic() {
        
    }
}
