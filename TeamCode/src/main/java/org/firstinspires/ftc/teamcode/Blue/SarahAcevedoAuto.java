package org.firstinspires.ftc.teamcode.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.autonomous.AbstractAutonomous;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@Autonomous(name="SarahsAuto")
public class SarahAcevedoAuto extends AbstractAutonomous{
    public RobotHardware robot = new RobotHardware();
    @Override
    public void runOpMode(){
        robot.init(this.hardwareMap, telemetry);
        waitForStart();
        move(24, 0);
        turnRelative(1800);
        move(-48,0);
        move(24, 0);
        turnRelative(720);
        move(12, 0);
        turnRelative(90);
        move(12, 0);
        turnRelative(90);
        move(12, 0);
        turnRelative(90);
        move(12, 0);
        turnRelative(90);
        sleep(100);
    }

}