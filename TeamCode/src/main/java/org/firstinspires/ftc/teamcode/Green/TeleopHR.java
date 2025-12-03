package org.firstinspires.ftc.teamcode.Green;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.ImprovedGamepad;
import org.firstinspires.ftc.teamcode.hardware.OutreachHardware;

@TeleOp (name="TeleopHR")
public class TeleopHR extends OpMode  {
    public OutreachHardware robot = new OutreachHardware();
    ImprovedGamepad gamepad;

    @Override
    public void init() {
        gamepad = new ImprovedGamepad(gamepad1, new ElapsedTime(), "Gamepad");
        robot.init(this.hardwareMap);
    }
    @Override
    public void loop() {
        gamepad.update();

        double leftMotorPower = 0;
        double rightMotorPower = 0;
        leftMotorPower += gamepad.left_stick_y.getValue() / 2;
        rightMotorPower += gamepad.left_stick_y.getValue() / 2;

        if(gamepad.right_stick_x.getValue() < 0) {
            leftMotorPower += gamepad.right_stick_x.getValue() / -2;
        } else {
            rightMotorPower += Math.abs(gamepad.right_stick_x.getValue() / -2);
        }

        robot.leftDrive.setPower(leftMotorPower);
        robot.rightDrive.setPower(rightMotorPower);
        }
    }

