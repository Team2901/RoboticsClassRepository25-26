package org.firstinspires.ftc.teamcode.Green;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class PaulHartleyHelloWorld extends OpMode {
    @Override
    public void init(){
        telemetry.addData("Hello", "World!");
    }

    @Override
    public void loop(){

    }
}