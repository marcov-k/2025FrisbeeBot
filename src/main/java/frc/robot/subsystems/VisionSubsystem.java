package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.math.geometry.*;

import org.photonvision.PhotonCamera;

public class VisionSubsystem extends SubsystemBase {
    PhotonCamera camera;
    boolean tagsVisible;
    double targetYaw;
    double targetArea;
    double targetPitch;
    boolean aligned;
    double deadband = 1.0;
    Pose2d currentPose;
    double currentAngle;

    public VisionSubsystem() {
        camera = new PhotonCamera("FrontLeftCamera");
        aligned = false;
    }


    public boolean isAligned() {
        return aligned;
    }

    public Pose2d getPose()
    {
        return currentPose;
    }

    public double getAngle()
    {
        return currentAngle;
    }

    @Override
    public void periodic() {
        UpdateVision();
    }


    public void UpdateVision() {
        tagsVisible = false;

        var results = camera.getAllUnreadResults();
        if (!results.isEmpty()) {
            var result = results.get(results.size() -1);
            var mTagResult = result.getMultiTagResult();
            if (mTagResult.isPresent())
            {
                var position = mTagResult.get().estimatedPose.best;
                var rot = position.getRotation();
                currentPose = new Pose2d(position.getX(), position.getY(), new Rotation2d(rot.getZ()));
            }
        }
    }

    public double CalculateDriveRotation() {

        if (!tagsVisible || aligned) return 0;
        
        // Clamp yaw and convert to rotation (power) 
        double maxYaw = 30.0;
        double maxPower = 0.25;
        double clampedYaw = Math.max(-maxYaw, Math.min(maxYaw, targetYaw-1.0));
        double rotation = (clampedYaw / maxYaw) * maxPower;        

        return rotation;
    }

    public Command align(DriveSubsystem drive) {
        return Commands.run(() -> drive.drive(0,0,CalculateDriveRotation(),false),drive);
    }

    public void robotInit() {        
        camera.getAllUnreadResults(); // warm-up
    }
}
