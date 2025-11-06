package org.firstinspires.ftc.teamcode.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "RonanAuto")
public class RonanAuto extends AbstractAutonomous {

    @Override
    public void runOpMode() {
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        move(-15, 0);
        sleep(2000);
        turnToAngle(30);
        move(4, 1);
        turnRelative(720);
    }

}
