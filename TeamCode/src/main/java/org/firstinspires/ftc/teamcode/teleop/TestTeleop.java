package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Projects.HWMap;

@TeleOp(name = "TestTeleop")
public class TestTeleop extends LinearOpMode {
    public HWMap robot = new HWMap();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        boolean slowMode = false;
        waitForStart();
        boolean isSpinning = false;
        double speed = 1;
        while (opModeIsActive()) {
            double pee = 0;
            if (gamepad1.left_trigger == 1)
            {
                if (pee == 0)
                {
                    slowMode = true;
                    pee = 1;
                }
                if (pee == 1)
                {
                    slowMode = false;
                    pee = 0;
                }
            }
            if (slowMode == true)
            {
                speed = 0.1;
            }
            else
            {
                speed = 1;
            }
            boolean aButtonHeld = false;
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.frontLeftDrive.setPower(frontLeftPower * speed);
            robot.backLeftDrive.setPower(backLeftPower * speed);
            robot.frontRightDrive.setPower(frontRightPower * speed);
            robot.backRightDrive.setPower(backRightPower * speed);


            if (gamepad2.y == true) {
                double outtake1Pos = robot.outtakeServo.getPosition();
                double outtake2Pos = robot.outtakeServo2.getPosition();
                if (outtake1Pos == 0 && outtake2Pos == 0) {
                    robot.outtakeServo.setPosition(1);
                    robot.outtakeServo2.setPosition(1);
                    sleep(500);
                } else if (outtake1Pos == 1 && outtake2Pos == 1) {
                    robot.outtakeServo.setPosition(0);
                    robot.outtakeServo2.setPosition(0);
                    sleep(500);
                } else if (outtake1Pos == 1 && outtake2Pos == 0 | outtake1Pos == 0 && outtake2Pos == 1){
                    telemetry.addLine("rip");
                    telemetry.update();
                }
            }
            if (gamepad1.right_bumper == true) {
                double d = robot.launchServo.getPosition();
                if (d == 0) {
                    robot.launchServo.setPosition(1);
                    sleep(500);
                } else if (d == 1) {
                    robot.launchServo.setPosition(0);
                    sleep(500);
                }
            }
            if (gamepad1.left_bumper == true) {
                double e = robot.mosaicServo.getPosition();
                if (e == 0) {
                    robot.mosaicServo.setPosition(1);
                    sleep(800);
                } else if (e == 1) {
                    robot.mosaicServo.setPosition(0);
                    sleep(800);
                }
            }
            if (gamepad1.a == true) {
                robot.intakeMotor.setPower(1);
            }
            if (gamepad1.b == true) {
                robot.intakeMotor.setPower(0);
            }
            if (gamepad1.x == true) {
                robot.intakeMotor.setPower(-0.5);
            }
            if (gamepad2.dpad_up == true) {
                robot.slide1.setPower(1);
                robot.slide2.setPower(1);
            }
            else if (gamepad2.dpad_down == true) {
                robot.slide1.setPower(-1);
                robot.slide2.setPower(-1);
            }
            else {
                robot.slide1.setPower(0);
                robot.slide2.setPower(0);
            }
        }
    }
}