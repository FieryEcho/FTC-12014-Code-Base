package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.HardwarePushbot;

@TeleOp(name="FireWires: Teleop Tank", group="Pushbot")
public class PushbotTeleopTank_Iterative extends OpMode{

    /* Declare OpMode members. */
    org.firstinspires.ftc.teamcode.HardwarePushbot robot = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
                                                         // could also use HardwarePushbotMatrix class.

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Systems Initialized...");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        float left;
        float right;
        float x;

        if (gamepad2.b) {
            robot.shootServo.setPosition(-1);
        } else {
            robot.shootServo.setPosition(1);
        }

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;

        if(gamepad1.dpad_up) {
            left = 1;
            right = 1;
            say("All Forward");
        }
        if(gamepad1.dpad_down){
            left = -1;
            right = -1;
            say("All Backward");
        }
        if(gamepad1.dpad_right){
            left = 1;
            right = 0;
            say("All Right");
        }
        if(gamepad1.dpad_left){
            right = 1;
            left = 0;
            say("All Left");
        }

        /**
         * Turn on and off joystick conditioning
         */
        if (!gamepad1.right_bumper) {
            say("Joystick Unconditioned...");
        } else {
            say("Joystick Conditioned...");
            left = JoystickConditioning(left, .2f, 0, .2f);
            right = JoystickConditioning(right, .2f, 0, .2f);
        }

        /**
         * Turn intake on at 100% to fix stuck balls
         */
        if (gamepad2.right_trigger == 1) {
            robot.intakeMotor.setPower(-1);
        }

        /**
         * Turn intake on at 45% for normal intake
         */
        if (gamepad2.left_trigger == 1) {
            robot.intakeMotor.setPower(1);
        }

        /**
         * Turn intake off if bumpers not pressed
         */
        if (gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
            robot.intakeMotor.setPower(-1);
        }

        /**
         * Fire
         */
        if (gamepad2.a) {
            robot.leftShooter.setPower(.3);
            robot.rightShooter.setPower(.3);
        } else {
            robot.leftShooter.setPower(0);
            robot.rightShooter.setPower(0);
        }

        say("Right: " + right);
        say("Left: " + left);
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

    public static float JoystickConditioning(float x, float db, float off, float gain) {
        float output = 0;
        boolean sign = (x > 0);

        x = Math.abs(x);
        if (x > db) {
            output = (float) (off - ((off - 1) * Math.pow(((db - x) / (db - 1)), gain)));
            output *= sign ? 1 : -1;
        }
        return output;
    }

    public void print(String command, String string)
    {
        telemetry.addData(command, string);
    }
    private void say(String string)
    {
        telemetry.addData("Say", string);
    }
}
