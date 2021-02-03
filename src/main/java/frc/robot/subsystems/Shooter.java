package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {

    private WPI_TalonFX shooterChild;
    private WPI_TalonFX shooterParent;

    public Shooter() {
        shooterParent = new WPI_TalonFX(SHOOTER_PARENT_ID);
        shooterChild = new WPI_TalonFX(SHOOTER_CHILD_ID);
        configMotor(shooterParent, true);
        configMotor(shooterChild, false);

        shooterChild.set(ControlMode.Follower, shooterParent.getDeviceID());

        shooterParent.config_kP(0, kP);
        shooterParent.config_kI(0, kI);
        shooterParent.config_kD(0, kD);

        shooterParent.setInverted(false);
        shooterChild.setInverted(true);

    }

    public void configMotor(WPI_TalonFX motor, boolean master) {
        motor.configFactoryDefault();
        motor.configVoltageCompSaturation(MAX_VOLTAGE);
        motor.enableVoltageCompensation(true);
        motor.configClosedloopRamp(RAMP_RATE);
        motor.setInverted(master);
        motor.setNeutralMode(NeutralMode.Coast);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    }

    public void setSpeed(double speed) {
        shooterParent.set(ControlMode.Velocity, speed/CONVERSION_RATE);
        SmartDashboard.putNumber("Velocity", shooterParent.getSelectedSensorVelocity()*CONVERSION_RATE);
    }

    public void coast() {
        shooterParent.set(0);
    }

    public void getSpeed() {
        SmartDashboard.putNumber("Velocity", shooterParent.getSelectedSensorVelocity()*CONVERSION_RATE);
    }

    public void getCurrent() {
        SmartDashboard.putNumber("Current", shooterParent.getSupplyCurrent());
    }

}