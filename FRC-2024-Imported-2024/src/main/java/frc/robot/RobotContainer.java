package frc.robot;

import Subsystems.Drive;
import Subsystems.IO;
import Subsystems.Shooter;
import Subsystems.Gyro;

import Commands.DriveCommand;
import Commands.ShooterCommand;
import Commands.DriveAutoCommand;
// import Subsystems.Arm;
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
  public Gyro m_gyro = new Gyro();
  private Drive m_driveController = new Drive(m_gyro, 0, 1);
  private Shooter m_shooter = new Shooter(2, 3, 4);
  private ShooterCommand m_ShooterCommand = new ShooterCommand(m_shooter, m_controller);
  private DriveCommand m_DriveCommand = new DriveCommand(m_driveController, m_controller);

  //private Auto m_auto = new Auto(m_driveController);

  private SequentialCommandGroup m_progOneAuto = new SequentialCommandGroup();
  private SequentialCommandGroup m_progTwoAuto = new SequentialCommandGroup();


  private double rightAutoSpeed = .70;
  private double leftAutoSpeed = rightAutoSpeed * .98;

  //-------Simulator Variables -----///
  private AnalogGyro gyro;
  private AnalogGyroSim m_gyroSim = new AnalogGyroSim(0);

  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private EncoderSim m_leftEncoderSim;
  private EncoderSim m_rightEncoderSim;

  private static final double kWheelRadius = 0.0508; // meters
  private static final int kEncoderResolution = 4096;

  
  private DifferentialDrivetrainSim m_driveSim = DifferentialDrivetrainSim.createKitbotSim(
    KitbotMotor.kDoubleFalcon500PerSide, // 2 CIMs per side.
    KitbotGearing.k10p71, // 10.71:1
    KitbotWheelSize.kSixInch, // 6" diameter wheels.
    null // No measurement noise.
);

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
    m_shooter.setDefaultCommand((m_ShooterCommand));

        //--- Simulator variable setup ----///
      if(Robot.isSimulation())
      {
        gyro = new AnalogGyro(0);
      // m_simaddChild("gyro", gyro);
        gyro.setSensitivity(0.007);
        m_gyroSim = new AnalogGyroSim(gyro);
        leftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        rightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        leftEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
        rightEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);

        m_leftEncoderSim = new EncoderSim(leftEncoder);
        m_rightEncoderSim = new EncoderSim(rightEncoder);

        m_odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), 0, 0);

        leftEncoder.reset();
        rightEncoder.reset();
        //--- Simulator -- //
      }

    //------------Setup autonomous commands -----------------
    m_progOneAuto.addCommands(

      //Allow the arm to lower for .25 seconds
      new WaitCommand(.25),
      
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

    //High Shoot Power Button 
    m_controller.GetHighShootBtn().whileTrue(m_shooter.ShootCommand(1.00));
    m_controller.GetHighShootBtn().onFalse(m_shooter.ShootCommand(0.00));

    //Low Shoot Power Button
    m_controller.GetLowShootBtn().whileTrue(m_shooter.ShootCommand(0.50));
    m_controller.GetLowShootBtn().onFalse(m_shooter.ShootCommand(0.00));

    //Reverse The Shooter Button
    m_controller.GetReverseShooterBtn().whileTrue(m_shooter.ShootCommand(-0.30));
    m_controller.GetReverseShooterBtn().whileFalse(m_shooter.ShootCommand(0.00));

    //Lift Button
    m_controller.GetLiftBtn().whileTrue(m_shooter.LiftCommand(0.50));
    m_controller.GetLiftBtn().whileFalse(m_shooter.LiftCommand(0.00));

    //Lower Button
    m_controller.GetLowerBtn().whileTrue(m_shooter.LiftCommand(-0.30));
    m_controller.GetLowerBtn().whileFalse(m_shooter.LiftCommand(0.00));
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
      System.out.println("Execute sim");
      double leftVal = m_driveController.GetLeft();
      double rightVal = m_driveController.GetRight();

      m_driveSim.setInputs(leftVal* RobotController.getInputVoltage(),
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