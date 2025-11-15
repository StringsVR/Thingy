package org.firstinspires.ftc.teamcode.subsystem.mechanisms;

import com.bylazar.telemetry.PanelsTelemetry;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import org.firstinspires.ftc.robotcore.external.Supplier;

public class Transfer implements Subsystem {
    public static final Transfer INSTANCE = new Transfer();
    private Transfer() {}

    // MOTORS
    private final MotorEx transferMotor = new MotorEx("transfer");

    // VARIABLES
    public double desired;

    // COMMANDS
    public final Command on(Supplier<Float> power) { // Set Power ON with specific value
        return new InstantCommand(() -> desired = power.get());
    }

    public final Command off() { // Set Power OFF
        return new InstantCommand(() -> desired = 0);
    }

    @Override
    public void initialize() {
        PanelsTelemetry.INSTANCE.getTelemetry().addLine("Transfer Subsystem Initialized");
        desired = 0;
    }

    @Override
    public void periodic() {
        transferMotor.setPower(desired);
    }
}
