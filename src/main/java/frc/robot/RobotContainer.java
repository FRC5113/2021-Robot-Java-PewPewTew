/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Constants.JoystickConstants.xboxAButton;
import static frc.robot.Constants.JoystickConstants.xboxBButton;
import static frc.robot.Constants.JoystickConstants.xboxLeftBumper;

import java.util.List;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.HopperMove;
import frc.robot.commands.IndexCommand;
import frc.robot.commands.SpinUpCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.HopUp;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import static frc.robot.Constants.PathweaverConstants.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  //private Compressor compressor = new Compressor();
  public DriveTrain driveTrain = new DriveTrain();
  public Indexer indexer = new Indexer();
  public Limelight limelight = new Limelight();
  public HopUp hopper = new HopUp();
  public Shooter shooter = new Shooter();

  private Joystick leftDriveJoystick = new Joystick(0);
  private Joystick rightDriveJoystick = new Joystick(1);
  public XboxController driveController = new XboxController(2);

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    //compressor.setClosedLoopControl(true);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveController, xboxLeftBumper)
        .whenPressed(() -> driveTrain.setMaxOutput(0.5))
        .whenReleased(() -> driveTrain.setMaxOutput(1));

    new JoystickButton(driveController, xboxAButton)
        .whileHeld(new IndexCommand(indexer), false);
    
    new JoystickButton(driveController, xboxBButton)
        .whileHeld(new IndexCommand(indexer, true), false);

    new Trigger(() -> (driveController.getTriggerAxis(Hand.kRight) > 0.75))
        .whileActiveContinuous(new SpinUpCommand(shooter, hopper, 3250));
        
    new Trigger(() -> (driveController.getTriggerAxis(Hand.kLeft) > 0.75))
        .whileActiveContinuous(new HopperMove(hopper));
    
    //new Trigger(() -> (driveController.getTriggerAxis(Hand.kLeft) > 0.75))
    //    .whileActiveContinuous(new CenterTargetRobot(driveTrain, limelight));
  }

  public double getDriveLeftVal() {
    return -leftDriveJoystick.getY();
  }

  public double getDriveRightVal() {
    return rightDriveJoystick.getX();
  }

  public double getControllerLeftY() {
    return -driveController.getY(Hand.kLeft);
  }

  public double getControllerRightX() {
    return driveController.getX(Hand.kRight);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(ksVolts,
                                       kvVoltSecondsPerMeter,
                                       kaVoltSecondsSquaredPerMeter),
            kDriveKinematics,
            10);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(kMaxSpeedMetersPerSecond,
                             kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
    );

    RamseteCommand ramseteCommand = new RamseteCommand(
        exampleTrajectory,
        driveTrain::getPose,
        new RamseteController(kRamseteB, kRamseteZeta),
        new SimpleMotorFeedforward(ksVolts,
                                   kvVoltSecondsPerMeter,
                                   kaVoltSecondsSquaredPerMeter),
        kDriveKinematics,
        driveTrain::getWheelSpeeds,
        new PIDController(kPDriveVel, 0, 0),
        new PIDController(kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        driveTrain::tankDriveVolts,
        driveTrain
    );

    // Reset odometry to the starting pose of the trajectory.
    driveTrain.resetOdometry(exampleTrajectory.getInitialPose());

    
    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> driveTrain.tankDriveVolts(0, 0));
  }
}
