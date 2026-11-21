package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

// Side +1.0 = the layout you measured. If blue turns out to be a
// rotation of red's layout instead of a mirror image, change BlueAuto to 1.0.
@Autonomous(
  name = "Auto RED",
  group = "StarterBot Kotlin",
  preselectTeleOp = "Competition TeleOp Two Drivers"
)
class RedAuto : StarterBotAuto(1.0)

@Autonomous(
  name = "Auto BLUE",
  group = "StarterBot Kotlin",
  preselectTeleOp = "Competition TeleOp Two Drivers"
)
class BlueAuto : StarterBotAuto(-1.0)
