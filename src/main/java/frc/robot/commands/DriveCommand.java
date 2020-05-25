package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends CommandBase {

    private DriveTrain driveTrain;

    private double leftValue;
    private double rightValue;

    public DriveCommand(DriveTrain driveTrain, DoubleSupplier leftVal, DoubleSupplier rightVal) {
        addRequirements(driveTrain);
        this.driveTrain = driveTrain;
        this.leftValue = leftVal.getAsDouble();
        this.leftValue = rightVal.getAsDouble();
    }

    @Override
    public void initialize() {
        this.leftValue = 0.5 * Math.pow(leftValue, 3) + 0.5 * (leftValue);
        this.rightValue = 0.5 * Math.pow(rightValue, 3) + 0.5 * (rightValue);
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(leftValue, rightValue);
    }

}