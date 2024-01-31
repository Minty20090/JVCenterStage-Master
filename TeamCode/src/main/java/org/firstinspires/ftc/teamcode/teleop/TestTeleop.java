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
        robot.lslide.setTargetPosition(0);
        robot.rslide.setTargetPosition(0);
        robot.lslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (opModeIsActive()) {
            telemetry.addLine("thing = " + robot.lslide.getCurrentPosition());
            telemetry.addLine("thing = " + robot.rslide.getCurrentPosition());
            telemetry.update();
            double p = 0;
            if (gamepad1.left_bumper == true) {
                if (p == 0) {
                    speed = 0.1;
                    p = 1;
                    telemetry.addLine("slow");
                    telemetry.update();
                }
                if (p == 1) {
                    speed = 1;
                    telemetry.addLine("fast");
                    telemetry.update();
                    p = 0;
                }
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


            // outtake
            if (gamepad2.right_trigger == 1) {
                double outtake1Pos = robot.leftOuttakeServo.getPosition();
                double outtake2Pos = robot.rightOuttakeServo.getPosition();
                if (outtake1Pos == 0 && outtake2Pos == 0) {
                    robot.leftOuttakeServo.setPosition(1);
                    robot.rightOuttakeServo.setPosition(1);
                    sleep(500);
                } else if (outtake1Pos == 1 && outtake2Pos == 1) {
                    robot.leftOuttakeServo.setPosition(0);
                    robot.rightOuttakeServo.setPosition(0);
                    sleep(500);
                } else if (outtake1Pos == 1 && outtake2Pos == 0 | outtake1Pos == 0 && outtake2Pos == 1) {
                    telemetry.addLine("rip");
                    telemetry.update();
                }
            }
            // launch
//            if (gamepad1.right_bumper == true) {
//                double d = robot.launchServo.getPosition();
//                if (d == 0) {
//                    robot.launchServo.setPosition(1);
//                    sleep(500);
//                } else if (d == 1) {
//                    robot.launchServo.setPosition(0);
//                    sleep(500);
//                }
//            }
            // mosaic
            if (gamepad1.y == true) {
                double e = robot.mosaicServo.getPosition();
                if (e == 0) {
                    robot.mosaicServo.setPosition(1);
                    sleep(800);
                } else if (e == 1) {
                    robot.mosaicServo.setPosition(0);
                    sleep(800);
                }
            }
            // intake
            if (gamepad1.a == true) {
                robot.intakeMotor.setPower(1);
            }
            if (gamepad1.b == true) {
                robot.intakeMotor.setPower(0);
            }
            if (gamepad1.x == true) {
                robot.intakeMotor.setPower(-0.5);
            }
            // lift held
            if (gamepad2.dpad_up == true) {
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                int rcurrentpos=robot.rslide.getCurrentPosition();
                int lcurrentpos=robot.lslide.getCurrentPosition();
                robot.rslide.setTargetPosition(rcurrentpos+100);
                robot.lslide.setTargetPosition(lcurrentpos+100);
            }
            if (gamepad2.dpad_down == true) {
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                int rcurrentpos=robot.rslide.getCurrentPosition();
                int lcurrentpos=robot.lslide.getCurrentPosition();
                robot.rslide.setTargetPosition(rcurrentpos-100);
                robot.lslide.setTargetPosition(lcurrentpos-100);
            }
            //rpesents
            if (gamepad2.a == true) {
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                robot.lslide.setTargetPosition(0);
                robot.rslide.setTargetPosition(0);
            }
            if (gamepad2.x == true) {
                int rVal = robot.rslide.getCurrentPosition();
                int lVal = robot.lslide.getCurrentPosition();
                int dog1 = 800 - rVal;
                int dog2 = 800 - lVal;
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                if (rVal > 800){
                    robot.lslide.setTargetPosition(lVal - Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal - Math.abs(dog1));
                }
                if (rVal < 800){
                    robot.lslide.setTargetPosition(lVal + Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal + Math.abs(dog1));
                }
                telemetry.addLine("Lift encoder right side: " + robot.rslide.getCurrentPosition());
                telemetry.addLine("Lift encoder left side: " + robot.lslide.getCurrentPosition());
                telemetry.update();
            }
            if (gamepad2.b == true) {
                int rVal = robot.rslide.getCurrentPosition();
                int lVal = robot.lslide.getCurrentPosition();
                int dog1 = 1600 - rVal;
                int dog2 = 1600 - lVal;
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                if (rVal > 1600){
                    robot.lslide.setTargetPosition(lVal - Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal - Math.abs(dog1));
                }
                if (rVal < 1600){
                    robot.lslide.setTargetPosition(lVal + Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal + Math.abs(dog1));
                }
                telemetry.addLine("Lift encoder right side: " + robot.rslide.getCurrentPosition());
                telemetry.addLine("Lift encoder left side: " + robot.lslide.getCurrentPosition());
                telemetry.update();
            }
            if (gamepad2.y == true) {
                int rVal = robot.rslide.getCurrentPosition();
                int lVal = robot.lslide.getCurrentPosition();
                int dog1 = 2400 - rVal;
                int dog2 = 2400 - lVal;
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                if (rVal > 2400){
                    robot.lslide.setTargetPosition(lVal - Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal - Math.abs(dog1));
                }
                if (rVal < 2400){
                    robot.lslide.setTargetPosition(lVal + Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal + Math.abs(dog1));
                }
                telemetry.addLine("Lift encoder right side: " + robot.rslide.getCurrentPosition());
                telemetry.addLine("Lift encoder left side: " + robot.lslide.getCurrentPosition());
                telemetry.update();
            }
            if (gamepad2.dpad_left == true) {
                int rVal = robot.rslide.getCurrentPosition();
                int lVal = robot.lslide.getCurrentPosition();
                int dog1 = 2800 - rVal;
                int dog2 = 2800 - lVal;
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                if (rVal > 2800){
                    robot.lslide.setTargetPosition(lVal - Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal - Math.abs(dog1));
                }
                if (rVal < 2800){
                    robot.lslide.setTargetPosition(lVal + Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal + Math.abs(dog1));
                }
                telemetry.addLine("Lift encoder right side: " + robot.rslide.getCurrentPosition());
                telemetry.addLine("Lift encoder left side: " + robot.lslide.getCurrentPosition());
                telemetry.update();
            }
            if (gamepad2.dpad_right == true) {
                int rVal = robot.rslide.getCurrentPosition();
                int lVal = robot.lslide.getCurrentPosition();
                int dog1 = 3600 - rVal;
                int dog2 = 3600 - lVal;
                robot.lslide.setPower(1);
                robot.rslide.setPower(1);
                if (rVal > 3600){
                    robot.lslide.setTargetPosition(lVal - Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal - Math.abs(dog1));
                }
                if (rVal < 3600){
                    robot.lslide.setTargetPosition(lVal + Math.abs(dog2));
                    robot.rslide.setTargetPosition(rVal + Math.abs(dog1));
                }
                telemetry.addLine("Lift encoder right side: " + robot.rslide.getCurrentPosition());
                telemetry.addLine("Lift encoder left side: " + robot.lslide.getCurrentPosition());
                telemetry.update();
            }
        }
    }
}

