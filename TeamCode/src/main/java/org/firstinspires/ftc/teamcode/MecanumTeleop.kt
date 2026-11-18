package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp(
  name = "Mecanum TeleOp Kotlin",
  group = "Mecanum Kotlin"
)
class MecanumTeleop : LinearOpMode() {
  lateinit var frontLeftDrive: DcMotor
  lateinit var frontRightDrive: DcMotor
  lateinit var backLeftDrive: DcMotor
  lateinit var backRightDrive: DcMotor
  override fun runOpMode() {
    frontLeftDrive = hardwareMap.device(RobotConfig.Drive.FRONT_LEFT)
    frontRightDrive = hardwareMap.device(RobotConfig.Drive.FRONT_RIGHT)
    backLeftDrive = hardwareMap.device(RobotConfig.Drive.BACK_LEFT)
    backRightDrive = hardwareMap.device(RobotConfig.Drive.BACK_RIGHT)
    telemetry.update()
    waitForStart()

    while (opModeIsActive()) {
      telemetry.update()
    }
  }
}