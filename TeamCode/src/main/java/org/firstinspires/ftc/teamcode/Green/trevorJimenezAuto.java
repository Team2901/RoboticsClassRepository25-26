package org.firstinspires.ftc.teamcode.Green;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

@Autonomous(name = "trevorJimenezAuto")
public class trevorJimenezAuto extends AbstractAutonomous {
    @Override
    public void runOpMode(){
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        move(-15, 0);
    }
}
