package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.HardwareMap

inline fun <reified T : Any> HardwareMap.device(name: String): T = get(
  T::class.java,
  name
)