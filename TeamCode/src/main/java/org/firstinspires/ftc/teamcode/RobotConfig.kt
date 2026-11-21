package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.DcMotorSimple

object RobotConfig {
  object Controls {
    const val DEADZONE: Double = 0.05
  }

  object Launcher {
    const val LAUNCHER: String = "launcher"
    const val WINDMILL_SERVO: String = "windmill"
    val WINDMILL_DIRECTION: DcMotorSimple.Direction =
      DcMotorSimple.Direction.REVERSE

    const val PROPORTIONAL: Double = 40.0
    const val INTEGRAL: Double = 0.0
    const val DERIVATIVE: Double = 0.0
    const val FEED_FORWARD: Double = 12.5

    // Launcher velocities in encoder ticks per second (28 ticks per rev)
    const val TARGET_VELOCITY: Double = 2400.0
    const val SLOW_TARGET_VELOCITY: Double = 1200.0
    const val MIN_VELOCITY_FRACTION: Double = 0.97

    // Windmill & Intake Levels
    const val WINDMILL_POWER_IDLE: Double = 0.0
    const val WINDMILL_POWER_FEED: Double = 1.0
    const val INTAKE_BUMP: Double = 0.5

    const val REVERSE_VELOCITY: Double = -1000.0

  }

  object Drive {
    // Normal Drive Motors
    const val LEFT: String = "left_drive"
    const val RIGHT: String = "right_drive"
    val LEFT_DIRECTION: DcMotorSimple.Direction =
      DcMotorSimple.Direction.FORWARD
    val RIGHT_DIRECTION: DcMotorSimple.Direction =
      DcMotorSimple.Direction.REVERSE

    // Mecanum Drive Motors
    const val FRONT_LEFT: String = "front_left"
    const val FRONT_RIGHT: String = "front_right"
    const val BACK_LEFT: String = "back_left"
    const val BACK_RIGHT: String = "back_right"
  }

  object Intake {
    const val INTAKE: String = "intake"
    const val LEFT_SERVO: String = "left_intake_servo"
    const val RIGHT_SERVO: String = "right_intake_servo"
  }

  object Wedge {
    const val WEDGE: String = "wedge"
    const val WEDGE_UP: Double = 1.0
    const val WEDGE_DOWN: Double = 0.5
  }

  object Imu {
    const val NAME: String = "imu"
    val LOGO: RevHubOrientationOnRobot.LogoFacingDirection =
      RevHubOrientationOnRobot.LogoFacingDirection.LEFT
    val USB: RevHubOrientationOnRobot.UsbFacingDirection =
      RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
  }

  object Auto {

    const val YAW_SIGN: Double = 1.0
    const val TURN_GAIN: Double = 0.012
    const val TURN_MIN_POWER: Double = 0.15
    const val TURN_MAX_POWER: Double = 0.5
    const val TURN_TOLERANCE_DEG: Double = 2.0
    const val TURN_TIMEOUT_S: Double = 3.0
    const val HEADING_GAIN: Double = 0.02
    const val DRIVE_TIMEOUT_S: Double = 4.0
    const val SPINUP_FALLBACK_S: Double = 3.0
    const val SHOOT_SECONDS: Double = 8.0
    const val WIGGLE_POWER: Double = 0.2
    const val WIGGLE_PERIOD_S: Double = 0.4

    // Measure with MeasureRoute. 0 ticks = skip step.
    const val APPROACH_TICKS: Int = -400
    const val AIM_HEADING_DEG: Double = 0.0
    const val PARK1_HEADING_DEG: Double = 33.0
    const val PARK1_TICKS: Int = -3500
    const val PARK2_HEADING_DEG: Double = -35.0
    const val PARK2_TICKS: Int = -530

    const val DRIVE_POWER: Double = 0.5
    const val TURN_POWER: Double = 0.2
    const val STOP_POWER: Double = 0.0
    const val SETTLE_MS: Long = 150

    const val FIRST_LEG_SECONDS: Double = 0.3
    const val FIRST_TURN_SECONDS: Double = 2.75
    const val DIAGONAL_LEG_SECONDS: Double = 1.3
    const val SECOND_TURN_SECONDS: Double = 2.75
    const val FINAL_LEG_SECONDS: Double = 0.25
  }
}