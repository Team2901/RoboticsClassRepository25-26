package org.firstinspires.ftc.teamcode.utilities;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TeleOp(name = "ServoTest", group = "test")
public class ServoTest extends OpMode {
    List<Map.Entry<String, Servo>> servoList = new ArrayList<>();
    Integer activeIndex = (0);
    double y;

    public void help() {
        telemetry.addData("dpad up/pdown", "select servo");
        telemetry.addData("a/y", "+/- 0.1");
        telemetry.addData("b/x", "+/- 0.01");
        telemetry.addLine("");
    }

    public void telemetry(){
        telemetry.addData("current servo", servoList.get(activeIndex).getKey());
        telemetry.addData("servo position", servoList.get(activeIndex).getValue().getPosition());
    }

    @Override
    public void init() {
        Set<Map.Entry<String, Servo>> servoSet = this.hardwareMap.servo.entrySet();
        servoList.addAll(servoSet);
        help();
    }

    @Override
    public void loop() {

        if(gamepad1.dpadUpWasPressed()){
            activeIndex++;
        }
        if(gamepad1.dpadDownWasPressed()){
            activeIndex--;
        }
        if(activeIndex == servoList.size()){
            activeIndex = 0;
        }
        if(activeIndex == -1){
            activeIndex = servoList.size() - 1;
        }

        Servo s = servoList.get(activeIndex).getValue();
        double position = s.getPosition();

        if (gamepad1.yWasPressed()){
            position += 0.1;
        }
        if (gamepad1.aWasPressed()){
            position -= 0.1;
        }

        if (gamepad1.bWasPressed()){
            position += 0.01;
        }

        if (gamepad1.xWasPressed()){
            position -= 0.01;
        }

        position = Math.min(1, Math.max(0, position));
        s.setPosition(position);

        help();
        telemetry();
    }
}
