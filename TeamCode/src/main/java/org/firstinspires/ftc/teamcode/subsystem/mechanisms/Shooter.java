package org.firstinspires.ftc.teamcode.subsystem.mechanisms;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Configurables;

public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();
    private Shooter() {}

    // MOTORS
    private final MotorEx leftShooterMotor = new MotorEx("left_shooter");
    private final MotorEx rightShooterMotor = new MotorEx("right_shooter").reversed();

    // VARIABLES
    private final TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();
    public double desired;

    // CONTROL SYSTEM
    private final ControlSystem controller = ControlSystem.builder()
            .velPid(Configurables.P, Configurables.I, Configurables.D)
            .basicFF(Configurables.V, Configurables.A, Configurables.S)
            .build();

    // COMMANDS
    public final Command onClose() { // Set Power ON with specific value
        return new RunToVelocity(controller, (Configurables.SHOOTER_CLOSE));
    }

    public final Command onFar() { // Set Power ON with specific value
        return new RunToVelocity(controller, (Configurables.SHOOTER_FAR));
    }

    public final Command off() { // Set Power OFF
        return new RunToVelocity(controller, (0));
    }

    @Override
    public void initialize() {
        PanelsTelemetry.INSTANCE.getTelemetry().addLine("Shooter Subsystem Initialized");
        desired = 0;
    }

    @Override
    public void periodic() {
        rightShooterMotor.setPower(controller.calculate(rightShooterMotor.getState()));
        leftShooterMotor.setPower(controller.calculate(rightShooterMotor.getState()));

        panels.addData("Shooter Velocity",  rightShooterMotor.getVelocity());
    }
}
