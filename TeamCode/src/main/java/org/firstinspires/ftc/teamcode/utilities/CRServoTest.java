package org.firstinspires.ftc.teamcode.utilities;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TeleOp(name = "CRServoTest", group = "test")
public class CRServoTest extends OpMode {
    List<Map.Entry<String, CRServo>> crServoList = new ArrayList<>();
    Integer activeIndex = (0);
    double y;

    public void help() {
        telemetry.addLine("Use Dpad up/down to choose crservo");
        telemetry.addLine("Left stick Y");
        telemetry.addLine("           up - forward");
        telemetry.addLine("           dn - backward");
        telemetry.addLine("");
    }

    public void telemetry(){
        telemetry.addData("current crservo", crServoList.get(activeIndex).getKey());
        telemetry.addData("crservo power", crServoList.get(activeIndex).getValue().getPower());
        telemetry.addData("y stick", y);
    }

    @Override
    public void init() {
        Set<Map.Entry<String, CRServo>> crServoSet = this.hardwareMap.crservo.entrySet();
        crServoList.addAll(crServoSet);
        help();
    }

    @Override
    public void loop() {

        if(gamepad1.dpadUpWasPressed()){
            crServoList.get(activeIndex).getValue().setPower(0);
            activeIndex++;
        }
        if(gamepad1.dpadDownWasPressed()){
            crServoList.get(activeIndex).getValue().setPower(0);
            activeIndex--;
        }
        if(activeIndex == crServoList.size()){
            activeIndex = 0;
        }
        if(activeIndex == -1){
            activeIndex = crServoList.size() - 1;
        }
        y = -gamepad1.left_stick_y;
        crServoList.get(activeIndex).getValue().setPower(y);

        help();
        telemetry();
    }
}
