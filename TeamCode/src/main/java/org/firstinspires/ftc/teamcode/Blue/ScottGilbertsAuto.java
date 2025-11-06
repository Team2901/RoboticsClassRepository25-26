package org.firstinspires.ftc.teamcode.Blue;

//loop() all code goes in here
// telemetry is used for debugging

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

@Autonomous(name = "ScottGilbertsAuto")
public class ScottGilbertsAuto extends AbstractAutonomous {
    @Override
    public void runOpMode(){
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        for (int i = 0; i < 4; i++) {
            move(5, 0);
            turnRelative(90);
        }
    }

}
