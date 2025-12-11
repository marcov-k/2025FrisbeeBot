package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class IntakeSubsystem extends SubsystemBase
{
    public static final class IntakeConstants
    {
        public static final int intakeSparkMaxCanId = 11;
        public static final double motorSpeed = 0.2;
        public static final double intakeTime = 1.5;
    }

    public static SparkMaxConfig DefaultConfig = new SparkMaxConfig();
    private SparkMax motor;

    public IntakeSubsystem()
    {
        motor = new SparkMax(IntakeConstants.intakeSparkMaxCanId, MotorType.kBrushless);
        DefaultConfig.idleMode(IdleMode.kCoast);
        motor.configure(DefaultConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public Command runIntake()
    {
        return Commands.sequence(
            Commands.runOnce(() -> this.motor.set(IntakeConstants.motorSpeed)),
            Commands.waitSeconds(IntakeConstants.intakeTime)
            ).finallyDo(() -> this.motor.stopMotor());
    }
}
