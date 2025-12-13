package org.firstinspires.ftc.teamcode.utilities;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TeleOp(name = "MotorTest", group = "test")
public class MotorTest extends OpMode {
    List<Map.Entry<String, DcMotor>> dcMotorList = new ArrayList<>();
    Integer activeIndex = (0);
    double y;

    public void help() {
        telemetry.addLine("Use Dpad up/down to choose motor");
        telemetry.addLine("Left stick Y");
        telemetry.addLine("           up - forward");
        telemetry.addLine("           dn - backward");
        telemetry.addLine("");
    }

    public void telemetry(){
        telemetry.addData("current motor", dcMotorList.get(activeIndex).getKey());
        telemetry.addData("motor power", dcMotorList.get(activeIndex).getValue().getPower());
        telemetry.addData("encoder value (ticks)", dcMotorList.get(activeIndex).getValue().getCurrentPosition());
        telemetry.addData("velocity", ((DcMotorEx) dcMotorList.get(activeIndex).getValue()).getVelocity());
        telemetry.addData("y stick", y);
    }

    @Override
    public void init() {
        Set<Map.Entry<String, DcMotor>> dcMotorSet = this.hardwareMap.dcMotor.entrySet();
        dcMotorList.addAll(dcMotorSet);
        help();
    }

    @Override
    public void loop() {

        dcMotorList.get(activeIndex).getValue().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if(gamepad1.dpadUpWasPressed()){
            dcMotorList.get(activeIndex).getValue().setPower(0);
            activeIndex++;
        }
        if(gamepad1.dpadDownWasPressed()){
            dcMotorList.get(activeIndex).getValue().setPower(0);
            activeIndex--;
        }
        if(activeIndex == dcMotorList.size()){
            activeIndex = 0;
        }
        if(activeIndex == -1){
            activeIndex = dcMotorList.size() - 1;
        }
        y = -gamepad1.left_stick_y;
        dcMotorList.get(activeIndex).getValue().setPower(y);

        help();
        telemetry();
    }
}
