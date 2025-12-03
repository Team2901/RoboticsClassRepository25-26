package org.firstinspires.ftc.teamcode.Green;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

@Autonomous(name="HaleyRankinAuto")
public class AutoHR extends AbstractAutonomous {
    @Override
    public void runOpMode() {
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        move(10, 0);
        turnRelative(90);
        move(10, 0);
        turnRelative(90);
        move(10, 0);
        turnRelative(90);
        move(10, 0);
    }
}