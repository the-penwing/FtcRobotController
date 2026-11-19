package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo

@TeleOp(
    name = "Test Servos Feature",
    group = "StarterBot Kotlin"
)
class TestServos : LinearOpMode() {
    lateinit var leftIntakeServo: CRServo
    lateinit var rightIntakeServo: CRServo
    lateinit var windmillServo: CRServo
    override fun runOpMode() {
        leftIntakeServo = hardwareMap.device(RobotConfig.Intake.LEFT_SERVO)
        rightIntakeServo = hardwareMap.device(RobotConfig.Intake.RIGHT_SERVO)
        windmillServo = hardwareMap.device(RobotConfig.Launcher.WINDMILL_SERVO)

        leftIntakeServo.power = 0.0
        rightIntakeServo.power = 0.0
        windmillServo.power = 0.0


        telemetry.update()
        waitForStart()
        while (opModeIsActive()) {
            moveServo(leftIntakeServo,gamepad1.a)
            moveServo(rightIntakeServo,gamepad1.b)
            moveServo(windmillServo,gamepad1.x)
            telemetry.update()

        }
    }

    fun moveServo(s: CRServo, b: Boolean) {
        if (b) {
            s.power = 1.0
        } else {
            s.power = 0.0
        }
    }

}