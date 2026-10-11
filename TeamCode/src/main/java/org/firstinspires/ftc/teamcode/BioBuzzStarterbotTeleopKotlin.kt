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
const val LAUNCHER_MIN_VELOCITY: Double = 50.0

@TeleOp(
  name = "BioBuzz StarterBot Teleop Kotlin",
  group = "StarterBot"
)
class BioBuzzStarterbotTeleopKotlin : OpMode() {
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
  }
}