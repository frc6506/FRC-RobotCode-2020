/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// limelight stuff
import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.commands.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Drivetrain class w/ limelight vision tracking */
public class Drivetrain extends Subsystem {
  // Drivetrain
  CANSparkMax leftBackMotor = new CANSparkMax(RobotMap.MOTOR_LEFT_BACK_ID, MotorType.kBrushless);
  CANSparkMax rightBackMotor = new CANSparkMax(RobotMap.MOTOR_RIGHT_BACK_ID, MotorType.kBrushless);
  CANSparkMax leftFrontMotor = new CANSparkMax(RobotMap.MOTOR_LEFT_FRONT_ID, MotorType.kBrushless);
  CANSparkMax rightFrontMotor =
      new CANSparkMax(RobotMap.MOTOR_RIGHT_FRONT_ID, MotorType.kBrushless);
  DifferentialDrive dualDrive = new DifferentialDrive(leftBackMotor, rightBackMotor);

  // limelight table to read offset value from
  NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

  // encoders
  CANEncoder lEncoder = leftBackMotor.getEncoder();
  CANEncoder rEncoder = rightBackMotor.getEncoder();
  double average = 0.0;

  // Gyro
  public AHRS gyro;

  public Drivetrain() {
    leftFrontMotor.follow(leftBackMotor);
    rightFrontMotor.follow(rightBackMotor);
  }

  public void initializeGyro() {
    try {
      gyro = new AHRS(SPI.Port.kMXP);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP", true);
    }
  }

  public void calibrate() {
    gyro.zeroYaw();
  }

  // PID
  double P = 0.09;
  double I = 0;
  double D = 0;
  PIDController pid = new PIDController(P, I, D);

  // Driving wrapper method
  public void drive(double speed, double rotation) {
    dualDrive.arcadeDrive(Math.pow(speed, 3), rotation, true);
  }

  public void oldDrive(double leftSpeed, double rightSpeed) {
    dualDrive.tankDrive(leftSpeed, rightSpeed);
  }

  public void driveStraight(double speed) {
    dualDrive.arcadeDrive(speed, pid.calculate(gyro.getAngle(), 0));
    SmartDashboard.putNumber("gyro", gyro.getAngle());
  }

  public void rotateToAngle(double angle) {
    dualDrive.arcadeDrive(0, pid.calculate(gyro.getAngle(), angle));
    SmartDashboard.putNumber("gyro", gyro.getAngle());
  }

  // getters
  public double getPosition() {
    average = (lEncoder.getPosition() + rEncoder.getPosition()) / 2.0;
    return average;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new Drive());
  }
}
