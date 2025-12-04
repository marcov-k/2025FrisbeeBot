package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class PositionSubsystem extends SubsystemBase
{
    Pose2d bestPose;
    double bestAngle = 0.0;
    private NetworkTableEntry PoseX;
    private NetworkTableEntry PoseY;
    private NetworkTableEntry PoseZ;

    public PositionSubsystem()
    {
        bestPose = new Pose2d();
        NetworkTable PoseTable = NetworkTableInstance.getDefault().getTable("Pose");
        PoseX = PoseTable.getEntry("X");
        PoseY = PoseTable.getEntry("Y");
        PoseZ = PoseTable.getEntry("Z");
    }

    public Pose2d getBestPos()
    {
        return bestPose;
    }

    public double getBestAngle()
    {
        return bestAngle;
    }

    public void calculateBestPos(VisionSubsystem vision, DriveSubsystem drive)
    {
        if (vision.tagsVisible)
        {
            bestPose = vision.getPose();
            bestAngle = vision.getAngle();
        }
        else
        {
            var odometry = drive.getPosition();
            bestPose = odometry.getPoseMeters();
            bestAngle = drive.getAngle();
        }
        PoseX.setDouble(bestPose.getX());
        PoseY.setDouble(bestPose.getY());
        PoseZ.setDouble(bestAngle);
    }

    public Command posUpdateCommand(VisionSubsystem vision, DriveSubsystem drive)
    {
        return Commands.run(() ->
        {
            this.calculateBestPos(vision, drive);
        });
    }
}
