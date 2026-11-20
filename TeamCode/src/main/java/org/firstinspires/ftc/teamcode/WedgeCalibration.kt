    package org.firstinspires.ftc.teamcode

    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp
    import com.qualcomm.robotcore.hardware.Servo

    @TeleOp(
        name = "Test Servos Feature",
        group = "StarterBot Kotlin"
    )
    class WedgeCalibration : LinearOpMode() {
        lateinit var wedgeServo: Servo

        override fun runOpMode() {
            wedgeServo = hardwareMap.device(RobotConfig.Wedge.WEDGE)
            wedgeServo.position = 1.0
            telemetry.update()

            waitForStart()

            while (opModeIsActive()) {
                telemetry.update()
            }
        }
    }