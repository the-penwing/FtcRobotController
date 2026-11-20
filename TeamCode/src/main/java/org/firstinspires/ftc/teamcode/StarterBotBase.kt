package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.abs

abstract class StarterBotBase : LinearOpMode() {
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
      handleControls()

      telemetry.addData(
        "Launcher Velocity",
        launcher.velocity
      )
      telemetry.addData(
        "Wedge Up",
        wedgeUp
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

  abstract fun handleControls()

  fun arcadeDrive(
    forward: Double,
    rotate: Double
  ) {
    leftDrive.power = forward + rotate
    rightDrive.power = forward - rotate
  }

  fun updateWedge(
    upPressed: Boolean,
    downPressed: Boolean
  ) {
    wedgeUp = upPressed || !downPressed && wedgeUp
    if (wedgeUp) {
      wedge.position = RobotConfig.Wedge.WEDGE_UP
    } else {
      wedge.position = RobotConfig.Wedge.WEDGE_DOWN
    }
  }

  fun updateIntake(
    power: Double,
    feeding: Boolean
  ) {
    // Right trigger in | Left trigger out
    intake.power = power
    if (feeding) {
      intake.power += RobotConfig.Launcher.INTAKE_BUMP
    }
  }

  fun updateLauncher(
    fastShot: Boolean,
    slowShot: Boolean
  ): Boolean {
    val target = if (fastShot) {
      RobotConfig.Launcher.TARGET_VELOCITY
    } else if (slowShot) {
      RobotConfig.Launcher.SLOW_TARGET_VELOCITY
    } else {
      0.0
    }
    launcher.velocity = target
    telemetry.addData(
      "Launcher Target",
      target
    )

    val feeding =
      if (target > 0.0 && abs(launcher.velocity) > target * RobotConfig.Launcher.MIN_VELOCITY_FRACTION) {
        windmillServo.power = RobotConfig.Launcher.WINDMILL_POWER_FEED
        true
      } else {
        windmillServo.power = RobotConfig.Launcher.WINDMILL_POWER_IDLE
        false
      }
    return feeding
  }
}