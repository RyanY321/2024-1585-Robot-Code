package Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class IO extends SubsystemBase {

  // modify to correct button mapping
  private final int LowLaunchButton = 1; // xbox controller A
  private final int HighLaunchButton = 4; // Xbox Controller Y
  private final int ReverseLauncherButton = 5; // Xbox Controller Left Bumper
  private final int LiftLauncherButton = 3; // Xbox Controller X
  private final int LowerLauncherButton = 2; // Xbox Controller B

  private CommandJoystick m_joystickController = new CommandJoystick(0);
  private CommandXboxController m_controller = new CommandXboxController(1);

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

  public boolean GetB()
  {
    return m_controller.button(LiftLauncherButton).getAsBoolean();
  }

  public boolean GetXBool()
  {
        return m_controller.button(LowerLauncherButton).getAsBoolean();
  }

  public boolean GetABool()
  {
    return m_controller.button(LowLaunchButton).getAsBoolean();
  }

  public boolean GetYBool()
  {
    return m_controller.button(HighLaunchButton).getAsBoolean();
  }

  public boolean GetLeftBumper()
  {
    return m_controller.button(ReverseLauncherButton).getAsBoolean();
  }

  public Trigger GetReverseLauncherBtn() {
    return m_controller.button(ReverseLauncherButton);
  }

  public Trigger GetLowLaunchBtn() {
    return m_controller.button(LowLaunchButton);
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
