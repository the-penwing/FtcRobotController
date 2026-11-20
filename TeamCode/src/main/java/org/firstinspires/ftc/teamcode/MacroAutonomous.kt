package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(
    name = "Macro Autonomous",
    group = "StarterBot Kotlin"
)
class MacroAutonomous : StarterBotBase() {
    override fun runOpMode() {
        initHardware()
        telemetry.addData(
            "Status",
            "Initialised"
        )
        telemetry.update()
        waitForStart()

        runMacro()
    }

    // Not used in autonomous, but required by StarterBotBase
    override fun handleControls() {}
}