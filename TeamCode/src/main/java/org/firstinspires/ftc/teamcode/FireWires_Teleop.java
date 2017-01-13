package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop", group = "FireBot")
public class FireWires_Teleop extends OpMode {
    private static final float INTAKE_POWER = 1;
    private static final float INTAKE_POWER_REVERSE = -1;
    private static final float JOYSTICK_DEADBAND = .2f;
    private static final float JOYSTICK_OFFSET = 0;
    private static final float JOYSTICK_GAIN = .2f;
    private static final float SHOOTER_SERVO_UP = -1;
    private static final float SHOOTER_SERVO_DOWN = 1;

    /* Declare OpMode members. */
    HardwareFireWiresBot robot = new HardwareFireWiresBot(); // use the class created to define a Pushbot's hardware
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
        float left2;
        float right2;
        float x;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;
        left2 = gamepad2.left_stick_y;
        right2 = gamepad2.right_stick_y;


        /* Use the DPad for absolute control */
        if(gamepad1.dpad_up) {
            left = -1;
            right = -1;
            say("All Forward");
        }
        if(gamepad1.dpad_down){
            left = 1;
            right = 1;
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
         * Turn intake on at 100% to fix stuck balls
         */
        if (gamepad2.right_trigger == 1) {
            robot.intake(INTAKE_POWER_REVERSE);
        }

        /**
         * Turn intake on at 100% for normal intake
         */
        if (gamepad2.left_trigger == 1) {
            robot.intake(INTAKE_POWER);
        }

        /**
         * Turn intake off if bumpers not pressed
         */
        if (gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
            robot.intake(0);
        }

        /**
         * Fire
         */
        if (gamepad2.a) {
            robot.fire();
        } else {
            say("Stopping Fire");
            robot.leftShooter.setPower(0);
            robot.rightShooter.setPower(0);
//            robot.move_shoot_servo(SHOOTER_SERVO_DOWN);
        }

        if (gamepad2.b && !gamepad2.a && left2 == 0) {
            robot.move_shoot_servo(SHOOTER_SERVO_UP);
        }
        if (!gamepad2.b && !gamepad2.a && left2 == 0) {
            robot.move_shoot_servo(SHOOTER_SERVO_DOWN);
        }

        if (left2 != 0) {
            robot.shootServo.setPosition(left2);
        }

        robot.drive(left, right);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

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
