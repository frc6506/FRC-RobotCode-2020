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
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.drivetrain.Drive;

/** Drivetrain class w/ limelight vision tracking */
public class Drivetrain extends Subsystem {
  // motor stuff
  private CANSparkMax leftMotorFront = new CANSparkMax(RobotMap.MOTOR_LEFT_FRONT_ID, MotorType.kBrushless);
  private CANSparkMax leftMotorBack = new CANSparkMax(RobotMap.MOTOR_LEFT_BACK_ID, MotorType.kBrushless);
  private CANSparkMax rightMotorFront = new CANSparkMax(RobotMap.MOTOR_RIGHT_FRONT_ID, MotorType.kBrushless);
  private CANSparkMax rightMotorBack = new CANSparkMax(RobotMap.MOTOR_RIGHT_BACK_ID, MotorType.kBrushless);

  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftMotorFront, leftMotorBack);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotorFront, rightMotorBack);

  // drivetrain
  private DifferentialDrive dualDrive = new DifferentialDrive(leftMotors, rightMotors);

  // encoders
  private CANEncoder lEncoder = new CANEncoder(leftMotorBack);
  private CANEncoder rEncoder = new CANEncoder(rightMotorBack);
  
  // gyro
  public AHRS gyro;

  /** Constructor; just initializes gyro */
  public Drivetrain() {
    try {
      gyro = new AHRS(SPI.Port.kMXP);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP", true);
    }
  }

  /** For outputting values to SmartDashboard or odometry */
  public void periodic() {
    SmartDashboard.putNumber("gyro", gyro.getAngle());
    SmartDashboard.putNumber("encoder", getPosition());
    SmartDashboard.putNumber("encoderLeft", lEncoder.getPosition());
    SmartDashboard.putNumber("encoderRight", rEncoder.getPosition());
    SmartDashboard.putNumber("Encoder Average", getPosition());
  }

  /** Manual calibration of gyro */
  public void calibrate() {
    lEncoder.setPosition(0);
    rEncoder.setPosition(0);
    gyro.zeroYaw();
  }

  /** Return average position between the encoders */
  public double getPosition() {
    return ((lEncoder.getPosition() + rEncoder.getPosition()*-1) / 2.0);
  }

  /**
   * Wrapper for DifferentialDrive.arcadeDrive();
   *
   * @param speed [-1,1] speed forwards
   * @param rotation [-1,1] rotation, negative is left
   */
  public void drive(double speed, double rotation) {
    dualDrive.arcadeDrive(speed, rotation);
  }

  /**
   * Wrapper for DifferentialDrive.tankDrive();
   *
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
