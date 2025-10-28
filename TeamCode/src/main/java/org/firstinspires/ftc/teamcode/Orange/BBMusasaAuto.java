package org.firstinspires.ftc.teamcode.Orange;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

@Autonomous(name = "BBMusasaAuto")
public class BBMusasaAuto extends AbstractAutonomous {

    @Override
    public void runOpMode() {
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        move(-15, 0);
    }
}
