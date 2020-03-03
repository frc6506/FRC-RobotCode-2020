/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance extends Command {
  private double P = 0.04;
  private double I = 0.0;
  private double D = 0.0055;
  private PIDController pidDriveController = new PIDController(P, I, D);
  private PIDController pidTurnController = new PIDController(P, I, D);
  
  private double rotationsToTarget;
  private double ticksTarget;
  private int ticksPerRotation = 42;
  private double wheelDiameter = 0.5; //ft

  /**
   * Drives robot a set distance w/ PID loop to keep it straight
   *
   * @param distance distance to travel in ft
   */
  public DriveDistance(double distance) {
    requires(Robot.drivetrain);
    // calculate # of ticks based off distance
    rotationsToTarget = distance / (wheelDiameter * Math.PI) * 10.75; 
    pidDriveController.setTolerance(1);
    pidTurnController.setTolerance(0.05);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.drivetrain.calibrate();
    SmartDashboard.putNumber("Ticks To Target", rotationsToTarget);
    SmartDashboard.putString("Command", "DriveDistance");

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double forwardsSpeed = pidDriveController.calculate(Robot.drivetrain.getPosition(), rotationsToTarget);
    double turnSpeed = pidTurnController.calculate(Robot.drivetrain.gyro.getAngle(), 0);
    Robot.drivetrain.drive(forwardsSpeed, turnSpeed);
  }



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return pidDriveController.atSetpoint();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.drive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
