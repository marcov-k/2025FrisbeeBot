package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkLowLevel.*;
import edu.wpi.first.wpilibj.XboxController;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class IntakeSubsystem extends SubsystemBase
{
    public static final class IntakeConstants
    {
        public static final int intakeSparkMaxCanId = 9;
        public static final double motorSpeed = 0.2;
    }

    final SparkMax motor = new SparkMax(IntakeConstants.intakeSparkMaxCanId, MotorType.kBrushed);

    public Command runIntake()
    {
        return Commands.run(() -> {
            this.motor.set(IntakeConstants.motorSpeed);
        });
    }

    public Command intakeIdleCommand()
    {
        return Commands.run(() -> {
            this.motor.set(0);
        });
    }
}
