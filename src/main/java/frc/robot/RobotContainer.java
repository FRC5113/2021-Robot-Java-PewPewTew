/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Constants.JoystickConstants.*;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CenterTargetRobot;
import frc.robot.commands.HopperMove;
import frc.robot.commands.IndexCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.SpinUpCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.HopUp;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

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
        .whileActiveContinuous(new SpinUpCommand(shooter, hopper, 6000));
        
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
    // An ExampleCommand will run in autonomous
    return null;
  }
}
