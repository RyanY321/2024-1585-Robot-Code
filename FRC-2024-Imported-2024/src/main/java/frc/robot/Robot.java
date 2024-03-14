// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  public static final String programOneAuto = "Program One";
  public static final String programTwoAuto = "Program Two";
  public static final String programThreeAuto = "Program Three";
  public static final String programFourAuto = "Program Four";
  public static final String programFiveAuto = "Program Five";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Field2d m_field;

  public boolean isAuto = false;

  /**
   * @implNote Robot Constructor 
   */
  public Robot() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotInit() {
    // Populates Autonoumous Dropdown Menu
    m_chooser.setDefaultOption("Backup", programOneAuto);
    m_chooser.addOption("Shoot, Backup", programTwoAuto);
    m_chooser.addOption("Shoot, Left Backup", programThreeAuto);

    if (Robot.isReal()) {
      // Shuffleboard.getTab("Cresendo").add(m_robotContainer.m_gyro.GetGyro());
      // Shuffleboard.getTab("Cresendo").add(m_robotContainer.m_gyro.GetLauncherAngleGyro());
      SmartDashboard.putData("Robot Position", m_robotContainer.m_gyro.GetGyro());
      SmartDashboard.putData("Launcher Posititon", m_robotContainer.m_gyro.GetLauncherAngleGyro());
    }

    // Used for robot SIM
    m_field = new Field2d();
    SmartDashboard.putData("Field", m_field);
    // Shuffleboard.getTab("Cresendo").add(m_chooser);
    SmartDashboard.putData("Auto Selector", m_chooser);

  }

  @Override
  public void teleopPeriodic() {
  }

  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
    m_autonomousCommand = m_robotContainer.getAutonomousCommand(m_autoSelected);

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {

  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    m_robotContainer.SimPeriodic();
    m_field.setRobotPose(m_robotContainer.GetPoseMeters());

  }

}
