package org.firstinspires.ftc.teamcode.subsystem.mechanisms;

import com.bylazar.telemetry.PanelsTelemetry;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() {}

    // MOTORS
    private final MotorEx shooterMotor = new MotorEx("intake").reversed();

    // VARIABLES
    public double desired;

    // COMMANDS
    public final Command on(double power) { // Set Power ON with specific value
        return new InstantCommand(() -> desired = power);
    };

    public final Command off() { // Set Power OFF
        return new InstantCommand(() -> desired = 0);
    }

    @Override
    public void initialize() {
        PanelsTelemetry.INSTANCE.getTelemetry().addLine("Intake Subsystem Initialized");
        desired = 0;
    }

    @Override
    public void periodic() {
        shooterMotor.setPower(desired);
    }
}
