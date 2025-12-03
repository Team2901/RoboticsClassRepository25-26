package org.firstinspires.ftc.teamcode.examples;

//// These import statements allow us to use the FTC SDK classes.
//// They give us access to motors, servos, robot hardware, and OpMode features.

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

// --------------------------------------------------------------------------------------------------------------

public class ChallengeTeleopExample extends LinearOpMode {

//// These variables represent the different moving parts of the robot.
//// Each one will connect to a real piece of hardware on the control hub.
    public DcMotor leftDrive = null; // Motor that drives the left wheel of the robot
    public DcMotor rightDrive = null; // Motor that drives the right wheel of the robot
    public DcMotor armMotor = null; // Motor that lifts and lowers the robot's arm
    public CRServo intake = null; // Spinning servo that takes game pieces in or spits them out
    public Servo wrist = null; // Standard servo that rotates the wrist at the end of the arm

// --------------------------------------------------------------------------------------------------------------

//TODO Your motors used are different, therefore you must figure out the numbers here. Information
// can be found on the GoBuilda Website
    /* This constant is the number of encoder ticks for each degree of rotation of the arm.
    To find this, we first need to consider the total gear reduction powering our arm.
    First, we have an external 20t:100t (5:1) reduction created by two spur gears.
    But we also have an internal gear reduction in our motor.
    The motor we use for this arm is a 117RPM Yellow Jacket. Which has an internal gear
    reduction of ~50.9:1. (more precisely it is 250047/4913:1)
    We can multiply these two ratios together to get our final reduction of ~254.47:1.
    The motor's encoder counts 28 times per rotation. So in total you should see about 7125.16
    counts per rotation of the arm. We divide that by 360 to get the counts per degree. */
    //
    final double ARM_TICKS_PER_DEGREE =
            28 // encoder ticks per motor rotation
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation


    /* These constants hold the position that the arm is commanded to run to.
    These are relative to where the arm was located when you start the OpMode. So make sure the
    arm is reset to collapsed inside the robot before you start the program.

    In these variables you'll see a number in degrees, multiplied by the ticks per degree of the arm.
    This results in the number of encoder ticks the arm needs to move in order to achieve the ideal
    set position of the arm. For example, the ARM_SCORE_SAMPLE_IN_LOW is set to
    160 * ARM_TICKS_PER_DEGREE. This asks the arm to move 160° from the starting position.
    If you'd like it to move further, increase that number. If you'd like it to not move
    as far from the starting position, decrease it. */

// --------------------------------------------------------------------------------------------------------------

    //// Variables for robot used later on in code (can be changed if needed)

    //PRESET ARM POSITIONS (in degrees * ticks per degree)
    //These positions are preset and tell the arm where to go -> defined later on when certain buttons are pressed
    final double ARM_COLLAPSED_INTO_ROBOT = 0; // Arm fully inside robot
    final double ARM_COLLECT = 250 * ARM_TICKS_PER_DEGREE; // Position for collecting pieces
    final double ARM_CLEAR_BARRIER = 230 * ARM_TICKS_PER_DEGREE; // Slight lift for driving over barrier
    final double ARM_SCORE_SPECIMEN = 160 * ARM_TICKS_PER_DEGREE; // Height for specimen scoring
    final double ARM_SCORE_SAMPLE_IN_LOW = 160 * ARM_TICKS_PER_DEGREE; // Low basket scoring
    final double ARM_ATTACH_HANGING_HOOK = 120 * ARM_TICKS_PER_DEGREE; // Position for hanging hook
    final double ARM_WINCH_ROBOT = 15  * ARM_TICKS_PER_DEGREE; // Pull robot up after hooking

    // Intake power levels
    final double INTAKE_COLLECT = -1.0; // spinning inward to collect
    final double INTAKE_OFF = 0.0; // stop spinning
    final double INTAKE_DEPOSIT = 0.5; // spinning outward to release

    // Variables controlling set wrist positions
    final double WRIST_FOLDED_IN = 0.8333; // wrist tucked inside robot
    final double WRIST_FOLDED_OUT = 0.5; // wrist rotated outward for collecting
    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE; // adjust arm by ±15 degrees

    // These variables are used to set the arm to a specific position
    double armPosition = (int)ARM_COLLAPSED_INTO_ROBOT; // start position
    double armPositionFudgeFactor; // small adjustments added to the target


    @Override
    public void runOpMode() {

// --------------------------------------------------------------------------------------------------------------

//// Setting general code before specifying later in code

        //these drive train variables hold joystick inputs and motor calculations
        double left;
        double right;
        double forward;
        double rotate;
        double max;


        // Connecting motors and servos to the configuration on the driver hub
        leftDrive = hardwareMap.get(DcMotor.class, "frontLeft"); //the left drivetrain motor
        rightDrive = hardwareMap.get(DcMotor.class, "frontRight"); //the right drivetrain motor
        armMotor = hardwareMap.get(DcMotor.class, "arm"); //the arm motor

        //One motor is reversed because of how they are mounted
        //TODO depending on the build of the robot, this may have to be changed
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        //Motors will stop quickly when no power is applied
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //If the arm gets stuck, this line detects possible dangerous current
        ((DcMotorEx) armMotor).setCurrentAlert(5,CurrentUnit.AMPS);

        //Resets arm encoder and prepares to run to set positions
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Define and initialize servos.
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist  = hardwareMap.get(Servo.class, "wrist");

        // Initial Servo start positions
        intake.setPower(INTAKE_OFF); //Stop intake
        wrist.setPosition(WRIST_FOLDED_IN); //Wrist started safely inside robot

// --------------------------------------------------------------------------------------------------------------

//// Sends telemetry message to show robot waiting for driver to press play
        telemetry.addLine("Robot Ready.");
        telemetry.update();
        waitForStart();

// --------------------------------------------------------------------------------------------------------------

        // Runs until the driver presses stop
        while (opModeIsActive()) {

//// DRIVING ROBOT CONTROLS
        //Left Stick Y => forward/backward
        //Right Stick X => turning left/right
        //Motors are mixed together to create smoother driving

            /* Set the drive and turn variables to follow the joysticks on the gamepad.
            the joysticks decrease as you push them up. So reverse the Y axis. */
            forward = -gamepad1.left_stick_y; // joystick up is negative, so we invert it
            rotate = gamepad1.right_stick_x;


            /* Here we "mix" the input channels together to find the power to apply to each motor.
            The both motors need to be set to a mix of how much you're retesting the robot move
            forward, and how much you're requesting the robot turn. When you ask the robot to rotate
            the right and left motors need to move in opposite directions. So we will add rotate to
            forward for the left motor, and subtract rotate from forward for the right motor. */

            left = forward + rotate;
            right = forward - rotate;

            // Normalize the values so they don't exceed the limit of 1.0 (speed = between 0 & 1)
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0)
            {
                left /= max;
                right /= max;
            }

            // Set the motor power to the variables we've mixed and normalized
            leftDrive.setPower(left);
            rightDrive.setPower(right);

// --------------------------------------------------------------------------------------------------------------

//// Setting buttons on the driver gamepad to control actions on the robot

            /* Here we handle the three buttons that have direct control of the intake speed.
            These control the continuous rotation servo that pulls elements into the robot,
            If the user presses A, it sets the intake power to the final variable that
            holds the speed we want to collect at.
            If the user presses X, it sets the servo to Off.
            And if the user presses B it reveres the servo to spit out the element.*/

            /* TECH TIP: If Else statements:
            We're using an else if statement on "gamepad1.x" and "gamepad1.b" just in case
            multiple buttons are pressed at the same time. If the driver presses both "a" and "x"
            at the same time. "a" will win over and the intake will turn on. If we just had
            three if statements, then it will set the intake servo's power to multiple speeds in
            one cycle. Which can cause strange behavior. */


            if (gamepad1.a) {
                intake.setPower(INTAKE_COLLECT);
            }
            else if (gamepad1.x) {
                intake.setPower(INTAKE_OFF);
            }
            else if (gamepad1.b) {
                intake.setPower(INTAKE_DEPOSIT);
            }


            /* Here we implement a set of if else statements to set our arm to different scoring positions.
            We check to see if a specific button is pressed, and then move the arm (and sometimes
            intake and wrist) to match. For example, if we click the right bumper we want the robot
            to start collecting. So it moves the armPosition to the ARM_COLLECT position,
            it folds out the wrist to make sure it is in the correct orientation to intake, and it
            turns the intake on to the COLLECT mode.*/

            if(gamepad1.right_bumper){
                /* This is the intaking/collecting arm position */
                armPosition = ARM_COLLECT;
                wrist.setPosition(WRIST_FOLDED_OUT);
                intake.setPower(INTAKE_COLLECT);
            }

            else if (gamepad1.left_bumper){
                    /* This is about 20° up from the collecting position to clear the barrier
                    Note here that we don't set the wrist position or the intake power when we
                    select this "mode", this means that the intake and wrist will continue what
                    they were doing before we clicked left bumper. */
                armPosition = ARM_CLEAR_BARRIER;
            }

            else if (gamepad1.y){
                /* This is the correct height to score the sample in the LOW BASKET */
                armPosition = ARM_SCORE_SAMPLE_IN_LOW;
            }

            else if (gamepad1.dpad_left) {
                    /* This turns off the intake, folds in the wrist, and moves the arm
                    back to folded inside the robot. This is also the starting configuration */
                armPosition = ARM_COLLAPSED_INTO_ROBOT;
                intake.setPower(INTAKE_OFF);
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            else if (gamepad1.dpad_right){
                /* This is the correct height to score SPECIMEN on the HIGH CHAMBER */
                armPosition = ARM_SCORE_SPECIMEN;
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            else if (gamepad1.dpad_up){
                /* This sets the arm to vertical to hook onto the LOW RUNG for hanging */
                armPosition = ARM_ATTACH_HANGING_HOOK;
                intake.setPower(INTAKE_OFF);
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            else if (gamepad1.dpad_down){
                /* this moves the arm down to lift the robot up once it has been hooked */
                armPosition = ARM_WINCH_ROBOT;
                intake.setPower(INTAKE_OFF);
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            armPositionFudgeFactor = FUDGE_FACTOR * (gamepad1.right_trigger + (-gamepad1.left_trigger));

////SUMMARY OF GAMEPAD CONTROLS:
        //Left Stick Y => forward/backward
        //Right Stick X => turning left/right
        //Motors are mixed together to create smoother driving
        //FUDGE_FACTOR => fine adjustments using triggers
        //A Button => bringing element into intake
        //X Button => turning intake off
        //B Button => releasing element out of intake
        //Right Bumper => intaking/collecting arm position
        //Left Bumper => 20° up from the collecting position in order to clear the barrier (FTC INTO THE DEEP GAME)
        //Y Button => correct height to score the element in the LOW BASKET (FTC INTO THE DEEP GAME)
        //D-pad Left => turns off intake, folding in the wrist, and moving arm back to resting position inside robot
        //D-pad Right => correct height to score the element in the HIGH CHAMBER (FTC INTO THE DEEP GAME)
        //D-pad Up => sets the arm vertically to hook onto LOW RUNG for hanging (FTC INTO THE DEEP GAME)
        //D-pad Down => moves the arm down to lift robot up once hooked onto rung (FTC INTO THE DEEP GAME)
        //Right Trigger on Gamepad => small UP adjustments
        //Left Trigger on Gamepad => small DOWN adjustments

// --------------------------------------------------------------------------------------------------------------
//// move arm motor to target position

            armMotor.setTargetPosition((int) (armPosition + armPositionFudgeFactor));

            ((DcMotorEx) armMotor).setVelocity(2100);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

// --------------------------------------------------------------------------------------------------------------

//// Checking to see if our arm is over the current limit, and report via telemetry.
//// telemetry = information that shows up on the driver station when running the program

            if (((DcMotorEx) armMotor).isOverCurrent()){
                telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
            }

            telemetry.addData("armTarget: ", armMotor.getTargetPosition());
            telemetry.addData("arm Encoder: ", armMotor.getCurrentPosition());
            telemetry.update();

        }
    }
}