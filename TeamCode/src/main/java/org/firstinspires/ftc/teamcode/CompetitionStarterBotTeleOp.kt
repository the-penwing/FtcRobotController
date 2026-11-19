package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.PIDFCoefficients

@TeleOp(
  name = "Competition Starter Bot TeleOp",
  group = "StarterBot Kotlin"
)
class CompetitionStarterBotTeleOp : LinearOpMode() {
  // Drive Devices
  lateinit var leftDrive: DcMotor
  lateinit var rightDrive: DcMotor

  // Intake Devices
  lateinit var intake: DcMotor

  // Launcher Devices
  lateinit var launcher: DcMotorEx
  lateinit var windmillServo: CRServo

  override fun runOpMode() {
    initHardware()
    telemetry.addData(
      "Status",
      "Initialised"
    )
    telemetry.update()
    waitForStart()

    while (opModeIsActive()) {
      telemetry.addData(
        "Launcher Velocity",
        launcher.velocity
      )
      arcadeDrive(
        -deadzone(gamepad1.left_stick_y.toDouble()),
        deadzone(gamepad1.right_stick_x.toDouble())
      )
      telemetry.update()
    }
  }

  fun initHardware() {
    // Drive Setup
    leftDrive = hardwareMap.device(RobotConfig.Drive.LEFT)
    rightDrive = hardwareMap.device(RobotConfig.Drive.RIGHT)
    leftDrive.direction = RobotConfig.Drive.LEFT_DIRECTION
    rightDrive.direction = RobotConfig.Drive.RIGHT_DIRECTION
    rightDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    leftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    // Intake Setup
    intake = hardwareMap.device(RobotConfig.Intake.INTAKE)
    intake.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

    // Launcher Setup
    launcher = hardwareMap.device(RobotConfig.Launcher.LAUNCHER)
    windmillServo = hardwareMap.device(RobotConfig.Launcher.WINDMILL_SERVO)
    launcher.mode = DcMotor.RunMode.RUN_USING_ENCODER
    launcher.setPIDFCoefficients(
      DcMotor.RunMode.RUN_USING_ENCODER,
      PIDFCoefficients(
        RobotConfig.Launcher.PROPORTIONAL,
        RobotConfig.Launcher.INTEGRAL,
        RobotConfig.Launcher.DERIVATIVE,
        RobotConfig.Launcher.FEED_FORWARD
      )
    )
    windmillServo.direction = RobotConfig.Launcher.WINDMILL_DIRECTION
    windmillServo.power = 0.0
  }

  fun arcadeDrive(
    forward: Double,
    rotate: Double
  ) {
    leftDrive.power = forward + rotate
    rightDrive.power = forward - rotate
  }
}