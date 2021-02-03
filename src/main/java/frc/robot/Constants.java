/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public final static class JoystickConstants {

        public static final int xboxAButton = 1;
        public static final int xboxBButton = 2;
        public static final int xboxXButton = 3;
        public static final int xboxYButton = 4;
        public static final int xboxLeftBumper = 5;
        public static final int xboxRightBumper = 6;
        public static final int xboxBackButton = 7;
        public static final int xboxStartButton = 8;


    }

    public final static class LimelightConstants {

        public static final double ANGLE = 25.0;

        public static final double kP = 0.055;
        public static final double kI = 0.000;
        public static final double kD = 0.002;

    }

    public final static class DriveTrainConstants {

        public static final int LEFT_MASTER_ID = 11;
        public static final int RIGHT_MASTER_ID = 12;
        public static final int LEFT_SLAVE_ID = 21;
        public static final int RIGHT_SLAVE_ID = 22;

        public static final double DRIVE_MAX_VOLTAGE = 12.0;
        public static final double RAMP_RATE = 0.6;
        public static final double DEADBAND = 0.1;

    }

    public final static class IntakeConstants {

        public static final int INTAKE_ID = 31;
        public static final double INTAKE_MAX_VOLTAGE = 12.0;
        public static final int INTAKE_CURRENT_LIMIT = 60;

        public static final double INTAKE_SPEED = 60;

    }

    public final static class IndexerConstants {

        public static final int EXT_INDEXER_ID = 32;
        public static final int MID_INDEXER_ID = 33;

        public static final int BEAM_BREAK_INPUT_ID = 5;
        public static final int BEAM_BREAK_OUTPUT_ID = 6;

    }

    public final static class ShooterConstants {

        public static final int SHOOTER_MASTER_ID = 32;
        public static final int SHOOTER_SLAVE_ID = 31;
        public static final double MAX_VOLTAGE = 12.0;
        public static final double RAMP_RATE = 1.0;

        public static final double CONVERSION_RATE = 600.0 / 2048;

        public static final double kP = 1;
        public static final double kI = 0.000062;
        public static final double kD = 5;

    }

}
