package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.HardwareFireWiresBot;

@Autonomous(name = "Drive, Shoot Twice, Push Ball & Park", group = "FireBot")
public class Drive_Shoot_Twice_Push_Ball_Park extends LinearOpMode {
    HardwareFireWiresBot robot = new HardwareFireWiresBot();
    long start_time;

    @Override
    public void runOpMode() {
        double shooterPower = .25;
        robot.init(hardwareMap);
        waitForStart();
        /* Drive a little over 1/2 way there */
        robot.leftMotor.setPower(-.25);
        robot.rightMotor.setPower(-.25);
        sleep(1500);

        /* Fire! */
        robot.drive(0, 0);
        robot.leftShooter.setPower(shooterPower);
        robot.rightShooter.setPower(shooterPower);
        sleep(2000);
        robot.shootServo.setPosition(-1);
        sleep(500);
        robot.shootServo.setPosition(1);
        robot.leftShooter.setPower(0);
        robot.rightShooter.setPower(0);
        sleep(1000);
        /* Fire again! */
        robot.shootServo.setPosition(1);
        sleep(2000);
        robot.leftShooter.setPower(shooterPower);
        robot.rightShooter.setPower(shooterPower);
        sleep(1000);
        robot.shootServo.setPosition(-1);
        sleep(1000);

        /* Drive the rest of the distance */
        robot.leftMotor.setPower(-.3);
        robot.rightMotor.setPower(-.3);
        sleep(1000);
        robot.drive(0, 0);

        /* Reset the servo for the match */
        robot.shootServo.setPosition(1);
        sleep(2000);
    }
}
