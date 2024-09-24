package org.firstinspires.ftc.robotcontroller.TeamCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "AprilTag", group = "Concept")

public class AprilTag1 extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, -90, 0, 0);
    private AprilTagProcessor aprilTag;

    private VisionPortal visionPortal;

    // Define starting position
    private Position gridPosition = new Position(DistanceUnit.INCH, 0, 0, 0, 0); // x, y, z

    // Define initial position based on the detected AprilTag
    private Position initialTagPosition = new Position(DistanceUnit.INCH, 0, 0, 0, 0);
    private boolean tagDetected = false;

    @Override
    public void runOpMode() {
        initAprilTag();

        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch START to start OpMode");
        waitForStart();

        while (opModeIsActive()) {
            // Get current detections from AprilTag
            ArrayList<AprilTagDetection> currentDetections = aprilTag.getDetections();

            if (!currentDetections.isEmpty()) {
                AprilTagDetection detection = currentDetections.get(0); // Assuming we are interested in the first tag detected

                // Only set initial position if the tag is detected for the first time
                if (!tagDetected) {
                    initialTagPosition = detection.robotPose.getPosition(); // Record the position relative to the detected tag
                    tagDetected = true;
                }

                // Calculate the current position relative to the initial tag position
                Position currentPose = detection.robotPose.getPosition();
                double relativeX = currentPose.x - initialTagPosition.x;
                double relativeY = currentPose.y - initialTagPosition.y;
                double relativeZ = currentPose.z - initialTagPosition.z;

                // Update telemetry with the relative position
                telemetry.addData("Relative Position (X, Y, Z)",
                        String.format("X: %.2f, Y: %.2f, Z: %.2f",
                                relativeX, relativeY, relativeZ));
            } else {
                telemetry.addLine("No AprilTag detected.");
            }

            // Reset values if the A button is pressed
            if (gamepad1.a) {
                resetValues();
            }

            // Push telemetry to the Driver Station
            telemetry.update();

            // Manage vision portal streaming
            if (gamepad1.dpad_down) {
                visionPortal.stopStreaming();
            } else if (gamepad1.dpad_up) {
                visionPortal.resumeStreaming();
            }

            // Share the CPU
            sleep(20);
        }

        visionPortal.close();
    }

    private void initAprilTag() {
    }

    // Reset method to reset tag detection status
    private void resetValues() {
        initialTagPosition = new Position(DistanceUnit.INCH, 0, 0, 0, 0);
        tagDetected = false;
        telemetry.addLine("Values reset to zero.");
    }
}
