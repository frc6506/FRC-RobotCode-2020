/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class MailboxSet extends Command {
  public MailboxSet() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.mail);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // double turnValue = 0;
    System.out.println(Robot.m_oi.getAxis(RobotMap.JOYSTICK_MALIBOX_IN_ID));
    System.out.println(Robot.m_oi.getAxis(RobotMap.JOYSTICK_MALIBOX_OUT_ID));
    if (Robot.m_oi.getAxis(RobotMap.JOYSTICK_MALIBOX_IN_ID) > 0.5) {
      Robot.mail.turn(1.0);
    } else if (Robot.m_oi.getAxis(RobotMap.JOYSTICK_MALIBOX_OUT_ID) > 0.5) {
      Robot.mail.turn(-1.0);
    } else {
      /*System.out.println(
      "DEBUG/WARNING: Java\\frc\\robot\\MailboxsSet.java: MailboxSet was called but no input values tripped.");*/
      Robot.mail.turn(0);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.mail.turn(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
