package org.firstinspires.ftc.teamcode.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;


@Autonomous(name = "AlexAuto")
public class Alex extends AbstractAutonomous {
    @Override
    public void runOpMode() {
        robot. init(this.hardwareMap, telemetry);
        waitForStart();
        move(2,9);
        turnRelative(2);

    }
}