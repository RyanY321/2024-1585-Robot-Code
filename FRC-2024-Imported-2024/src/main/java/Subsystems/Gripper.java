package Subsystems;

import frc.robot.Pnumatics;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//TODO : This is a stub class for a gripper. We will 
// need to fill in code with real implementations
public class Gripper extends SubsystemBase
{
    private final Pnumatics m_pnumatics;

    /**
     * Open Gripper command factory
     *
     * @return a command
     */
    public Command openGripperCommand() {
        return run(
            () -> {
                this.OpenGripper();
            });
    }

    /**
     * Close Gripper command factory
     *
     * @return a command
     */
    public Command closeGrippercommand() {
        return run(
            () -> {
                this.CloseGripper();
            });
    }

    /**
     * Close Gripper command factory
     *
     * @return a command
     */
    public Command StopGrippercommand() {
        return runOnce(
            () -> {
                this.StopGripper();
            });
    }

    public Gripper(Pnumatics pnumatics)
    {
        m_pnumatics = pnumatics;
    }

    public void OpenGripper()
    {
        System.out.println("Opening Gripper");
        m_pnumatics.TriggerGripperSolenoid(true,false);
    }

    private void CloseGripper()
    {
        System.out.println("Closing Gripper");
        m_pnumatics.TriggerGripperSolenoid(false,true);
    }

    private void StopGripper()
    {
        System.out.println("Stop Gripper");
        m_pnumatics.TriggerGripperSolenoid(false,false);
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
