    package org.firstinspires.ftc.robotcontroller.TeamCode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic Tank Movement", group="Linear OpMode")

public class BasicTankMovement extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor l1 = null;
    private DcMotor r1 = null;
    private DcMotor l2 = null;
    private DcMotor r2 = null;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        l1  = hardwareMap.get(DcMotor.class, "left_drive1");
        r1 = hardwareMap.get(DcMotor.class, "right_drive1");
        l2  = hardwareMap.get(DcMotor.class, "left_drive2");
        r2 = hardwareMap.get(DcMotor.class, "right_drive2");

        l1.setDirection(DcMotor.Direction.REVERSE);
        r1.setDirection(DcMotor.Direction.FORWARD);
        l2.setDirection(DcMotor.Direction.REVERSE);
        r2.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double leftPower1;
            double rightPower1;
            double leftPower2;
            double rightPower2;

            leftPower1  = -gamepad1.left_stick_y ;
            leftPower2  = -gamepad1.left_stick_y ;
            rightPower1 = -gamepad1.right_stick_y ;
            rightPower2 = -gamepad1.right_stick_y ;

            l1.setPower(leftPower1);
            l2.setPower(leftPower2);
            r1.setPower(rightPower1);
            r2.setPower(rightPower2);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower1, rightPower1);
            telemetry.update();
        }
    }
}

