/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Robot;

public class RotateToAngle extends Command {
  private double P = 0.04;
  private double I = 0.0;
  private double D = 0.0055;
  private PIDController pid = new PIDController(P, I, D);
  private double gyroSetpoint;
  private double commandStartTime;

  /**
   * Rotates to an angle.
   *
   * @param gsp Setpoint in degrees (e.g. 90).
   */
  public RotateToAngle(double gsp) {
    requires(Robot.drivetrain);
    gyroSetpoint = gsp;
    pid.setTolerance(0.05 * gyroSetpoint);
    commandStartTime = Timer.getFPGATimestamp();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.drivetrain.calibrate();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.drivetrain.drive(0, pid.calculate(Robot.drivetrain.gyro.getAngle(), gyroSetpoint));
  }

  // Make this return true when this Command no longer needs to run execute()
  // Robot sometimes doesn't hit setpoint 100%, call stop based off timer
  @Override
  protected boolean isFinished() {
    if (Timer.getFPGATimestamp() > commandStartTime + 2) {
      return true;
    } else {
      return pid.atSetpoint();
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {}

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
