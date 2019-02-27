/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6986.m2d16.commands;

import java.awt.Robot;

import org.usfirst.frc6986.m2d16.commands.PIDseries.Backward_a_bit;
import org.usfirst.frc6986.m2d16.commands.PIDseries.Forward_a_bit;
import org.usfirst.frc6986.m2d16.commands.PIDseries.PanelLevel_1;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GetPanelTest extends CommandGroup {
  /**
   * Add your docs here.
   */
  public GetPanelTest() {
    addSequential(new ClawOut(), 0.6);
    addSequential(new HookOut(), 0.4);
    addSequential(new Forward_a_bit(), 2);
    addSequential(new PanelLevel_1(), 1);
    addSequential(new HookBack(), 0.4);
    addSequential(new Backward_a_bit(), 2);



    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
