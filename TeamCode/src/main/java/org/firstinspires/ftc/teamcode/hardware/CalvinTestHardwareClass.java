package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
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
    }
}
