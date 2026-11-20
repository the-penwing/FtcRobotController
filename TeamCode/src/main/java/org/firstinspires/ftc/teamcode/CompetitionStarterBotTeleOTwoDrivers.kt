package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.abs

@TeleOp(
  name = "Competition Starter Bot TeleOp Two Drivers",
  group = "StarterBot Kotlin"
)
class CompetitionStarterBotTeleOpTwoDrivers : LinearOpMode() {
  // Drive Devices
  lateinit var leftDrive: DcMotor
  lateinit var rightDrive: DcMotor

  // Intake Devices
  lateinit var intake: DcMotor

  // Wedge
  lateinit var wedge: Servo
  var wedgeUp: Boolean = true

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
      updateWedge()
      updateIntake()
      telemetry.addData(
        "Wedge Up",
        wedgeUp
      )
      updateLauncher()
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
    wedge = hardwareMap.device(RobotConfig.Wedge.WEDGE)
    wedge.position = RobotConfig.Wedge.WEDGE_UP
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

  fun updateIntake() {
    // Right trigger in | Left trigger out
    intake.power = (gamepad2.right_trigger - gamepad2.left_trigger).toDouble()
  }

  fun updateWedge() {
    wedgeUp = gamepad2.dpad_up || !gamepad2.dpad_down && wedgeUp
    if (wedgeUp) {
      wedge.position = RobotConfig.Wedge.WEDGE_UP
    } else {
      wedge.position = RobotConfig.Wedge.WEDGE_DOWN
    }
  }

  fun updateLauncher() {
    val target = if (gamepad2.y) {
      RobotConfig.Launcher.TARGET_VELOCITY
    } else if (gamepad2.x) {
      RobotConfig.Launcher.SLOW_TARGET_VELOCITY
    } else {
      0.0
    }
    launcher.velocity = target
    telemetry.addData(
      "Launcher Target",
      target
    )

    if (target > 0.0 && abs(launcher.velocity) > target * RobotConfig.Launcher.MIN_VELOCITY_FRACTION) {
      windmillServo.power = RobotConfig.Launcher.WINDMILL_POWER_FEED
      // only works if updateIntake() runs first
      intake.power += RobotConfig.Launcher.INTAKE_BUMP
    } else {
      windmillServo.power = RobotConfig.Launcher.WINDMILL_POWER_IDLE
    }
  }
}