package org.firstinspires.ftc.teamcode.Green;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.ImprovedGamepad;
import org.firstinspires.ftc.teamcode.hardware.OutreachHardware;

@TeleOp(name="trevorJimenezTeleOp")

public abstract class trevorJimenezTeleOp extends OpMode {
    public OutreachHardware robot = new OutreachHardware();
    ImprovedGamepad gamepad;

    public void init() {
        gamepad = new ImprovedGamepad(gamepad1, new ElapsedTime(), "Gamepad");
    }

    public void loop() {
        gamepad.update();

        double leftMotorPower = 0;
        double rightMotorPower = 0;

        leftMotorPower += gamepad.left_stick_y.getValue()/2;
        rightMotorPower += gamepad.left_stick_y.getValue()/2;

        if(gamepad.right_stick_x.getValue()>0){
            if(gamepad.left_stick_y.getValue()>0){
                leftMotorPower += gamepad.right_stick_x.getValue()/2;
            }else if(gamepad.left_stick_y.getValue()<0){
                leftMotorPower -= gamepad.right_stick_x.getValue()/2;
            }
        }else if(gamepad.right_stick_x.getValue()<0){
            if(gamepad.left_stick_y.getValue()>0){
                rightMotorPower -= gamepad.right_stick_x.getValue()/2;
            }else if(gamepad.left_stick_y.getValue()<0){
                rightMotorPower += gamepad.right_stick_x.getValue()/2;
            }
        }
    }
}