package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.DriveTrainConstants.*;

public class DriveTrain extends SubsystemBase {

    private final WPI_TalonFX leftParent;
    private final WPI_TalonFX rightParent;
    private final WPI_TalonFX leftChild;
    private final WPI_TalonFX rightChild;
    private final DifferentialDrive driveBase;

    private final AHRS gyro;

    public DriveTrain() {
        leftParent = new WPI_TalonFX(LEFT_PARENT_ID);
        rightParent = new WPI_TalonFX(RIGHT_PARENT_ID);
        leftChild = new WPI_TalonFX(LEFT_CHILD_ID);
        rightChild = new WPI_TalonFX(RIGHT_CHILD_ID);
        configureMotor(leftParent, true);
        configureMotor(rightParent, false);
        configureMotor(leftChild, true);
        configureMotor(rightChild, false);

        rightChild.set(ControlMode.Follower, rightParent.getDeviceID());
        leftChild.set(ControlMode.Follower, leftParent.getDeviceID());

        driveBase = new DifferentialDrive(leftParent, rightParent);
        driveBase.setDeadband(DEADBAND);

        gyro = new AHRS(SPI.Port.kMXP);
        gyro.enableLogging(true);
        
    }

    private void configureMotor(WPI_TalonFX motor, boolean left) {
        motor.configFactoryDefault(); // Resetting the motors to make sure there's no junk on there before configuring
        motor.configVoltageCompSaturation(DRIVE_MAX_VOLTAGE); // only use 12.3 volts regardless of battery voltage
        motor.enableVoltageCompensation(true); // enable ^
        motor.setNeutralMode(NeutralMode.Brake); // set it so that when the motor is getting no input, it stops
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor); // configure the encoder (it's inside)
        motor.setSelectedSensorPosition(0); // reset the encoder to have a value of 0
        motor.configOpenloopRamp(RAMP_RATE); // how long it takes to go from 0 to the set speed
        motor.setSensorPhase(left);
        // Make sure that both sides' encoders are getting positive values when going
        // forward
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        // Controlling the left side and the right side seperately
        driveBase.tankDrive(leftSpeed, rightSpeed);
    }

    public void tankDriveVolts(double leftSpeed, double rightSpeed) {
        // Tank drive, but in the case we want to use volts, it's here
        leftParent.setVoltage(DRIVE_MAX_VOLTAGE * leftSpeed);
        rightParent.setVoltage(DRIVE_MAX_VOLTAGE * rightSpeed);
    }

    public void arcadeDrive(double speed, double rotation) {
        // How fast you want to go forward, and how much you want to turn
        driveBase.arcadeDrive(speed, rotation);
    }

    public void curvatureDrive(double speed, double rotation) {
        // Curvature drive is subset of arcade drive seems interesting ... I'll try
        // testing it
        if (speed < 0.25)
            driveBase.curvatureDrive(speed, rotation, true);
        else
            driveBase.curvatureDrive(speed, rotation, false);

        /**
         * These should help to control curvature drive, but testing needs to be done
         * driveBase.setQuickStopAlpha(something);
         * driveBase.setQuickStopThreshold(something);
         */
    }

    public void triggerDrive(double forward, double reverse, double rotation) {
        // Basically how driving works in Forza, uses triggers
        driveBase.arcadeDrive(forward - reverse, rotation);
    }

    public void setMaxOutput(double max) {
        driveBase.setMaxOutput(max);
    }

    public void setAllToCoast() {
        leftParent.setNeutralMode(NeutralMode.Coast);
        rightParent.setNeutralMode(NeutralMode.Coast);
        leftChild.setNeutralMode(NeutralMode.Coast);
        rightChild.setNeutralMode(NeutralMode.Coast);
    }

    public void setAllToBrake() {
        leftParent.setNeutralMode(NeutralMode.Brake);
        rightParent.setNeutralMode(NeutralMode.Brake);
        leftChild.setNeutralMode(NeutralMode.Brake);
        rightChild.setNeutralMode(NeutralMode.Brake);
    }

    public double getAngle() {
        return gyro.getYaw();
    }

    public void showAngle() {
        SmartDashboard.putNumber("Angle", gyro.getAngle());
    }

}