/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/** Add your docs here. */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // Sparks
  Spark leftMotor = new Spark(RobotMap.DRIVE_LEFT_PORT);
  Spark rightMotor = new Spark(RobotMap.DRIVE_RIGHT_PORT);

  // Drivetrain made from sparks
  DifferentialDrive dualDrive = new DifferentialDrive(leftMotor, rightMotor);

  // Wrapper for arcade drive
  public void setDrivetrainArcade(double speed, double angle) {
    dualDrive.arcadeDrive(speed, angle);
  }

  // Wrapper for tank drive
  public void setDrivetrainTank(double leftSpeed, double rightSpeed) {
    dualDrive.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
