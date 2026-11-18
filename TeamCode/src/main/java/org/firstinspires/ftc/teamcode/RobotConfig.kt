package org.firstinspires.ftc.teamcode

object RobotConfig {
  // Launcher velocities in encoder ticks per second (28 ticks per rev)
  const val LAUNCHER_TARGET_VELOCITY: Double = 1250.0
  const val LAUNCHER_MIN_VELOCITY: Double =
    LAUNCHER_TARGET_VELOCITY * 0.85 /* TODO: Tune on robot */

  object Drive {
    const val FRONT_LEFT: String = "front_left"
    const val FRONT_RIGHT: String = "front_right"
    const val BACK_LEFT: String = "back_left"
    const val BACK_RIGHT: String = "back_right"
  }
}