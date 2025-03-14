package frc.robot;

import Subsystems.Drive;
import Subsystems.IO;
import Subsystems.Gyro;
import Subsystems.Coral;
import Subsystems.Elevator;
import Subsystems.Algae;

import Commands.AlgaeCommand;
import Commands.CoralAutoCommand;
import Commands.ElevatorCommand;
import Commands.CoralCommand;
import Commands.DriveCommand;
import Commands.ElevatorAutoCommand;
import Commands.AlgaeAutoCommand;
import Commands.DriveAutoCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
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
  private Coral m_coral;
  public Elevator m_elevator;
  private Algae m_alage;

  private DriveCommand m_DriveCommand;
  private AlgaeCommand m_AlgaeCommand;
  private CoralCommand m_CoralCommand;
  private ElevatorCommand m_ElevatorCommand;

  // private Auto m_auto = new Auto(m_driveController);

  private SequentialCommandGroup m_progOneAuto = new SequentialCommandGroup();
  private SequentialCommandGroup m_progTwoAuto = new SequentialCommandGroup();

  private double rightAutoSpeed = .70;
  private double leftAutoSpeed = rightAutoSpeed * .98;
  private double frontLaunchMotorAutoSpeed = 0.10;
  private double backLaunchMotorAutoSpeed = 0.10;

  private double autoLiftSpeed = -.34;

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

      //DIO 0 = elevator height stop top
      //DIO 1 = elevator height stop bottom

      //DIO 7 = left Wheel Counter
      //DIO 8 = Right Wheel counter

      m_driveController = new Drive(m_gyro,7,8);
      m_coral = new Coral(7,5,3);
      m_elevator = new Elevator(6,1,0,1);
      m_alage = new Algae(8);

      // Front and Back Motors are in the PWM channels
      // Lift and Feeder Motors are in the CAN channels
      m_DriveCommand = new DriveCommand(m_driveController, m_controller);
      m_CoralCommand = new CoralCommand(m_coral, m_controller);
      m_AlgaeCommand = new AlgaeCommand(m_alage, m_controller);
      m_ElevatorCommand = new ElevatorCommand(m_elevator, m_controller);


      // Start camera server for teleop
      UsbCamera camera = CameraServer.startAutomaticCapture();
      camera.setResolution(1280, 720);

      // Schedule the drive controller to move
      m_driveController.setDefaultCommand(m_DriveCommand);

      // Schedule the Commands
      m_coral.setDefaultCommand((m_CoralCommand));
      m_alage.setDefaultCommand((m_AlgaeCommand));
      m_elevator.setDefaultCommand((m_ElevatorCommand));
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

    //Drive Out
    m_progOneAuto.addCommands(
        new DriveAutoCommand(m_driveController, .80, .80),
        new WaitCommand(1.00),
        new DriveAutoCommand(m_driveController, 0.00, 0.00));

    // Place Coral Straight
    m_progTwoAuto.addCommands(
      new DriveAutoCommand(m_driveController, .80, .80),
      new WaitCommand(2.0),
      new DriveAutoCommand(m_driveController, 0, 0),
      new WaitCommand(.5),
      new CoralAutoCommand(m_coral, 1.00, 1.00),
      new WaitCommand(2),
      new CoralAutoCommand(m_coral, 0, 0)
    );
  }
  

  // Configures all button mapping bindings for Xbox Controller
  private void configureBindings() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand(String selectedAuto) {

    System.out.println(String.format("Getting autonomous program for selected, %s", selectedAuto));
    // return m_progOneAuto;

    switch (selectedAuto) {
      case Robot.programOneAuto:
        return m_progOneAuto;
      case Robot.programTwoAuto:
        return m_progTwoAuto;
      default:
        return m_progOneAuto;

    }

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