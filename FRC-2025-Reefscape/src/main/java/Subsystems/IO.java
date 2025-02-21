package Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class IO extends SubsystemBase {

  // modify to correct button mapping
  private final int AButton = 1;
  private final int BButton = 2;
  private final int XButton = 3;
  private final int YButton = 4;
  private final int LeftBumper = 5;
  private final int RightBumper = 6;

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

  public boolean GetButtonB() {
    return m_controller.b().getAsBoolean();
    // return m_controller.button(BButton).getAsBoolean();
  }

  public boolean GetButtonX() {
    return m_controller.x().getAsBoolean();
    // return m_controller.button(XButton).getAsBoolean();
  }

  public boolean GetButtonA() {
    return m_controller.button(AButton).getAsBoolean();
    // return m_controller.a().getAsBoolean();
  }

  public boolean GetButtonY() {
    return m_controller.button(YButton).getAsBoolean();
    // return m_controller.y().getAsBoolean();
  }

  public boolean GetLeftBumper() {
    // return m_controller.button(LeftBumper).getAsBoolean();
    return m_controller.leftBumper().getAsBoolean();
  }

  public boolean getRightBumper() {
      // return m_controller.button(RightBumper).getAsBoolean();
      return m_controller.rightBumper().getAsBoolean();
  }

  public boolean DPadUp() {
    return m_controller.povUp().getAsBoolean();
  }

  public boolean DPadUpRight() {
    return m_controller.povUpRight().getAsBoolean();
  }

  public boolean DpadUpLeft() {
    return m_controller.povUpLeft().getAsBoolean();
  }

  public boolean DPadDown() {
    return m_controller.povDown().getAsBoolean();
  }

  public boolean DPadDownRight() {
    return m_controller.povDownRight().getAsBoolean();
  }

  public boolean DPadDownLeft() {
    return m_controller.povDownLeft().getAsBoolean();
  }

  public boolean DPadLeft() {
    return m_controller.povLeft().getAsBoolean();
  }

  public boolean DPadRight() {
    return m_controller.povRight().getAsBoolean();
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
