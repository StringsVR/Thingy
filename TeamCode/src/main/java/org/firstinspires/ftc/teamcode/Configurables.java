package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class Configurables {
    public static double TRIGGER_SENSITIVITY = 0.2; // Sensitivity to trigger 0 - 1
    public static double INTAKE_POWER = 1;  // Power of intake on max setting.
    public static float TRANSFER_SLOW_POWER = 0.6f; // Power of transfer on dpad
    public static float SLOW_MULTIPLIER = 1.2f; // Divider of power in half toggle.
    public static double P = 0.0003, I = 0, D = 0;
    public static double V = 0.03, A = 0.00023, S = 0;
    public static float SHOOTER_CLOSE = -1000, SHOOTER_FAR = -1600;
    public static boolean DEBUG_DT_ON = false;
}
