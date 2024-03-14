package frc.robot;

import Subsystems.Drive;
import Subsystems.IO;
import Subsystems.Launcher;
import Subsystems.Gyro;

import Commands.DriveCommand;
import Commands.LauncherAutoCommand;
import Commands.LauncherCommand;
import Commands.DriveAutoCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;

public class RobotContainer {
  private IO m_controller = new IO();
  public Gyro m_gyro;
  private Drive m_driveController;
  private Launcher m_launcher;

  private LauncherCommand m_LauncherCommand;
  private DriveCommand m_DriveCommand;

  // private Auto m_auto = new Auto(m_driveController);

  private SequentialCommandGroup m_progOneAuto = new SequentialCommandGroup();
  private SequentialCommandGroup m_progTwoAuto = new SequentialCommandGroup();

  private double rightAutoSpeed = .70;
  private double leftAutoSpeed = rightAutoSpeed * .98;
  private double frontLaunchMotorAutoSpeed = 0.10;
  private double backLaunchMotorAutoSpeed = 0.10;

  // -------Simulator Variables -----///
  private AnalogGyro gyro;
  private AnalogGyro gyro2;
  private AnalogGyroSim m_gyroSim; // = new AnalogGyroSim(0);
  private AnalogGyroSim m_gyroSim2; // = new AnalogGyroSim(1);

  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private EncoderSim m_leftEncoderSim;
  private EncoderSim m_rightEncoderSim;

  private static final double kWheelRadius = 0.0508; // meters
  private static final int kEncoderResolution = 4096;

  private DifferentialDrivetrainSim m_driveSim;

  private DifferentialDriveOdometry m_odometry;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the controller bindings
    configureBindings();

    if (!Robot.isSimulation()) {
      m_gyro = new Gyro();
      // Located in Subsystems.Drive.Java 
      // CAN ID 1 = Left Motor A
      // CAN ID 2 = Left Motor B
      // CAN ID 3 = Right Motor A
      // CAN ID 4 = Right Motor B
      m_driveController = new Drive(m_gyro);
      // Front and Back Motors are in the PWM channels
      // Lift and Feeder Motors are in the CAN channels
      m_launcher = new Launcher(0, 1, 5, 6);
      m_LauncherCommand = new LauncherCommand(m_launcher, m_controller);
      m_DriveCommand = new DriveCommand(m_driveController, m_controller);

      // Start camera server for teleop
      CameraServer.startAutomaticCapture();

      // Schedule the drive controller to move
      m_driveController.setDefaultCommand(m_DriveCommand);

      // Schedule the launcher
      m_launcher.setDefaultCommand((m_LauncherCommand));

    }

    // --- Simulator variable setup ----///
    if (Robot.isSimulation()) {

      m_driveSim = DifferentialDrivetrainSim.createKitbotSim(
          KitbotMotor.kDoubleFalcon500PerSide, // 2 CIMs per side.
          KitbotGearing.k10p71, // 10.71:1
          KitbotWheelSize.kSixInch, // 6" diameter wheels.
          null // No measurement noise.
      );

      gyro = new AnalogGyro(0);
      // gyro2 = new AnalogGyro(1);
      // m_simaddChild("gyro", gyro);
      // gyro.setSensitivity(0.007);
      m_gyroSim = new AnalogGyroSim(0);

      // m_gyroSim2 = new AnalogGyroSim(1);
      leftEncoder = new Encoder(4, 5, false, EncodingType.k4X);
      rightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
      leftEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
      rightEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);

      m_leftEncoderSim = new EncoderSim(leftEncoder);
      m_rightEncoderSim = new EncoderSim(rightEncoder);

      m_odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), 0, 0);

      leftEncoder.reset();
      rightEncoder.reset();
      // --- Simulator -- //
    }

    // ------------Setup autonomous commands -----------------
    m_progOneAuto.addCommands(
      new DriveAutoCommand(m_driveController, -0.50, -0.50),
      new WaitCommand(3.0),
      new DriveAutoCommand(m_driveController, 0.00, 0.00)
    );

    m_progTwoAuto.addCommands(
      new DriveAutoCommand(m_driveController, -0.50, -0.50),
      new WaitCommand(2.5),
      new DriveAutoCommand(m_driveController, 0.00, 0.00)
    );
  }

  // Configures all button mapping bindings for Xbox Controller
  private void configureBindings() {

    // High Launcher Power Button
    // m_controller.GetHighLaunchBtn().whileTrue(m_launcher.LaunchCommand(1.00));
    // m_controller.GetHighLaunchBtn().onFalse(m_launcher.LaunchCommand(0.00));

    // // Low Launcher Power Button
    // m_controller.GetLowLaunchBtn().whileTrue(m_launcher.LaunchCommand(0.50));
    // m_controller.GetLowLaunchBtn().onFalse(m_launcher.LaunchCommand(0.00));

    // // Reverse The Launcher Button
    // m_controller.GetReverseLauncherBtn().whileTrue(m_launcher.LaunchCommand(-0.30));
    // m_controller.GetReverseLauncherBtn().whileFalse(m_launcher.LaunchCommand(0.00));

    // Lift The Launcher Button
    // m_controller.GetLiftLauncherBtn().whileTrue(m_launcher.LiftLauncherCommand(0.40));
    // m_controller.GetLiftLauncherBtn().whileFalse(m_launcher.LiftLauncherCommand(0.00));

    // Lower The Launcher Button
    // m_controller.GetLowerLauncherBtn().whileTrue(m_launcher.LiftLauncherCommand(-0.40));
    // m_controller.GetLowerLauncherBtn().whileFalse(m_launcher.LiftLauncherCommand(0.00));
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
    // case "Program One":
    // return m_progOneAuto;
    // case "Program Two":
    // return m_progTwoAuto;
    // default:
    // return m_progOneAuto;

    // }

  }

  public Pose2d GetPoseMeters() {
    return m_odometry.getPoseMeters();
  }

  public void SimPeriodic() {
    System.out.println("Execute sim");
    double leftVal = m_driveController.GetLeft();
    double rightVal = m_driveController.GetRight();

    m_driveSim.setInputs(leftVal * RobotController.getInputVoltage(),
        rightVal * RobotController.getInputVoltage());

    // Advance the model by 20 ms. Note that if you are running this
    // subsystem in a separate thread or have changed the nominal timestep
    // of TimedRobot, this value needs to match it.
    m_driveSim.update(0.02);

    // Update all of our sensors.
    m_leftEncoderSim.setDistance(m_driveSim.getLeftPositionMeters());
    m_leftEncoderSim.setRate(m_driveSim.getLeftVelocityMetersPerSecond());
    m_rightEncoderSim.setDistance(m_driveSim.getRightPositionMeters());
    m_rightEncoderSim.setRate(m_driveSim.getRightVelocityMetersPerSecond());
    m_gyroSim.setAngle(-m_driveSim.getHeading().getDegrees());

    m_odometry.update(gyro.getRotation2d(),
        leftEncoder.getDistance(),
        rightEncoder.getDistance());
  }
}