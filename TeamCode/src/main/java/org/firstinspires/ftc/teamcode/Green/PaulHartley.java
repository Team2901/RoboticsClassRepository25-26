package org.firstinspires.ftc.teamcode.Green;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;
@Autonomous(name = "PaulHartleyAuto")
public class PaulHartley extends AbstractAutonomous {
    @Override
    public void runOpMode() {
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        move(-15, 0);
    }
}
