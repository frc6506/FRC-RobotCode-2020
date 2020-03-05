/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ArmSet;

/** Arm lifting the intake */
public class Arm extends Subsystem {
  TalonSRX armMotor = new TalonSRX(RobotMap.MOTOR_ARM_ID);

  // configure arm
  // I think this works but I'm scared to test it
  public Arm() {
    // set mag encoder
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    // config PID parameters
    armMotor.config_kF(0, 0, 10);
		armMotor.config_kP(0, 0, 10);
		armMotor.config_kI(0, 0, 10);
		armMotor.config_kD(0, 0, 10);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ArmSet());
  }

  public void periodic() {
    SmartDashboard.putNumber("arm pos", armMotor.getSelectedSensorPosition());
  }

  public void turn(double value) {
    armMotor.set(ControlMode.PercentOutput, value);
    armMotor.set(ControlMode.PercentOutput, value); 
  } 

  public void setSetpoint(double setpoint) {
    armMotor.set(ControlMode.Position, setpoint);
  }
}
