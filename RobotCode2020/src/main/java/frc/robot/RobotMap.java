/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into to a variable name.
 * This provides flexibility changing wiring, makes checking the wiring easier and significantly
 * reduces the number of magic numbers floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  // arm motor ID; placeholder for now
  public static final int CONTROLLER_PORT_ID = 0;

  public static final int MOTOR_ARM_ID = 20;

  public static final int MOTOR_MAILBOX_ID = 31;

  public static final int MOTOR_LEFT_ID =
      12; // Fliped sides virutally becuaes of turning issues  //nevermind
  public static final int MOTOR_RIGHT_ID = 10;

  public static final int MOTOR_CLIMB_ID = 0;

  // left joystick
  public static final int JOYSTICK_DRIVE_FORWARDS_ID = 1;
  public static final int JOYSTICK_DRIVE_ROTATION_ID = 0;
  public static final int A_BUTTON_ID = 1; // 1-based
  public static final int B_BUTTON_ID = 2;
  // backwards button
  // public static final int JOYSTICK_LEFT_TRIGGER_ID = 0;

  public static final int JOYSTICK_ARM_SET_ID = 5;

  public static final int JOYSTICK_MALIBOX_IN_ID = 2;
  public static final int JOYSTICK_MALIBOX_OUT_ID = 3;

  // public static final int JOYSTICK_MAILBOX_ROLLERS_ID = 0;
}
