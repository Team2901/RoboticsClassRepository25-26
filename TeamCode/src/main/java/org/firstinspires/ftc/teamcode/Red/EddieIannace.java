package org.firstinspires.ftc.teamcode.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

@Autonomous(name = "EddieIannace")
public class EddieIannace extends AbstractAutonomous {

    @Override
    public void runOpMode() throws InterruptedException {
        robot. init(this.hardwareMap, telemetry);
        waitForStart();
        move(2,9);

    }
}
