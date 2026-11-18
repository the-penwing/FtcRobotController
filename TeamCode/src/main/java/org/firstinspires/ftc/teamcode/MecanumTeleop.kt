package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import kotlin.math.abs

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
      mecanumDrive(
        -gamepad1.left_stick_y.toDouble(),
        gamepad1.left_stick_x.toDouble(),
        gamepad1.right_stick_x.toDouble()
      )
      telemetry.update()
    }
  }

  fun mecanumDrive(
    forward: Double,
    strafe: Double,
    rotate: Double
  ) {
    var frontLeft = forward + strafe + rotate
    var frontRight = forward - strafe - rotate
    var backLeft = forward - strafe + rotate
    var backRight = forward + strafe - rotate
    val biggest = maxOf(
      abs(frontLeft),
      abs(frontRight),
      abs(backLeft),
      abs(backRight)
    )
    if (biggest > 1.0) {
      frontLeft /= biggest
      frontRight /= biggest
      backLeft /= biggest
      backRight /= biggest
    }
    frontLeftDrive.power = frontLeft
    frontRightDrive.power = frontRight
    backLeftDrive.power = backLeft
    backRightDrive.power = backRight
    telemetry.addData(
      "Front left",
      frontLeft
    )
    telemetry.addData(
      "Front right",
      frontRight
    )
    telemetry.addData(
      "Back left",
      backLeft
    )
    telemetry.addData(
      "Back right",
      backRight
    )
  }
}