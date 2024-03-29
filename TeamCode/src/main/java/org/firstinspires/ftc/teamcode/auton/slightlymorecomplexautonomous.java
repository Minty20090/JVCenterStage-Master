package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Projects.HWMap;
import org.firstinspires.ftc.teamcode.auton.BluePropDetectionPipeline.BluePropLocation;
import org.firstinspires.ftc.teamcode.auton.RedPropDetectionPipeline.RedPropLocation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "thiswontwork")

public class slightlymorecomplexautonomous extends LinearOpMode {
    enum Parking {
        FBlue,
        BBlue,
        BRed,
        FRed,
    }
    OpenCvCamera webcam;
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    public HWMap robot = new HWMap();
    public String location = "Left";
    RedPropDetectionPipeline RedPropDetectionPipeline = new RedPropDetectionPipeline(telemetry);
    BluePropDetectionPipeline BluePropDetectionPipeline = new BluePropDetectionPipeline(telemetry);
    @Override
    public void runOpMode() throws InterruptedException {


        //initialize hardware map
        robot.init(hardwareMap);
        Parking Alliance = Parking.FBlue;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()); // init the camera?
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId); // init the camera?

        robot.frontRightDrive.setTargetPosition(0);
        robot.frontLeftDrive.setTargetPosition(0);
        robot.backRightDrive.setTargetPosition(0);
        robot.backLeftDrive.setTargetPosition(0);
        robot.lslide.setTargetPosition(0);
        robot.rslide.setTargetPosition(0);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() { // init the camera?
            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT); // starting the camera at 1280 x 800
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50); // setting the amount of time between new shots transmitted

        int direction = 1;
        int otherDirection = -1;

        boolean isFBlue = false;
        boolean isBBlue = false;
        boolean isFRed = false;
        boolean isBRed = false;
        int robotPos = 0;

        // hi
        // Autonomous code starts here
        String var = "";
        while (!isStarted()) {
            // uygiufougoijpiuhitfjfstuhhdstersuyrukuy;oyg

            if (gamepad1.right_bumper == true) {

                isFBlue = true;
                isBBlue = false;
                isFRed = false;
                isBRed = false;
                robotPos = 1;
                var = "FBlue";
            }
            if (gamepad1.right_trigger != 0) {
                isFBlue = false;
                isBBlue = true;
                isFRed = false;
                isBRed = false;
                robotPos = 2;
                var = "BBlue";
            }
            if (gamepad1.left_bumper == true) {

                isFBlue = false;
                isBBlue = false;
                isFRed = true;
                isBRed = false;
                robotPos = 3;
                var = "FRed";
            }
            if (gamepad1.left_trigger != 0) {

                isFBlue = false;
                isBBlue = false;
                isFRed = false;
                isBRed = true;
                robotPos = 4;
                var = "BRed";
            }



            telemetry.addData("Parking", var);
            telemetry.update();
            sleep(500);

            if (robotPos == 1 || robotPos == 2) {
                webcam.setPipeline(BluePropDetectionPipeline); // setting the pipeline
                BluePropLocation elementLocation = BluePropDetectionPipeline.getPropLocation(); // getting the prop location into a variable elementLocation
                if (elementLocation == BluePropLocation.RIGHT) {
                    telemetry.addLine("right");
                    telemetry.update();
                    location = "Right";
                    sleep(500);


                } else if (elementLocation == BluePropLocation.LEFT) {
                    telemetry.addLine("left");
                    telemetry.update();
                    location = "Left";
                    sleep(500);

                } else if (elementLocation == BluePropLocation.MIDDLE) {
                    telemetry.addLine("middle");
                    telemetry.update();
                    location = "Middle";
                    sleep(500);


                } else {
                    telemetry.addLine("rip");
                    telemetry.update();
                    location = "Middle";
                    sleep(500);
                }
            }
            if (robotPos == 3 || robotPos == 4) {
                webcam.setPipeline(RedPropDetectionPipeline); // setting the pipeline
                RedPropLocation elementLocation = RedPropDetectionPipeline.getPropLocation(); // getting the prop location into a variable elementLocation
                if (elementLocation == RedPropLocation.RIGHT) {
                    telemetry.addLine("right");
                    telemetry.update();
                    location = "Right";
                    sleep(500);


                } else if (elementLocation == RedPropLocation.LEFT) {
                    telemetry.addLine("left");
                    telemetry.update();
                    location = "Left";
                    sleep(500);

                } else if (elementLocation == RedPropLocation.MIDDLE) {
                    telemetry.addLine("middle");
                    telemetry.update();
                    location = "Middle";
                    sleep(500);

                } else {
                    telemetry.addLine("rip");
                    telemetry.update();
                    location = "Left";
                    sleep(500);
                }
            }
        }

        waitForStart(); //wait for play button to be pressed

        if (robotPos == 1)
        { // BBlue
            fBlue(location);
        }
        else if (robotPos == 2)
        {
            bBlue(location);
        }
        else if (robotPos == 3)
        {
            fRed(location);
        }
        else if (robotPos == 4)
        {
            bRed(location);
        }
    }

    public void slow(double tileNum)
    {
        robot.frontRightDrive.setPower(0.1);
        robot.frontLeftDrive.setPower(0.1);
        robot.backRightDrive.setPower(0.1);
        robot.backLeftDrive.setPower(0.1);
        int fright = robot.frontRightDrive.getCurrentPosition();
        int fleft = robot.frontLeftDrive.getCurrentPosition();
        int bright = robot.backRightDrive.getCurrentPosition();
        int bleft = robot.backLeftDrive.getCurrentPosition();
        robot.frontRightDrive.setTargetPosition((int)(fright + 769.23 * tileNum));
        robot.frontLeftDrive.setTargetPosition((int)(fleft + 769.23 * tileNum));
        robot.backRightDrive.setTargetPosition((int)(bright + 769.23 * tileNum));
        robot.backLeftDrive.setTargetPosition((int)(bleft + 769.23 * tileNum));
        sleep(1000);
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
    }
    public void tile(double tileNum)
    {
        robot.frontRightDrive.setPower(0.84);
        robot.frontLeftDrive.setPower(0.8);
        robot.backRightDrive.setPower(0.84);
        robot.backLeftDrive.setPower(0.8);
        int fright = robot.frontRightDrive.getCurrentPosition();
        int fleft = robot.frontLeftDrive.getCurrentPosition();
        int bright = robot.backRightDrive.getCurrentPosition();
        int bleft = robot.backLeftDrive.getCurrentPosition();
        robot.frontRightDrive.setTargetPosition((int)(fright + 903.846 * tileNum));
        robot.frontLeftDrive.setTargetPosition((int)(fleft + 903.846 * tileNum));
        robot.backRightDrive.setTargetPosition((int)(bright + 903.846 * tileNum));
        robot.backLeftDrive.setTargetPosition((int)(bleft + 903.846 * tileNum));
        sleep(Math.abs((long)(1000 * tileNum)));
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
    }
//    public void right90()
//    {
//        sleep(100);
//        robot.frontLeftDrive.setPower(0.7);
//        robot.frontRightDrive.setPower(0.7);
//        robot.backLeftDrive.setPower(0.7);
//        robot.backRightDrive.setPower(0.7);
//        int fleft = robot.frontLeftDrive.getCurrentPosition();
//        int bleft = robot.backLeftDrive.getCurrentPosition();
//        int bright = robot.backRightDrive.getCurrentPosition();
//        int fright = robot.frontRightDrive.getCurrentPosition();
//        robot.frontLeftDrive.setTargetPosition(fleft + 1275);
//        robot.frontRightDrive.setTargetPosition(fright - 1275);
//        robot.backLeftDrive.setTargetPosition(bleft + 1275);
//        robot.backRightDrive.setTargetPosition(bright - 1275);
//        sleep(2000);
//        robot.backRightDrive.setPower(0);
//        robot.backLeftDrive.setPower(0);
//        robot.frontRightDrive.setPower(0);
//        robot.frontLeftDrive.setPower(0);
//    }
    public void turn(String direction, double degrees)
    {
        if (direction == "left")
        {
            robot.frontLeftDrive.setPower(0.7);
            robot.frontRightDrive.setPower(0.7);
            robot.backLeftDrive.setPower(0.7);
            robot.backRightDrive.setPower(0.7);
            int fleft = robot.frontLeftDrive.getCurrentPosition();
            int bleft = robot.backLeftDrive.getCurrentPosition();
            int bright = robot.backRightDrive.getCurrentPosition();
            int fright = robot.frontRightDrive.getCurrentPosition();
            robot.frontLeftDrive.setTargetPosition((int)(fleft - 11.0256384615 * degrees));
            robot.frontRightDrive.setTargetPosition((int)(fright + 11.0256384615 * degrees));
            robot.backLeftDrive.setTargetPosition((int)(bleft - 11.0256384615 * degrees));
            robot.backRightDrive.setTargetPosition((int)(bright + 11.0256384615 * degrees));
            // remember to sleep
        }
        if (direction == "right")
        {
            robot.frontLeftDrive.setPower(0.7);
            robot.frontRightDrive.setPower(0.7);
            robot.backLeftDrive.setPower(0.7);
            robot.backRightDrive.setPower(0.7);
            int fleft = robot.frontLeftDrive.getCurrentPosition();
            int bleft = robot.backLeftDrive.getCurrentPosition();
            int bright = robot.backRightDrive.getCurrentPosition();
            int fright = robot.frontRightDrive.getCurrentPosition();
            robot.frontLeftDrive.setTargetPosition((int)(fleft + 11.0256384615 * degrees));
            robot.frontRightDrive.setTargetPosition((int)(fright - 11.0256384615 * degrees));
            robot.backLeftDrive.setTargetPosition((int)(bleft + 11.0256384615 * degrees));
            robot.backRightDrive.setTargetPosition((int)(bright - 11.0256384615 * degrees));
            // remember to sleep
        }
    }
//    public void left90()
//    {
//        sleep(100);
//        robot.frontLeftDrive.setPower(0.7);
//        robot.frontRightDrive.setPower(0.7);
//        robot.backLeftDrive.setPower(0.7);
//        robot.backRightDrive.setPower(0.7);
//        int fleft = robot.frontLeftDrive.getCurrentPosition();
//        int bleft = robot.backLeftDrive.getCurrentPosition();
//        int bright = robot.backRightDrive.getCurrentPosition();
//        int fright = robot.frontRightDrive.getCurrentPosition();
//        robot.frontLeftDrive.setTargetPosition(fleft - 1275);
//        robot.frontRightDrive.setTargetPosition(fright + 1275);
//        robot.backLeftDrive.setTargetPosition(bleft - 1275);
//        robot.backRightDrive.setTargetPosition(bright + 1275);
//        sleep(1000);
//        robot.backRightDrive.setPower(0);
//        robot.backLeftDrive.setPower(0);
//        robot.frontRightDrive.setPower(0);
//        robot.frontLeftDrive.setPower(0);
//    }
    public void pixelDown()
    {
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
        sleep(100);
        robot.intakeMotor.setPower(0.5);
        sleep(1300);
        robot.intakeMotor.setPower(0);
    }
    public void outtake()
    {
        sleep(200);
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
        int slidePos1 = robot.lslide.getCurrentPosition();
        int slidePos2 = robot.rslide.getCurrentPosition();
        robot.lslide.setPower(0.7);
        robot.rslide.setPower(0.7);
        robot.lslide.setTargetPosition(slidePos1 + 800);  //if change this, change slideDown as well
        robot.rslide.setTargetPosition(slidePos2 + 800);
        sleep(400);
        robot.leftOuttakeServo.setPosition(1);
        robot.rightOuttakeServo.setPosition(1);
        sleep(600);
        robot.lslide.setPower(1);
        robot.rslide.setPower(1);
        slidePos1 = robot.lslide.getCurrentPosition();
        slidePos2 = robot.rslide.getCurrentPosition();
        robot.lslide.setTargetPosition(slidePos1 + 500);
        robot.rslide.setTargetPosition(slidePos2 + 500);
        sleep(500);
    }
    public void slideDown()
    {
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
        int slidePos1 = robot.lslide.getCurrentPosition();
        int slidePos2 = robot.rslide.getCurrentPosition();
        robot.lslide.setPower(0.7);
        robot.rslide.setPower(0.7);
        robot.rslide.setTargetPosition(slidePos2 - 1300);
        robot.lslide.setTargetPosition(slidePos1 - 1300);
        sleep(2000);
    }
    public void strafeLeft(double tileNum)
    {
        robot.frontLeftDrive.setPower(-0.5);
        robot.frontRightDrive.setPower(0.7);
        robot.backRightDrive.setPower(-0.5);
        robot.backLeftDrive.setPower(0.7);
        int fleft = robot.frontLeftDrive.getCurrentPosition();
        int bleft = robot.backLeftDrive.getCurrentPosition();
        int bright = robot.backRightDrive.getCurrentPosition();
        int fright = robot.frontRightDrive.getCurrentPosition();
        robot.frontLeftDrive.setTargetPosition((int) (fleft - 1307.692 * tileNum));
        robot.frontRightDrive.setTargetPosition((int) (fright + 1307.692 * tileNum));;
        robot.backLeftDrive.setTargetPosition((int) (bleft + 1153.846 * tileNum));
        robot.backRightDrive.setTargetPosition((int)(bright - 1153.846 * tileNum));
        sleep(1500);
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
    }
    public void strafeRight(double tileNum)
    {
        robot.frontLeftDrive.setPower(0.5);
        robot.frontRightDrive.setPower(-0.7);
        robot.backRightDrive.setPower(0.5);
        robot.backLeftDrive.setPower(-0.7);
        int fleft = robot.frontLeftDrive.getCurrentPosition();
        int bleft = robot.backLeftDrive.getCurrentPosition();
        int bright = robot.backRightDrive.getCurrentPosition();
        int fright = robot.frontRightDrive.getCurrentPosition();
        robot.frontLeftDrive.setTargetPosition((int) (fleft + 1307.692 * tileNum));
        robot.frontRightDrive.setTargetPosition((int) (fright - 1307.692 * tileNum));;
        robot.backLeftDrive.setTargetPosition((int) (bleft - 1153.846 * tileNum));
        robot.backRightDrive.setTargetPosition((int)(bright + 1153.846 * tileNum));
        sleep(Math.abs((long)(1500 * tileNum)));
        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);
    }
    public void bBlue(String location)
    {
        if (location == "Middle")
        {
            tile(1);
            pixelDown();
            tile(-0.36);
            turn("left", 90);
            tile(-1.7);
            outtake();
            sleep(500);
            tile(0.2);
            robot.leftOuttakeServo.setPosition(0);
            robot.rightOuttakeServo.setPosition(0);
            slideDown();
            strafeRight(1.5);
            tile(-1.5);
        }
        if (location == "Right")
        {
            tile(1);
            turn("right", 90);
            pixelDown();
            strafeLeft(0.2);
            tile(-1.7);
            outtake();
            tile(0.3);
            robot.leftOuttakeServo.setPosition(0);
            robot.rightOuttakeServo.setPosition(0);
            slideDown();
            strafeRight(2.2);
            tile(-1.7);
        }
        if (location == "Left")
        {
            tile(1);
            turn("right", 90);
            tile(-1.2);
            pixelDown();
            strafeRight(0.2);
            tile(-1);
            outtake();
            tile(0.3);
            robot.leftOuttakeServo.setPosition(0);
            robot.rightOuttakeServo.setPosition(0);
            slideDown();
            strafeRight(1.3);
            tile(-1.3);
        }
    }
    public void fBlue(String location) {

        if (location == "Left") {

//            tile(1.7);
//            sleep(100);
//            left90();
//            sleep(100);
//           tile(-1.45);
//            sleep(100);
//            pixelDown();
 }
            if (location == "Middle") {
                strafeLeft(1);
//            tile(2);
//            tile(-0.39);
//            pixelDown();
//
        }
                if (location == "Right") {
//            tile(1.7);
//            left90();
//            tile(0.05);
//            sleep(100);
//            pixelDown();
        }
//    }
                }
                public void fRed (String location)
                {
                    if (location == "Middle") {
                        tile(2);
                        tile(-0.35);
                        pixelDown();
                        tile(-0.15);
                    }
                    if (location == "Left") {
                        tile(1.7);
                        turn("right", 90);
                        sleep(100);
                        pixelDown();
                        tile(-0.15);
                    }
                    if (location == "Right") {
                        tile(1.7);
                        sleep(100);
                        turn("left", 90);
                        sleep(100);
                        sleep(100);
                        pixelDown();
                        tile(-0.2);
                    }

                }
                public void bRed (String location)
                {
                    if (location == "Middle") {
                        tile(2);
                        tile(-0.35);
                        pixelDown();
                        tile(-0.3);
                        turn("right", 90);
                        tile(-2.85);
                        outtake();
                        sleep(500);
                        tile(0.3);
                        robot.leftOuttakeServo.setPosition(0);
                        robot.rightOuttakeServo.setPosition(0);
                        slideDown();
                        strafeLeft(2);
                        tile(-1.2);
                    }
                    if (location == "Left") {
                        tile(1.8);
                        turn("right", 90);
                        tile(0.2);
                        sleep(100);

                        pixelDown();
                        tile(-1.5);
                        tile(-1.2);
                        outtake();
                        tile(0.5);
                        robot.leftOuttakeServo.setPosition(0);
                        robot.rightOuttakeServo.setPosition(0);
                        slideDown();
                        strafeLeft(1.57);
                        tile(-1.4);
                    }
                    if (location == "Right") {
                        tile(1.8);
                        sleep(100);
                        turn("right", 90);
                        sleep(100);
                        tile(-1.2);
                        sleep(100);
                        pixelDown();
                        sleep(100);
                        tile(-2.365);
                        sleep(100);
                        outtake();
                        sleep(300);
                        tile(0.5);
                        sleep(100);
                        robot.leftOuttakeServo.setPosition(0);
                        robot.rightOuttakeServo.setPosition(0);
                        sleep(500);
                        slideDown();
                        sleep(100);
                        strafeLeft(2.05);
                        tile(-1.5);
                    }
                }
            }

