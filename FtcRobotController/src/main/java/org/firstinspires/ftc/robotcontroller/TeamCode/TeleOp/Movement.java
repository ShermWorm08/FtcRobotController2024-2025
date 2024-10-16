package org.firstinspires.ftc.robotcontroller.TeamCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Movement", group = "Linear Opmode")
public class Movement extends LinearOpMode {

    // Declare motors
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    @Override
    public void runOpMode() {
        // Initialize the hardware variables
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        // Reverse the right motor (if necessary)
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the start button to be pressed
        waitForStart();

        // Run until the op mode is stopped
        while (opModeIsActive()) {
            // Control movement with gamepad
            if (gamepad1.a) {
                moveForward();
            } else if (gamepad1.b) {
                moveBackward();
            } else {
                stopMotors();
            }

            // Optional: Add a telemetry update
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }

    private void moveForward() {
        leftMotor.setPower(1.0);
        rightMotor.setPower(1.0);
    }

    private void moveBackward() {
        leftMotor.setPower(-1.0);
        rightMotor.setPower(-1.0);
    }

    private void stopMotors() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}

