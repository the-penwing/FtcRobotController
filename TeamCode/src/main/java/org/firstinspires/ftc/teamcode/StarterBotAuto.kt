package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.IMU
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import kotlin.math.abs

/**
 * Shared autonomous routine for both alliances.
 *
 * side = 1.0 for the layout you measured, -1.0 for its mirror image.
 * Every heading below is relative to the way the robot faces at the start
 * (yaw is reset after waitForStart). Positive heading = turn left.
 */
abstract class StarterBotAuto(private val side: Double) : StarterBotBase() {
  lateinit var imu: IMU

  // Not used in autonomous, but required by StarterBotBase
  override fun handleControls() {}

  override fun runOpMode() {
    initHardware()
    initImu()
    telemetry.addData(
      "Status",
      "Initialised"
    )
    telemetry.update()
    waitForStart()

    imu.resetYaw()
    resetDriveEncoders()
    runAuto()
    stopEverything()
  }

  private fun runAuto() {
    // 1. Drive straight from the start to the shooting spot
    driveTicks(
      RobotConfig.Auto.APPROACH_TICKS,
      0.0
    )
    // 2. Aim at the upward-facing CELL and shoot the 4 preloaded POLLEN
    turnTo(side * RobotConfig.Auto.AIM_HEADING_DEG)
    shoot(RobotConfig.Auto.SHOOT_SECONDS)
    // 3. Park (up to two legs, a leg with 0 ticks is skipped)
    leg(
      side * RobotConfig.Auto.PARK1_HEADING_DEG,
      RobotConfig.Auto.PARK1_TICKS
    )
    leg(
      side * RobotConfig.Auto.PARK2_HEADING_DEG,
      RobotConfig.Auto.PARK2_TICKS
    )
  }

  private fun leg(
    headingDegrees: Double,
    ticks: Int
  ) {
    if (ticks == 0) return
    turnTo(headingDegrees)
    driveTicks(
      ticks,
      headingDegrees
    )
  }

  private fun initImu() {
    imu = hardwareMap.device(RobotConfig.Imu.NAME)
    imu.initialize(
      IMU.Parameters(
        RevHubOrientationOnRobot(
          RobotConfig.Imu.LOGO,
          RobotConfig.Imu.USB
        )
      )
    )
  }

  // Yaw in degrees, positive = robot turned left (counter-clockwise)
  private fun yaw(): Double = RobotConfig.Auto.YAW_SIGN *
    imu.robotYawPitchRollAngles.getYaw(AngleUnit.DEGREES)

  // Wrap an angle into -180..180 so turns always take the short way round
  private fun wrap(degrees: Double): Double {
    var d = degrees % 360.0
    if (d > 180.0) d -= 360.0
    if (d < -180.0) d += 360.0
    return d
  }

  private fun resetDriveEncoders() {
    leftDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    rightDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    leftDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    rightDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
  }

  private fun travelled(): Int =
    (abs(leftDrive.currentPosition) + abs(rightDrive.currentPosition)) / 2

  private fun stopDrive() = arcadeDrive(
    0.0,
    0.0
  )

  // Drive until the encoders say we have gone `ticks`, holding a heading.
  // Negative ticks drives backwards. Stops on a timeout so it can never hang.
  private fun driveTicks(
    ticks: Int,
    headingDegrees: Double
  ) {
    if (ticks == 0) return
    resetDriveEncoders()
    val power = if (ticks > 0) {
      RobotConfig.Auto.DRIVE_POWER
    } else {
      -RobotConfig.Auto.DRIVE_POWER
    }
    val timer = ElapsedTime()
    while (opModeIsActive() &&
      travelled() < abs(ticks) &&
      timer.seconds() < RobotConfig.Auto.DRIVE_TIMEOUT_S
    ) {
      // Target to the left of where we face = positive error = turn left
      // = negative rotate, hence the minus sign
      val error = wrap(headingDegrees - yaw())
      arcadeDrive(
        power,
        -error * RobotConfig.Auto.HEADING_GAIN
      )
      telemetry.addData(
        "Drive",
        "%d / %d ticks",
        travelled(),
        abs(ticks)
      )
      telemetry.update()
    }
    stopDrive()
    sleep(RobotConfig.Auto.SETTLE_MS)
  }

  // Turn on the spot to an absolute heading. Stops on a timeout so it can
  // never hang if the sign is wrong.
  private fun turnTo(targetDegrees: Double) {
    val timer = ElapsedTime()
    while (opModeIsActive() &&
      timer.seconds() < RobotConfig.Auto.TURN_TIMEOUT_S
    ) {
      val error = wrap(targetDegrees - yaw())
      if (abs(error) < RobotConfig.Auto.TURN_TOLERANCE_DEG) break
      val power = (abs(error) * RobotConfig.Auto.TURN_GAIN).coerceIn(
        RobotConfig.Auto.TURN_MIN_POWER,
        RobotConfig.Auto.TURN_MAX_POWER
      )
      // Positive error = target is to the left = negative rotate
      arcadeDrive(
        0.0,
        if (error > 0.0) -power else power
      )
      telemetry.addData(
        "Turn",
        "yaw %.1f target %.1f",
        yaw(),
        targetDegrees
      )
      telemetry.update()
    }
    stopDrive()
    sleep(RobotConfig.Auto.SETTLE_MS)
  }

  // Spin the launcher up and feed. Uses the same "wait until fast enough"
  // gate as teleop, with a fallback so a slightly low battery can't stop
  // the first ball from ever being fed.
  private fun shoot(seconds: Double) {
    val timer = ElapsedTime()
    val wiggleTimer = ElapsedTime()
    var wiggling = false
    var everReady = false
    while (opModeIsActive() && timer.seconds() < seconds) {
      var feeding = updateLauncher(
        true,
        false
      )
      if (feeding) everReady = true
      if (!everReady && timer.seconds() > RobotConfig.Auto.SPINUP_FALLBACK_S) {
        windmillServo.power = RobotConfig.Launcher.WINDMILL_POWER_FEED
        feeding = true
      }
      // Launcher first, then intake: intake needs to know if we are feeding
      updateIntake(
        0.0,
        feeding
      )
      // Nudge forward and back while feeding so balls reach the launcher.
      // Equal time each way, so the robot ends up roughly where it started.
      // Nudge forward and back while feeding.
      if (
        feeding &&
        RobotConfig.Auto.WIGGLE_POWER > 0.0 &&
        RobotConfig.Auto.WIGGLE_PERIOD_S > 0.0
      ) {
        if (!wiggling) {
          wiggling = true
          wiggleTimer.reset()
        }

        val phase =
          (wiggleTimer.seconds() % RobotConfig.Auto.WIGGLE_PERIOD_S) /
                  RobotConfig.Auto.WIGGLE_PERIOD_S

        val power = if (phase < 0.5) {
          RobotConfig.Auto.WIGGLE_POWER
        } else {
          -RobotConfig.Auto.WIGGLE_POWER
        }

        arcadeDrive(power, 0.0)
      } else {
        wiggling = false
        stopDrive()
      }
      telemetry.addData(
        "Launcher Velocity",
        launcher.velocity
      )
      telemetry.update()
    }
    stopEverything()
  }

  // The robot must be motionless when AUTO ends
  private fun stopEverything() {
    launcher.velocity = 0.0
    windmillServo.power = RobotConfig.Launcher.WINDMILL_POWER_IDLE
    intake.power = 0.0
    stopDrive()
  }
}