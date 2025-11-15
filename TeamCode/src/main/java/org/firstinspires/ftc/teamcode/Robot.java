package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.utilities.Alliances;

public class Robot {
    public final static Robot INSTANCE = new Robot();
    private Robot() {}

    public Alliances alliance;
}
