/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.commands.Drive;

/** Add your docs here. */
public class Drivetrain extends Subsystem {
  // Drivetrain
  CANSparkMax leftBackMotor = new CANSparkMax(RobotMap.MOTOR_LEFT_BACK_ID, MotorType.kBrushless);
  CANSparkMax rightBackMotor = new CANSparkMax(RobotMap.MOTOR_RIGHT_BACK_ID, MotorType.kBrushless);
  CANSparkMax leftFrontMotor = new CANSparkMax(RobotMap.MOTOR_LEFT_FRONT_ID, MotorType.kBrushless);
  CANSparkMax rightFrontMotor =
      new CANSparkMax(RobotMap.MOTOR_RIGHT_FRONT_ID, MotorType.kBrushless);
  DifferentialDrive dualDrive = new DifferentialDrive(leftBackMotor, rightBackMotor);

  // encoders
  CANEncoder lEncoder = leftBackMotor.getEncoder();
  CANEncoder rEncoder = rightBackMotor.getEncoder();

  // average
  double average = 0.0;

  // Wrapper classes
  public void drive(double speed, double rotation) {
    dualDrive.arcadeDrive(speed, rotation);
  }

  public void oldDrive(double leftSpeed, double rightSpeed) {
    dualDrive.tankDrive(leftSpeed, rightSpeed);
  }

  // TO-DO: Consider moving non-driving stuff to commands
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
