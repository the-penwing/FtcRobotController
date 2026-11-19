package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.PIDFCoefficients

@TeleOp(
  name = "Competition Starter Bot TeleOp",
  group = "StarterBot Kotlin"
)
class CompetitionStarterBotTeleOp : LinearOpMode() {
  // Drive Devices
  lateinit var leftDrive: DcMotor
  lateinit var rightDrive: DcMotor

  // Intake Devices
  lateinit var intake: DcMotor
  lateinit var leftIntakeServo: CRServo
  lateinit var rightIntakeServo: CRServo

  // Launcher Devices
  lateinit var launcher: DcMotorEx
  lateinit var windmillServo: CRServo

  override fun runOpMode() {
    initHardware()
    waitForStart()

    while (opModeIsActive()) {
      telemetry.update()
    }
  }

  fun initHardware() {
    // Drive Setup
    leftDrive = hardwareMap.device(RobotConfig.Drive.LEFT)
    rightDrive = hardwareMap.device(RobotConfig.Drive.RIGHT)
    leftDrive.direction = RobotConfig.Drive.LEFT_DIRECTION
    rightDrive.direction = RobotConfig.Drive.RIGHT_DIRECTION
    rightDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    leftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    // Intake Setup
    intake = hardwareMap.device(RobotConfig.Intake.INTAKE)
    leftIntakeServo = hardwareMap.device(RobotConfig.Intake.LEFT_SERVO)
    rightIntakeServo = hardwareMap.device(RobotConfig.Intake.RIGHT_SERVO)
    intake.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    leftIntakeServo.direction = RobotConfig.Intake.LEFT_SERVO_DIRECTION
    rightIntakeServo.direction = RobotConfig.Intake.RIGHT_SERVO_DIRECTION
    leftIntakeServo.power = 0.0
    rightIntakeServo.power = 0.0
    // Launcher Setup
    launcher = hardwareMap.device(RobotConfig.Launcher.LAUNCHER)
    windmillServo = hardwareMap.device(RobotConfig.Launcher.WINDMILL_SERVO)
    launcher.mode = DcMotor.RunMode.RUN_USING_ENCODER
    launcher.setPIDFCoefficients(
      DcMotor.RunMode.RUN_USING_ENCODER,
      PIDFCoefficients(
        RobotConfig.Launcher.PROPORTIONAL,
        RobotConfig.Launcher.INTEGRAL,
        RobotConfig.Launcher.DERIVATIVE,
        RobotConfig.Launcher.FEED_FORWARD
      )
    )
    windmillServo.direction = RobotConfig.Launcher.WINDMILL_DIRECTION
    windmillServo.power = 0.0
  }
}