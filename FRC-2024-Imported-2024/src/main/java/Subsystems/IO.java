package Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class IO extends SubsystemBase {

  // modify to correct button mapping
  private final int FeederButton = 1; // xbox controller A
  private final int HighLaunchButton = 4; // Xbox Controller Y
  private final int ReverseLauncherButton = 5; // Xbox Controller Left Bumper
  private final int LiftLauncherButton = 3; // Xbox Controller X
  private final int LowerLauncherButton = 2; // Xbox Controller B

  public CommandJoystick m_joystickController = new CommandJoystick(0);
  public CommandXboxController m_controller = new CommandXboxController(1);

  public IO() {

  }

  /**
   * @details Get the controllers X axis value
   * @return double
   */
  public double GetX() {
    // return m_controller.getLeftX();
    // System.out.println(m_joystickController.getX());
    return m_joystickController.getX();
  }

  /**
   * @details get the Controllers y axis value
   * @return double
   */
  public double GetY() {
    // System.out.println(m_joystickController.getY());
    return m_joystickController.getY();
  }

  public boolean GetButtonB()
  {
    return m_controller.button(LiftLauncherButton).getAsBoolean();
  }

  public boolean GetButtonX()
  {
        return m_controller.button(LowerLauncherButton).getAsBoolean();
  }

  public boolean GetButtonA()
  {
    return m_controller.button(FeederButton).getAsBoolean();
  }

  public boolean GetButtonY()
  {
    return m_controller.button(HighLaunchButton).getAsBoolean();
  }

  public boolean GetLeftBumper()
  {
    return m_controller.button(ReverseLauncherButton).getAsBoolean();
  }

  public double GetFeederReverseValue()
  {
    return m_controller.getLeftTriggerAxis();
  }

  public double GetFeederForwardValue()
  {
    return m_controller.getRightTriggerAxis();
  }

  public Trigger GetReverseLauncherBtn() {
    return m_controller.button(ReverseLauncherButton);
  }

  public Trigger GetFeederBtn() {
    return m_controller.button(FeederButton);
  }

  public Trigger GetHighLaunchBtn() {
    return m_controller.button(HighLaunchButton);
  }

  public Trigger GetLiftLauncherBtn() {
    return m_controller.button(LiftLauncherButton);
  }

  public Trigger GetLowerLauncherBtn() {
    return m_controller.button(LowerLauncherButton);
  }


  @Override

  public void periodic() {
    // This method will be called once per scheduler run

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
