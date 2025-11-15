package org.firstinspires.ftc.teamcode.subsystem.components;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.ftc.ActiveOpMode;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class Pinpoint implements Subsystem {
    public static final Pinpoint INSTANCE = new Pinpoint();
    private Pinpoint() {}

    // VARIABLES
    GoBildaPinpointDriver pinpoint;

    // COMMANDS
    public Command resetPinpoint = new InstantCommand(() -> { pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0)); });

    // Configure Pinpoint to defaults
    public void configurePinpoint(){
        pinpoint.setOffsets(-84.0, -168.0, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
    }

    // Return Current Angle in NextFTC
    public Angle getHeading() { return Angle.fromDeg(pinpoint.getHeading(AngleUnit.DEGREES)); }

    @Override
    public void initialize() {
        pinpoint = ActiveOpMode.hardwareMap().get(GoBildaPinpointDriver.class, "pinpoint");
        configurePinpoint();
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
    }

    @Override
    public void periodic() {
        pinpoint.update();
    }
}
