package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDFCoefficients

// Launcher velocities in encoder ticks per second (28 ticks per rev)
const val LAUNCHER_TARGET_VELOCITY: Double = 1250.0
const val LAUNCHER_MIN_VELOCITY: Double = 1200.0 /* TODO: Tune on robot */

@TeleOp(
  name = "StarterBot Kotlin Teleop",
  group = "StarterBot Kotlin"
)
class StarterBotKotlinTeleop : OpMode() {
  // Hardware
  lateinit var leftDrive: DcMotor
  lateinit var rightDrive: DcMotor
  lateinit var intake: DcMotor
  lateinit var launcher: DcMotorEx
  lateinit var leftIntakeServo: CRServo
  lateinit var rightIntakeServo: CRServo
  lateinit var windmillServo: CRServo

  // Power Variables
  var leftPower: Double = 0.0
  var rightPower: Double = 0.0
  var intakePower: Double = 0.0

  override fun init() {
    // Names must match the robot configuration on the Driver Station
    leftDrive = hardwareMap.get(
      DcMotor::class.java,
      "left_drive"
    )
    rightDrive = hardwareMap.get(
      DcMotor::class.java,
      "right_drive"
    )
    intake = hardwareMap.get(
      DcMotor::class.java,
      "intake"
    )
    launcher = hardwareMap.get(
      DcMotorEx::class.java,
      "launcher"
    )
    windmillServo = hardwareMap.get(
      CRServo::class.java,
      "windmill"
    )
    leftIntakeServo = hardwareMap.get(
      CRServo::class.java,
      "left_intake_servo"
    )
    rightIntakeServo = hardwareMap.get(
      CRServo::class.java,
      "right_intake_servo"
    )

    // One side reversed so pushing the stick forward, drives forward
    leftDrive.direction = DcMotorSimple.Direction.FORWARD
    rightDrive.direction = DcMotorSimple.Direction.REVERSE

    // Brake mode: motors stop quickly instead of coasting
    leftDrive.zeroPowerBehavior = BRAKE
    rightDrive.zeroPowerBehavior = BRAKE
    intake.zeroPowerBehavior = BRAKE

    // Launcher uses closed-loop velocity control with custom PIDF
    launcher.mode = DcMotor.RunMode.RUN_USING_ENCODER
    launcher.setPIDFCoefficients(
      DcMotor.RunMode.RUN_USING_ENCODER,
      PIDFCoefficients(
        40.0,
        0.0,
        0.0,
        12.5
      )
    )

    // Initialise the servo controller
    leftIntakeServo.power = 0.0
    rightIntakeServo.power = 0.0
    windmillServo.power = 0.0

    // Right intake servo reversed so both pull elements in
    rightIntakeServo.direction = DcMotorSimple.Direction.REVERSE
    windmillServo.direction = DcMotorSimple.Direction.FORWARD

    telemetry.addData(
      "Status",
      "Initialised"
    )
  }

  override fun init_loop() {
  }

  override fun start() {
  }

  override fun loop() {
    // Left stick forward/back, right stick turn
    arcadeDrive(
      -gamepad1.left_stick_y.toDouble(),
      gamepad1.right_stick_x.toDouble()
    )

    // Right trigger in, left trigger out
    intakePower = (gamepad1.right_trigger - gamepad1.left_trigger).toDouble()

    // launch() may add to intakePower, so it runs before the intake is set
    launch()

    intake.power = intakePower
    leftIntakeServo.power = intakePower
    rightIntakeServo.power = intakePower

    telemetry.addData(
      "Motors",
      "left (%.2f), right (%.2f)",
      leftPower,
      rightPower
    )
    telemetry.addData(
      "launcher velocity",
      launcher.velocity
    )
    telemetry.addLine()
  }

  override fun stop() {
  }

  fun arcadeDrive(
    forward: Double,
    rotate: Double
  ) {
    leftPower = forward + rotate
    rightPower = forward - rotate

    leftDrive.power = leftPower
    rightDrive.power = rightPower
  }

  fun launch() {
    // Right bumper held: spin up the launcher, otherwise spin down
    if (gamepad1.right_bumper) {
      launcher.velocity = LAUNCHER_TARGET_VELOCITY
    } else {
      launcher.velocity = 0.0
    }

    // Feed only while the bumper is held and the launcher is fast enough.
    if (gamepad1.right_bumper && launcher.velocity > LAUNCHER_MIN_VELOCITY) {
      windmillServo.power = 1.0
      intakePower += 0.5 // extra intake power can free stuck elements
    } else {
      windmillServo.power = 0.0
    }
  }
}