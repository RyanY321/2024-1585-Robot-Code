package Subsystems;

import Commands.DriveAutoCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Auto extends SubsystemBase {

    private final Drive m_drive;
    
    private DriveAutoCommand m_autAutoCommand;

    public Auto(Drive drive)
    {
        m_drive = drive;
        m_autAutoCommand = new DriveAutoCommand(m_drive, .50, .50);
    }

    public Command MoveForward()
    {
        return runOnce(
        () -> {
            m_autAutoCommand.execute();
        });
    }
    
}
