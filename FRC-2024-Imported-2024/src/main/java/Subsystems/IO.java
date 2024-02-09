package Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class IO extends SubsystemBase 
{

    //TODO : create variables for the buttons for other subsystem such as the arm, modify to correct button mapping
    private final int AmpShootButton = 2; // xbox controller B
    private final int ShooterButton = 3; // Xbox Controller Right Trigger
    private final int ReverseShooterButton = 4; // Xbox Controller Left Trigger


    //TODO: Swap between xbox and joystick
    private CommandJoystick m_joystickController =  new CommandJoystick(0);
    private CommandXboxController m_controller = new CommandXboxController(1);

    public IO()
    {

    }


     /**
      * @details Get the controllers X axis value
      * @return double 
      */
    public double GetX()
    {
        //return m_controller.getLeftX();
        System.out.println(m_joystickController.getX());
        return m_joystickController.getX();
    }

    /**
     * @details get the Controllers y axis valu
     * @return double
     */
    public double GetY()
    {
        //return m_controller.getLeftY();
        System.out.println(m_joystickController.getY());
       return m_joystickController.getY();
    }


    //TODO: Create more methods like these for extending/retracting, raising/lowering the arm
    
    public Trigger GetAmpShootBtn()
    {
        return m_controller.button(AmpShootButton);
    }

    public Trigger GetShooterBtn()
    {
      return m_controller.button(ShooterButton);
    }
    
    public Trigger GetReverseShooterBtn()
    {
      return m_controller.button(ReverseShooterButton);
    }

    /**
     * @details Get the retract arm  btn trigger
     * @return Trigger
     */
    public double GetShooterValue()
    {
      return m_controller.getRightTriggerAxis();
    }

    public double GetReverseShooterValue()
    {
      return m_controller.getLeftTriggerAxis();
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
