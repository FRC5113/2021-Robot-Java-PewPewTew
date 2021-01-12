package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {

    private WPI_TalonFX shooterSlave;
    private WPI_TalonFX shooterMaster;

    public Shooter() {
        shooterMaster = new WPI_TalonFX(SHOOTER_MASTER_ID);
        shooterSlave = new WPI_TalonFX(SHOOTER_SLAVE_ID);
        configMotor(shooterMaster, true);
        configMotor(shooterSlave, false);

        shooterSlave.set(ControlMode.Follower, shooterMaster.getDeviceID());

        shooterMaster.config_kP(0, kP);
        shooterMaster.config_kI(0, kI);
        shooterMaster.config_kD(0, kD);

        shooterMaster.setInverted(false);
        shooterSlave.setInverted(true);

    }

    public void configMotor(WPI_TalonFX motor, boolean master) {
        motor.configFactoryDefault();
        motor.configVoltageCompSaturation(MAX_VOLTAGE);
        motor.enableVoltageCompensation(true);
        motor.setInverted(master);
        motor.setNeutralMode(NeutralMode.Coast);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    }

    public void setSpeed(double speed) {
        shooterMaster.set(ControlMode.Velocity, speed/CONVERSION_RATE);
        SmartDashboard.putNumber("Velocity", shooterMaster.getSelectedSensorVelocity()*CONVERSION_RATE);
    }

    public void coast() {
        shooterMaster.set(0);
    }

    public void getSpeed() {
        SmartDashboard.putNumber("Velocity", shooterMaster.getSelectedSensorVelocity()*CONVERSION_RATE);
    }

}