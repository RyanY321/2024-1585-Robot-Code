package Commands;

import Subsystems.Arm;
import Subsystems.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** Drive auto cammand used for autonomous movements */
public class ArmAutoCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Arm m_ArmSubsystem; 
  private boolean m_isFinished = false;
  private double m_speed;

  /**
   * Creates a new Drive auto command.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmAutoCommand(Arm armSubsystem, double speed) {
    m_ArmSubsystem = armSubsystem;
    m_speed = speed;
    addRequirements(armSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ArmSubsystem.LowerArm(m_speed);
    m_isFinished = true;
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}
