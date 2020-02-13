/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// PID
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.Drive;

/** Drivetrain class w/ limelight vision tracking */
public class Drivetrain extends Subsystem {
  // motor stuff
  CANSparkMax leftMotorFront = new CANSparkMax(RobotMap.MOTOR_LEFT_FRONT_ID, MotorType.kBrushless);
  CANSparkMax leftMotorBack = new CANSparkMax(RobotMap.MOTOR_LEFT_BACK_ID, MotorType.kBrushless);
  CANSparkMax rightMotorFront = new CANSparkMax(RobotMap.MOTOR_RIGHT_FRONT_ID, MotorType.kBrushless);
  CANSparkMax rightMotorBack = new CANSparkMax(RobotMap.MOTOR_RIGHT_BACK_ID, MotorType.kBrushless);
  SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftMotorFront, leftMotorBack);
  SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotorFront, rightMotorBack);

  // drivetrain
  DifferentialDrive dualDrive = new DifferentialDrive(leftMotors, rightMotors);

  // gyro
  // TO-DO: consider moving gyro to "utils"
  public AHRS gyro;

  public Drivetrain() {
    try {
      gyro = new AHRS(SPI.Port.kMXP);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP", true);
    }
  }

  /**
   * For outputting values to SmartDashboard or odometry
   */
  public void periodic() {
    SmartDashboard.putNumber("gyro", gyro.getAngle());
    // yeah
  }

  /**
   * Manual calibration of gyro
   */
  public void calibrate() {
    gyro.zeroYaw();
  }

  /**
   * Wrapper for DifferentialDrive.arcadeDrive();
   * @param speed [-1,1] speed forwards
   * @param rotation [-1,1] rotation, negative is left
   */
  public void drive(double speed, double rotation) {
    dualDrive.arcadeDrive(speed, rotation);
  }

  /**
   * Wrapper for DifferentialDrive.tankDrive();
   * @param leftSpeed [-1,1] left wheel speed
   * @param rightSpeed [-1,1] right wheel speed
   */
  public void oldDrive(double leftSpeed, double rightSpeed) {
    dualDrive.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new Drive());
  }
}
