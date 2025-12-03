package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class HelloWorldExample extends OpMode {
    @Override
    public void init() {
        telemetry.addLine("Hello World!");
    }

//Don't need to worry abt this
    @Override
    public void loop() {

    }


}
