package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
public abstract class AbstractAutonomous extends LinearOpMode{
    public RobotHardware robot = new RobotHardware();
    public void move(double yInches, double xInches) {
        int ticksY = (int) (yInches * RobotHardware.TICKS_PER_INCH);
        int ticksX = (int) (xInches * (RobotHardware.TICKS_PER_INCH /0.9));

        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeft.setTargetPosition(ticksY + ticksX);
        robot.frontRight.setTargetPosition(ticksY - ticksX);
        robot.backLeft.setTargetPosition(ticksY - ticksX);
        robot.backRight.setTargetPosition(ticksY + ticksX);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeft.setPower(robot.speed);
        robot.frontRight.setPower(robot.speed);
        robot.backLeft.setPower(robot.speed);
        robot.backRight.setPower(robot.speed);

        while (opModeIsActive() && (robot.frontLeft.isBusy() && robot.frontRight.isBusy() &&
                robot.backLeft.isBusy() && robot.backRight.isBusy())) {
            telemetryLog(robot.frontLeft);
            telemetryLog(robot.frontRight);
            telemetryLog(robot.backLeft);
            telemetryLog(robot.backRight);
        }

//        if(Math.abs(original_angle - end_angle) > 2){
//            turnToAngle(original_angle);
//        }

        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void telemetryLog(DcMotorEx dcMotorEx) {
        telemetry.addData("angle", robot.getAngle());
        telemetry.addData("target angle", robot.robotTargetAngle);
        telemetry.addData("turn error", robot.robotTurnError);
        telemetry.addData("turn power", robot.robotTurnPower);
        telemetry.addData("PIDFCoefficients", dcMotorEx.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
        telemetry.addData("Target Position", dcMotorEx.getTargetPosition());
        telemetry.addData("Current Position", dcMotorEx.getCurrentPosition());
        telemetry.addData("Target Position - fL", robot.frontLeft.getTargetPosition());
        telemetry.addData("Target Position - fR", robot.frontRight.getTargetPosition());
        telemetry.addData("Target Position - bL", robot.backLeft.getTargetPosition());
        telemetry.addData("Target Position - bR", robot.backRight.getTargetPosition());
        telemetry.addData("Current Position - fL", robot.frontLeft.getCurrentPosition());
        telemetry.addData("Current Position - fR", robot.frontRight.getCurrentPosition());
        telemetry.addData("Current Position - bL", robot.backLeft.getCurrentPosition());
        telemetry.addData("Current Position - bR", robot.backRight.getCurrentPosition());
        telemetry.update();
    }

    public void turnToAngle(double turnAngle) {
            //robot.getAngle is between -180 and 180, starting at 0
        double turnPower = robot.getTurnToAngleSpeed(turnAngle);
        while (opModeIsActive() && turnPower != 0) {
            robot.frontLeft.setPower(-turnPower);
            robot.frontRight.setPower(turnPower);
            robot.backLeft.setPower(-turnPower);
            robot.backRight.setPower(turnPower);

            turnPower = robot.getTurnToAngleSpeed(turnAngle);
            telemetryLog(robot.frontLeft);
        }
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backRight.setPower(0);
        robot.backLeft.setPower(0);
    }

    public void turnRelative(double relativeAngle) {
        double targetAngle = (robot.getAngle() + relativeAngle);
        turnToAngle(targetAngle);
    }
}
