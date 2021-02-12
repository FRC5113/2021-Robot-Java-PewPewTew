package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterPulseCommand extends CommandBase{
    
    private double speed;
    private Shooter shooter;

    public ShooterPulseCommand(Shooter shooter, double speed) {
        addRequirements(shooter);
        this.shooter = shooter;
        this.speed = speed;
    }

    @Override
    public void execute() {
        shooter.setSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.coast();
    }

}
