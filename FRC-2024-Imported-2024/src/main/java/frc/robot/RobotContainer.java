package frc.robot;

import Subsystems.Drive;
import Subsystems.Gripper;
import Subsystems.IO;
import Subsystems.Shooter;

import Commands.DriveCommand;
import Commands.GripperAutoCommand;
import Commands.GripperCommand;
import Commands.ArmAutoCommand;
import Commands.ShooterCommand;
import Commands.DriveAutoCommand;
// import Subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;

public class RobotContainer {
  private IO m_controller = new IO();
  private Drive m_driveController = new Drive(0, 1);
  private Pnumatics m_pnumatics = new Pnumatics();
  private Gripper m_gripper = new Gripper(m_pnumatics);
  // private Arm m_arm = new Arm(5, 3);
  private Shooter m_shooter = new Shooter(2);

  private ShooterCommand m_ShooterCommand = new ShooterCommand(m_shooter, m_controller);
  // private ArmCommand m_ArmCommand = new ArmCommand(m_arm, m_controller);
  private DriveCommand m_DriveCommand = new DriveCommand(m_driveController, m_controller);

  //private Auto m_auto = new Auto(m_driveController);

  private SequentialCommandGroup m_progOneAuto = new SequentialCommandGroup();
  private SequentialCommandGroup m_progTwoAuto = new SequentialCommandGroup();


  private double rightAutoSpeed = .70;
  private double leftAutoSpeed = rightAutoSpeed * .98;

  //-------Simulator Variables -----///
  //TODO: put these in a seperate subsystem
  private AnalogGyro gyro;
  private AnalogGyroSim m_gyroSim;

  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private EncoderSim m_leftEncoderSim;
  private EncoderSim m_rightEncoderSim;

  private static final double kWheelRadius = 0.0508; // meters
  private static final int kEncoderResolution = 4096;

  
  // private DifferentialDrivetrainSim m_driveSim = DifferentialDrivetrainSim.createKitbotSim(
  //   KitbotMotor.kDoubleFalcon500PerSide, // 2 CIMs per side.
  //   KitbotGearing.k10p71, // 10.71:1
  //   KitbotWheelSize.kSixInch, // 6" diameter wheels.
  //   null // No measurement noise.
// );

private DifferentialDriveOdometry m_odometry;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the controller bindings
    configureBindings();

    //Start camera server for teleop
    CameraServer.startAutomaticCapture();


    // Schedule the drive controller to move
    m_driveController.setDefaultCommand(m_DriveCommand);
    // m_arm.setDefaultCommand(m_ArmCommand);
    m_shooter.setDefaultCommand((m_ShooterCommand));

    //------------Setup autonomous commands -----------------
    m_progOneAuto.addCommands(

      //Lower Arm at a speed of .25
      // new ArmAutoCommand(m_arm,-.15),

      //Allow the arm to lower for .25 seconds
      new WaitCommand(.25),
      
      //Stop the arm from lowering anymore
      // new ArmAutoCommand(m_arm,0),

      //Openm the gripper
      new GripperAutoCommand(m_gripper),

      //wait .5 seconds for gripper to open
      new WaitCommand(.25),

      //Engange the robot drive with given speeds
      new DriveAutoCommand(m_driveController, -leftAutoSpeed,-rightAutoSpeed),

      //Drive robot for 7.6 seconds
      new WaitCommand(6.0),

      //Stop the robot
      new DriveAutoCommand(m_driveController, 0, 0)
    );
  }
 
  // Configures all button mapping bindings for Xbox Controller
  private void configureBindings() {

    //Gripper Close Button
    // m_controller.GetOpenGripperBtn().whileTrue(m_gripper.openGripperCommand());
    // m_controller.GetOpenGripperBtn().onFalse(m_gripper.StopGrippercommand());

    // //Gripper Close Button
    // m_controller.GetCloseGripperBtn().whileTrue(m_gripper.closeGrippercommand());
    // m_controller.GetCloseGripperBtn().onFalse(m_gripper.StopGrippercommand());
 
    //Raise Arm button
    // m_controller.GetRaiseArmBtn().whileTrue(m_arm.liftArmCommand());
    // m_controller.GetRaiseArmBtn().onFalse(m_arm.stopArmLiftCommand());

    //Shoot Shooter Button 
    m_controller.GetShooterBtn().whileTrue(m_shooter.ShooterCommand());
    m_controller.GetShooterBtn().onFalse(m_shooter.StopShooterCommand());

    //Reverse Shooter Button
    m_controller.GetReverseShooterBtn().whileTrue(m_shooter.ReverseShooterCommand());
    m_controller.GetReverseShooterBtn().onFalse(m_shooter.StopShooterCommand());

    //Amp Shoot Button
    m_controller.GetAmpShootBtn().whileTrue(m_shooter.AmpShooterCommand());
    m_controller.GetAmpShootBtn().onFalse(m_shooter.AmpShooterCommand());

    //Lower Arm Button
    // m_controller.GetLowerArmBtn().whileTrue(m_arm.lowerArmCommand());
    // m_controller.GetLowerArmBtn().onFalse(m_arm.stopArmLiftCommand());

    //Extend Arm Button
    // m_controller.GetExtendArmBtn().whileTrue(m_arm.extendArmCommand());
    // m_controller.GetExtendArmBtn().onFalse(m_arm.stopArmExtendCommand());

    //Retract Arm Button
    // m_controller.GetRetractArmBtn().whileTrue(m_arm.retractArmCommand());
    // m_controller.GetRetractArmBtn().onFalse(m_arm.stopArmExtendCommand());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand(String selectedAuto) {

      System.out.println(String.format("Getting autonomous program for selected, %s", selectedAuto));  
      return m_progOneAuto;

      // switch(selectedAuto)
      // {
      //   case "Program One":
      //   return m_progOneAuto;
      //   case "Program Two":
      //   return m_progTwoAuto;
      //   default:
      //   return m_progOneAuto;
        
      // }

  }

  public Pose2d GetPoseMeters()
  {
    return m_odometry.getPoseMeters();
  }

  public void SimPeriodic()
  {
      // System.out.println("Execute sim");
      // double leftVal = m_driveController.GetLeft();
      // double rightVal = m_driveController.GetRight();

      // m_driveSim.setInputs(leftVal* RobotController.getInputVoltage(),
      //           rightVal * RobotController.getInputVoltage());

      //   // Advance the model by 20 ms. Note that if you are running this
      //   // subsystem in a separate thread or have changed the nominal timestep
      //   // of TimedRobot, this value needs to match it.
      //   m_driveSim.update(0.02);

      //   // Update all of our sensors.
      //   m_leftEncoderSim.setDistance(m_driveSim.getLeftPositionMeters());
      //   m_leftEncoderSim.setRate(m_driveSim.getLeftVelocityMetersPerSecond());
      //   m_rightEncoderSim.setDistance(m_driveSim.getRightPositionMeters());
      //   m_rightEncoderSim.setRate(m_driveSim.getRightVelocityMetersPerSecond());
      //   m_gyroSim.setAngle(-m_driveSim.getHeading().getDegrees());

      //   m_odometry.update(gyro.getRotation2d(),
      //   leftEncoder.getDistance(),
      //   rightEncoder.getDistance());
  }
}