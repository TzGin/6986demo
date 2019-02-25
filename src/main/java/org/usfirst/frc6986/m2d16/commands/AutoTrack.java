/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6986.m2d16.commands;
//package org.usfirst.frc6986.m2d16.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc6986.m2d16.Robot;

//import java.awt.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoTrack extends Command {
  public AutoTrack() {
    requires(Robot.driveTrain);
    requires(Robot.trackPanelTest);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.trackPanelTest.Tracl();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.driveTrain.tankDrive(Robot.oi.joystick1.getRawAxis(1)*0.99, Robot.oi.joystick1.getRawAxis(5)*0.99);
  }
}
