package org.firstinspires.ftc.teamcode.Red;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp
public class EddieHelloWorld extends OpMode{
    @Override
    public void init() {
        telemetry.addLine("Hello World");
    }
    @Override
    public void loop() {
    }
}

