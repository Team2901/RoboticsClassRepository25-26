package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;


@Autonomous(name = "ExampleAutonomous")
public class ExampleAutonomous extends AbstractAutonomous {
    @Override
    public void runOpMode() {
        robot. init(this.hardwareMap, telemetry);
        waitForStart();
        move(3,12);
        turnRelative(90);
    }
}