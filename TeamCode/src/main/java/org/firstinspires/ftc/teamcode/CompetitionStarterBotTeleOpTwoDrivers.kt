package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(
  name = "Competition TeleOp Two Drivers",
  group = "StarterBot Kotlin"
)
class CompetitionStarterBotTeleOpTwoDrivers : StarterBotBase() {
  override fun handleControls() {
    if (gamepad1.y) {
      runMacro()
      return
    }

    arcadeDrive(
      -deadzone(gamepad1.left_stick_y.toDouble()),
      deadzone(gamepad1.right_stick_x.toDouble())
    )
    updateWedge(
      gamepad2.dpad_up,
      gamepad2.dpad_down
    )
    val feeding: Boolean = updateLauncher(
      gamepad2.y,
      gamepad2.x
    )
    updateIntake(
      (gamepad2.right_trigger - gamepad2.left_trigger).toDouble(),
      feeding
    )
  }
}