package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.abs

inline fun <reified T : Any> HardwareMap.device(name: String): T = get(
  T::class.java,
  name
)

fun deadzone(
  value: Double,
  limit: Double = RobotConfig.Controls.DEADZONE
): Double = if (abs(value) < limit) 0.0 else value