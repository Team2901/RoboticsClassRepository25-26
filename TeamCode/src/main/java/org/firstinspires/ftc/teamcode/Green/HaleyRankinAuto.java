package org.firstinspires.ftc.teamcode.Green;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@Autonomous(name="HaleyRankinAuto")
public class HaleyRankinAuto extends AbstractAutonomous {
    @Override
    public void runOpMode(){
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        move(10,0);
        turnRelative(90);
        move(10,0);
        turnRelative(90);
        move(10,0);
        turnRelative(90);
        move(10,0);



    }
}