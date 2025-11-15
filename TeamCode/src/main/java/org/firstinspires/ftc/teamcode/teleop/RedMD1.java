package org.firstinspires.ftc.teamcode.teleop;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import static dev.nextftc.bindings.Bindings.*;

import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;
import org.firstinspires.ftc.teamcode.Configurables;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.components.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystem.mechanisms.*;
import org.firstinspires.ftc.teamcode.utilities.Alliances;

// MOTOR NAMES
//  Drive Trains:
//   front_right
//   front_back
//   back_left
//   back_right
//  Shooter:
//   left_shooter
//   right_shooter
//  Intake:
//   intake
//  Transfer:
//   transfer

// MAPPING PRESET
// RT = SHOOTER
// LT = TRANSFER
// LB = INTAKE REVERSE
// RB = INTAKE FORWARD
// X = RESET FIELD CENTRIC
// A = TOGGLE POWER PRESET

@TeleOp(name = "Red Mecanum Drive")
public class RedMD1 extends NextFTCOpMode {
    public RedMD1() {
        addComponents(
                new SubsystemComponent(
                        Shooter.INSTANCE,
                        Transfer.INSTANCE,
                        Intake.INSTANCE,
                        Pinpoint.INSTANCE
                ),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    // LAYERS
    // "max" - Max Power on Shooter & Intake.
    // "half" - Half Power on Shooter & Intake.
    // "endgame" - Endgame Mappings

    @Override
    public void onInit() {
        Robot.INSTANCE.alliance = Alliances.RED;
        BindingManager.setLayer("max");
    }

    private final MotorEx frontLeftMotor = new MotorEx("front_left").reversed();
    private final MotorEx frontRightMotor = new MotorEx("front_right");
    private final MotorEx backLeftMotor = new MotorEx("back_left").reversed();
    private final MotorEx backRightMotor = new MotorEx("back_right");

    @Override
    public void onStartButtonPressed() {
        if (!Configurables.DEBUG_DT_ON) {
            Command driverControlled = new MecanumDriverControlled(
                    frontLeftMotor,
                    frontRightMotor,
                    backLeftMotor,
                    backRightMotor,
                    Gamepads.gamepad1().leftStickY().negate(),
                    Gamepads.gamepad1().leftStickX(),
                    Gamepads.gamepad1().rightStickX()
            );
            driverControlled.schedule();
        }

        // CONTROLS

        // TRANSFER
        Gamepads.gamepad1().leftTrigger().atLeast(Configurables.TRIGGER_SENSITIVITY)
                .whenBecomesFalse(Transfer.INSTANCE.off())
                .inLayer("max")
                .whenTrue(Transfer.INSTANCE.on(() -> { return gamepad1.left_trigger; }))
                .inLayer("half")
                .whenTrue(Transfer.INSTANCE.on(() -> { return ( gamepad1.left_trigger / 1.2f ); }));

        // More Controlled Transfer
        Gamepads.gamepad1().dpadLeft()
                .whenBecomesFalse(Transfer.INSTANCE.off())
                .whenTrue(Transfer.INSTANCE.on(() -> { return -Configurables.TRANSFER_SLOW_POWER; }));

        Gamepads.gamepad1().dpadRight()
                .whenBecomesFalse(Transfer.INSTANCE.off())
                .whenTrue(Transfer.INSTANCE.on(() -> { return Configurables.TRANSFER_SLOW_POWER; }));

        // SHOOTER
        Gamepads.gamepad1().y()
                .whenBecomesTrue(Shooter.INSTANCE.onFar())
                .whenBecomesFalse(Shooter.INSTANCE.off());

        Gamepads.gamepad1().b()
                .whenBecomesTrue(Shooter.INSTANCE.onClose())
                .whenBecomesFalse(Shooter.INSTANCE.off());

        // INTAKE REVERSE
        Gamepads.gamepad1().leftBumper()
                .whenBecomesFalse(Intake.INSTANCE.off())
                .inLayer("max")
                .whenBecomesTrue(Intake.INSTANCE.on(-Configurables.INTAKE_POWER))
                .inLayer("half")
                .whenBecomesTrue(Intake.INSTANCE.on((-Configurables.INTAKE_POWER) / Configurables.SLOW_MULTIPLIER));

        // INTAKE FORWARD
        Gamepads.gamepad1().rightBumper()
                .whenBecomesFalse(Intake.INSTANCE.off())
                .inLayer("max")
                .whenBecomesTrue(Intake.INSTANCE.on(Configurables.INTAKE_POWER))
                .inLayer("half")
                .whenBecomesTrue(Intake.INSTANCE.on((Configurables.INTAKE_POWER) / 2));

        // RESET
        Gamepads.gamepad1().x().whenBecomesTrue(Pinpoint.INSTANCE.resetPinpoint);

        // TOGGLE LAYER
        Gamepads.gamepad1().a()
                .inLayer("max").whenBecomesTrue(() -> BindingManager.setLayer("half"))
                .inLayer("half").whenBecomesTrue(() -> BindingManager.setLayer("max"));
    }

    @Override
    public void onUpdate() {
        BindingManager.update();
        PanelsTelemetry.INSTANCE.getTelemetry().update();
    }

    @Override
    public void onStop() {
        BindingManager.reset();
    }
}
