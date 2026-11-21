package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import kotlin.math.abs

// Push the robot by hand along the route and read off the numbers.
// Press A to zero the distance and heading at any point.
@TeleOp(
  name = "Measure Route",
  group = "StarterBot Kotlin"
)
class MeasureRoute : LinearOpMode() {
  override fun runOpMode() {
    val left: DcMotor = hardwareMap.device(RobotConfig.Drive.LEFT)
    val right: DcMotor = hardwareMap.device(RobotConfig.Drive.RIGHT)
    val imu: IMU = hardwareMap.device(RobotConfig.Imu.NAME)

    // Same directions as the real auto, so counts go positive driving forward
    left.direction = RobotConfig.Drive.LEFT_DIRECTION
    right.direction = RobotConfig.Drive.RIGHT_DIRECTION
    // FLOAT so the robot can be pushed by hand
    left.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    right.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    left.power = 0.0
    right.power = 0.0

    imu.initialize(
      IMU.Parameters(
        RevHubOrientationOnRobot(
          RobotConfig.Imu.LOGO,
          RobotConfig.Imu.USB
        )
      )
    )

    fun zero() {
      left.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
      right.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
      left.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
      right.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
      imu.resetYaw()
    }

    zero()
    telemetry.addData(
      "Status",
      "Ready: A zeroes distance and heading"
    )
    telemetry.update()
    waitForStart()
    zero()

    while (opModeIsActive()) {
      if (gamepad1.a) zero()
      val rawYaw = imu.robotYawPitchRollAngles.getYaw(AngleUnit.DEGREES)
      val average = (abs(left.currentPosition) + abs(right.currentPosition)) / 2
      telemetry.addData(
        "Yaw raw (left turn should go UP)",
        String.format("%.1f", rawYaw)
      )
      telemetry.addData(
        "Heading to use in config",
        String.format("%.1f", RobotConfig.Auto.YAW_SIGN * rawYaw)
      )
      telemetry.addData(
        "Left ticks",
        left.currentPosition
      )
      telemetry.addData(
        "Right ticks",
        right.currentPosition
      )
      telemetry.addData(
        "Ticks to use in config",
        average
      )
      telemetry.update()
    }
  }
}