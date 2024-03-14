package Commands;

import Subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;

/** Drive auto cammand used for autonomous movements */
public class DriveAutoCommand extends Command {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Drive m_driveSubsystem;
  private double m_leftSpeed;
  private double m_rightSpeed;
  private boolean m_isFinished = false;

  /**
   * Creates a new Drive auto command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveAutoCommand(Drive driveSubsystem, double leftSpeed, double rightSpeed) {
    // super(waitTime);
    m_driveSubsystem = driveSubsystem;
    m_leftSpeed = leftSpeed;
    m_rightSpeed = rightSpeed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // System.out.println("Auto Command initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(String.format("Moving Auto : Move :  %f %f", m_leftSpeed, m_rightSpeed));
    m_driveSubsystem.MoveTankAuto(m_leftSpeed, m_rightSpeed);
    m_isFinished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      System.out.println("Auto Motors stopped");
      m_driveSubsystem.MoveTankAuto(0, 0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}
