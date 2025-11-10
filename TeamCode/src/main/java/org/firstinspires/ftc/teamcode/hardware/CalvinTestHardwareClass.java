package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class CalvinTestHardwareClass {
    public DcMotorEx arm;
    public DcMotorEx leftMotor;
    public DcMotorEx rightMotor;
    public Servo claw;
    public void init(HardwareMap hardwareMap){
        arm = hardwareMap.get(DcMotorEx.class,"arm");
        leftMotor = hardwareMap.get(DcMotorEx.class,"leftMotor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        claw = hardwareMap.get(Servo.class, "claw");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //claw = hardwareMap.servo.get("claw");
        //claw.setPosition(0);
    }
}
