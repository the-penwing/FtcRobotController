package org.firstinspires.ftc.teamcode

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
    const val TARGET_VELOCITY: Double = 1250.0
    const val SLOW_TARGET_VELOCITY: Double = 650.0
    // TODO: Tune on robot
    const val MIN_VELOCITY_FRACTION: Double = 0.85
    // Windmill & Intake Levels
    const val WINDMILL_POWER_IDLE: Double = 0.0
    const val WINDMILL_POWER_FEED: Double = 1.0
    const val INTAKE_BUMP: Double = 0.5

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
    const val WEDGE_DOWN: Double = 0.65
  }

  object Auto {
    const val DRIVE_POWER: Double = 0.5
    const val TURN_POWER: Double = 0.4
    const val STOP_POWER: Double = 0.0
    const val SETTLE_MS: Long = 150

    const val FIRST_LEG_SECONDS: Double = 1.0
    const val FIRST_TURN_SECONDS: Double = 0.86
    const val DIAGONAL_LEG_SECONDS: Double = 1.4
    const val SECOND_TURN_SECONDS: Double = 0.86
    const val FINAL_LEG_SECONDS: Double = 0.9
  }
}