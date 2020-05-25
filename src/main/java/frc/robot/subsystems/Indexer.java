package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IndexerConstants.*;

public class Indexer extends SubsystemBase{
    
    public int BALL_COUNT;

    private CANSparkMax extMotor;
    private CANSparkMax midMotor;

    public Indexer() {
        extMotor = new CANSparkMax(EXT_INDEXER_ID, MotorType.kBrushless);
        midMotor = new CANSparkMax(MID_INDEXER_ID, MotorType.kBrushless); 

        configureMotor(extMotor);
        configureMotor(midMotor);

    }

    private void configureMotor(CANSparkMax motor) {
        motor.restoreFactoryDefaults();
        motor.setIdleMode(IdleMode.kBrake);
        motor.setSmartCurrentLimit(50);
        motor.enableVoltageCompensation(12);
        motor.burnFlash();
    }

}