package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {

    private Intake intake;
    private boolean direction;

    public IntakeCommand(Intake intake, boolean forward) {
        addRequirements(intake);
        this.intake = intake;
        this.direction = forward;
    }

}