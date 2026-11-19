package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.DcMotorSimple

object RobotConfig {
  object Launcher {
    const val LAUNCHER: String = "launcher"
    const val WINDMILL_SERVO: String = "windmill"
    val WINDMILL_DIRECTION: DcMotorSimple.Direction =
      DcMotorSimple.Direction.FORWARD

    const val PROPORTIONAL: Double = 40.0
    const val INTEGRAL: Double = 0.0
    const val DERIVATIVE: Double = 0.0
    const val FEED_FORWARD: Double = 12.5

    // Launcher velocities in encoder ticks per second (28 ticks per rev)
    const val TARGET_VELOCITY: Double = 1250.0

    // TODO: Tune on robot
    const val MIN_VELOCITY_FRACTION: Double = 0.85
    const val MIN_VELOCITY: Double = TARGET_VELOCITY * MIN_VELOCITY_FRACTION
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
    val LEFT_SERVO_DIRECTION: DcMotorSimple.Direction =
      DcMotorSimple.Direction.FORWARD
    val RIGHT_SERVO_DIRECTION: DcMotorSimple.Direction =
      DcMotorSimple.Direction.REVERSE
  }
}