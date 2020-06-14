package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IndexerConstants.*;

public class Indexer extends SubsystemBase {

    public int BALL_COUNT = 3;

    private CANSparkMax extMotor;
    private CANSparkMax midMotor;

    private boolean indexStarted = false;
    private boolean indexHappening = false;
    private boolean indexDone = false;
    private int state;

    // Digital Sensor port for Beambreak
    // beambreakStart is the sensor that is closest to the turret
    private DigitalInput beamBreakStartInput = new DigitalInput(BEAM_BREAK_INPUT_ID);
    private DigitalOutput beamBreakStartOutput = new DigitalOutput(BEAM_BREAK_OUTPUT_ID);

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

    public void setSpeed(double speed1, double speed2) {
        extMotor.set(speed1);
        midMotor.set(speed2);
    }

    public void stopIndexing() {
        this.setSpeed(0, 0);
    }

    public int getBallCount() {
        return BALL_COUNT;
    }

    public void incrementBallCount() {
        BALL_COUNT += 1;
    }

    public void decrementBallCount() {
        BALL_COUNT -= 1;
    }

    // ALL STATE MACHINE STUFF

    public boolean getStartInput() {
        return beamBreakStartInput.get();
    }

    public boolean getIndexingStarted() {
        return indexStarted;
    }

    public void setIndexingStarted() {
        indexStarted = !indexStarted;
    }

    public boolean getIndexHappening() {
        return indexStarted;
    }

    public void setIndexHappening() {
        indexHappening = !indexHappening;
    }

    public boolean getIndexDone() {
        return indexDone;
    }

    public void setIndexDone() {
        indexDone = false;
        indexHappening = false;
        indexStarted = false;
    }

    public void indexStart() {
        indexStarted = true;
        indexHappening = false;
        indexDone = false;
    }

    public void indexHappen() {
        indexStarted = false;
        indexHappening = true;
        indexDone = false;
    }

    public void indexDone() {
        indexStarted = false;
        indexHappening = false;
        indexDone = true;
    }

    public int getState() {

        if (!indexStarted && !indexHappening && !indexDone && getStartInput())
            state = 0;
        else if (indexStarted && !indexHappening && !indexDone && !getStartInput())
            state = 1;
        else if (!indexStarted && indexHappening && !indexDone && getStartInput())
            state = 2;
        else
            state = 3;

        return state;
    }

}