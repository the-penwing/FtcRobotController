package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(
  name = "Competition TeleOp Single Driver",
  group = "StarterBot Kotlin"
)
class CompetitionStarterBotTeleOpSingleDriver : StarterBotBase() {
  var wasWedgePressed: Boolean = false
  override fun handleControls() {
    arcadeDrive(
      -deadzone(gamepad1.left_stick_y.toDouble()),
      deadzone(gamepad1.right_stick_x.toDouble())
    )
    val down: Boolean = gamepad1.left_bumper
    val tapped: Boolean = down && !wasWedgePressed
    val upSignal = tapped && !wedgeUp
    val downSignal = tapped && wedgeUp
    updateWedge(
      upSignal,
      downSignal
    )
    wasWedgePressed = down
    val feeding: Boolean = updateLauncher(
      gamepad1.right_bumper,
      gamepad1.a
    )
    updateIntake(
      (gamepad1.right_trigger - gamepad1.left_trigger).toDouble(),
      feeding
    )
  }
}