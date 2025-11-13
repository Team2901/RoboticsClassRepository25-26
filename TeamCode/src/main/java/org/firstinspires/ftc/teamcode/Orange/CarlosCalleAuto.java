package org.firstinspires.ftc.teamcode.Orange;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//auto

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

@Autonomous(name = "CarlosCalleAuto")
public class CarlosCalleAuto extends AbstractAutonomous {

    @Override
    public void runOpMode(){
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        turnToAngle(90);
    }
}

