package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends CommandBase {

    private double speed;

    private final Shooter shooter;

    public ShootCommand(Shooter shooter, double speed) {
        this.shooter = shooter;
        addRequirements(shooter);
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