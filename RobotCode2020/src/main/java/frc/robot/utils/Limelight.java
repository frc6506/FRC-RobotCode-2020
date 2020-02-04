package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
  public static NetworkTable limelightTable =
      NetworkTableInstance.getDefault().getTable("limelight");

  public static double returnHorizontalOffset() {
    return limelightTable.getEntry("tx").getDouble(0);
  }

  public static double returnVerticalOffset() {
    return limelightTable.getEntry("ty").getDouble(0);
  }
}
