package org.firstinspires.ftc.teamcode.Brown;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;

@Autonomous(name="Matthewauto")
public class Matthewauto extends AbstractAutonomous {
    @Override
    public void runOpMode() {
        robot.init(this.hardwareMap,telemetry);
        waitForStart();
        move(2,9);
    }
}
