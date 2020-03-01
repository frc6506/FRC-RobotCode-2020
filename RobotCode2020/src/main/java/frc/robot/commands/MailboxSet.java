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
    requires(Robot.mail);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean intake = Robot.m_oi.getAxis(RobotMap.JOYSTICK_INTAKE_ID) >= 0.75;
    boolean outake = Robot.m_oi.getAxis(RobotMap.JOYSTICK_OUTPUT_ID) >= 0.75;
    if (intake && outake) {
      System.out.println("both intake and outtake pressed!");
      Robot.mail.turn(0);
    } else if (intake) {
      Robot.mail.turn(1);
    } else if (outake) {
      Robot.mail.turn(-1);
    } else {
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
