package org.firstinspires.ftc.teamcode.hardware;

//import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class RobotHardware {
    public DcMotorEx frontLeft;
    public DcMotorEx backLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backRight;
    public DcMotorEx launcher;
    public DcMotorEx intake;
    public DcMotorEx ramp;
    public Servo gate;
    RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection;
    RevHubOrientationOnRobot.LogoFacingDirection logoDirection;
    //private static TelemetryManager panelsTelemetry = null;
    public IMU imu;
    public Telemetry telemetry;
    public static final double TICKS_PER_MOTOR_REV = 537.7;
    public static final double DRIVE_GEAR_RATIO = 1.0/1.0;
    public static final double TICKS_PER_DRIVE_REV = TICKS_PER_MOTOR_REV * DRIVE_GEAR_RATIO;
    public static final double WHEEL_DIAMETER = 3.78;
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public static final double TICKS_PER_INCH = TICKS_PER_DRIVE_REV / WHEEL_CIRCUMFERENCE;
    public final double GATE_CLOSED = 0.0;
    public final double GATE_OPEN = 0.1;
    public double TOLORANCE = 1; // degrees
    public double robotTargetAngle;
    public double robotTurnError;
    public double robotTurnPower;
    public double speed = 0.5;
    public void openGate() {
        gate.setPosition(GATE_OPEN);
    }
    public void closeGate(){
        gate.setPosition(GATE_CLOSED);
    }
    public double getAngle(){
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        return AngleUnit.normalizeDegrees(angles.getYaw(AngleUnit.DEGREES));
    }
    public void init(HardwareMap hardwareMap, Telemetry _telemetry){
        telemetry = _telemetry;
        try{
            frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        }catch(IllegalArgumentException e){
            frontLeft = new MockDcMotor();
            telemetry.addLine("Can't find frontLeft: making a mock");
        }
        try{
            frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        }catch(IllegalArgumentException e){
            frontRight = new MockDcMotor();
            telemetry.addLine("Can't find frontRight: making a mock");
        }
        try{
            backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        }catch(IllegalArgumentException e){
            backLeft = new MockDcMotor();
            telemetry.addLine("Can't find backLeft: making a mock");
        }
        try{
            backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        }catch(IllegalArgumentException e){
            backRight = new MockDcMotor();
            telemetry.addLine("Can't find backRight: making a mock");
        }

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        try{
            launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        } catch (IllegalArgumentException e) {
            launcher = new MockDcMotor();
            telemetry.addLine("Can't find launcher: making a mock");
        }
        try{
            intake = hardwareMap.get(DcMotorEx.class, "intake");
        } catch (IllegalArgumentException e) {
            intake = new MockDcMotor();
            telemetry.addLine("Can't find intake: making a mock");
        }
        try{
            gate = hardwareMap.get(Servo.class, "gate");
        } catch (IllegalArgumentException e) {
            gate = new MockServo();
            telemetry.addLine("Can't find gate: making a mock");
        }
        try{
            ramp = hardwareMap.get(DcMotorEx.class, "ramp");
        } catch (IllegalArgumentException e) {
            ramp = new MockDcMotor();
            telemetry.addLine("Can't find ramp: making a mock");
        }
        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setPower(0);

        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setPower(0);

        ramp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ramp.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        ramp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ramp.setPower(0);

        logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        usbFacingDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;

        // Our Control Hub has the new IMU chip (BHI260AP). Use the new generic IMU class when
        // requesting a reference to the IMU hardware. What chip you have can be determined by
        // using "program and manage" tab on dr iver station, then "manage" on the hamburger menu.
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbFacingDirection);
        parameters = new IMU.Parameters(orientationOnRobot);
        boolean success = imu.initialize(parameters);

        if(success && (telemetry != null)){
            telemetry.addLine("IMU initialized");
            telemetry.update();
        }
    }
    public Double getTurnToAngleSpeed(Double turnAngle) {

        if (turnAngle == null) {
            return null;
        }

        //robot.getAngle is between -180 and 180, starting at 0
        double turnPower = 0;
        double targetAngle = AngleUnit.normalizeDegrees(turnAngle) + 180;
        double startAngle = getAngle() + 180;
        double turnError = AngleUnit.normalizeDegrees(targetAngle - startAngle);
        if (Math.abs(turnError) <= TOLORANCE) {
            return 0.0;
        }
        if (turnError >= 0) {
            turnPower = turnError / 90;
            if (turnPower > speed) {
                turnPower = speed;
            }
        } else if (turnError < 0) {
            turnPower = turnError / 90;
            if (turnPower < -speed) {
                turnPower = -speed;
            }
        }
        robotTargetAngle = turnAngle;
        robotTurnError = turnError;
        robotTurnPower = turnPower;
        return turnPower;
    }
}
