/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static java.lang.Thread.sleep;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "DriveUpdated", group = "Iterative Opmode")
//@Disabled
public class Drive extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor[][] drivetrainDC = new DcMotor[3][3];
    DcMotor[] intakeDC = new DcMotor[2];
    DcMotor[] relicDC = new DcMotor[1];
    double[][] sticks = new double[4][4];
    Servo[] servosGlip = new Servo[3];
    Servo servoSensor;
    Servo servoArm;
    Servo[] servosRelic = new Servo[2];
    ModernRoboticsI2cRangeSensor range;
    ColorSensor sensorColor;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        drivetrainDC[0][1] = hardwareMap.get(DcMotor.class, "right1");
        drivetrainDC[1][1] = hardwareMap.get(DcMotor.class, "right2");
        drivetrainDC[0][0] = hardwareMap.get(DcMotor.class, "left1");
        drivetrainDC[1][0] = hardwareMap.get(DcMotor.class, "left2");

 /*       intakeDC[0] = hardwareMap.get(DcMotor.class, "glipMotor");
        intakeDC[1] = hardwareMap.get(DcMotor.class, "glipMotor2");
        relicDC[0] = hardwareMap.get(DcMotor.class, "dcRelic");
        servoSensor = hardwareMap.get(Servo.class, "servoSensor");
        servoArm = hardwareMap.get(Servo.class, "servoArm");
        servosGlip[0] = hardwareMap.get(Servo.class, "scoringServo");
        servosGlip[1] = hardwareMap.get(Servo.class, "servoSide");
        servosGlip[2] = hardwareMap.get(Servo.class, "fingerServo");
        servosRelic[0] = hardwareMap.get(Servo.class, "relicServo1");
        servosRelic[1] = hardwareMap.get(Servo.class, "relicServo2");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColor");
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");*/


        for (int i = 0; i < 1; i++) {
           // for (int i = 0; i < 2; i++) {

            for (int j = 0; j < 1; j++) {
           //     for (int j = 0; j < 2; j++) {


                drivetrainDC[i][j].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                drivetrainDC[i][j].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            drivetrainDC[i][1].setDirection(DcMotorSimple.Direction.REVERSE);
        }
        //intakeDC[0].setDirection(DcMotorSimple.Direction.REVERSE);

        drivetrainDC[1][1].setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrainDC[1][1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("Status", "Initialized");
    }



    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

    }


    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    double pos2 = 0.5;
    int pos[][] = new int[2][2];
    double glyphPower = 0;
    @Override
    public void loop() {
//        telemetry.addData("y: ", gamepad1.right_stick_y);
//        telemetry.addData("x: ", gamepad1.right_stick_x);
//        telemetry.addData("motor RF", dcMotor[0][1].getPower());
//        telemetry.addData("motor RB", dcMotor[1][1].getPower());
//        telemetry.addData("motor LF", dcMotor[0][0].getPower());
//        telemetry.addData("motor LB", dcMotor[1][0].getPower());
        //servoSensor.setPosition(0);
        sticks[0][0] = gamepad1.left_stick_x;
        sticks[1][0] = gamepad1.left_stick_y;
        sticks[0][1] = gamepad1.right_stick_x;
        sticks[1][1] = gamepad1.right_stick_y;
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {
                pos[i][j] =  drivetrainDC[i][j].getCurrentPosition();
                telemetry.addData("pos", pos[i][j]);
            }
        telemetry.update();
        double serGain = 0;
        Range.clip(1, 0, serGain);
        double gain = 1;
        double strafeGain = 1;
        double intakeGain = 0;



        if (gamepad1.left_bumper)
            gain /= 1.8;
        else if (gamepad1.right_bumper)
            gain *= 1.8;
        if (gamepad1.b == true)//switch the direction of the drivetrain
            gain *= -1;
        double gain2 = 0.5;
        if (gamepad2.left_bumper)
            gain2 /= 2;
        else if (gamepad2.right_bumper)
            gain2 *= 1.6;

        telemetry.addData("gain:", gain);
        telemetry.update();



        tankDriveTrainSetPower(gain);

      //  intakeOperation(intakeGain);

        if (gamepad1.right_trigger != 0)//strafe to the right
            strafe(1);

        else if (gamepad1.left_trigger != 0)//strafe to the left
            strafe(-1);
        else
            tankDriveTrainSetPower(gain);

        if (gamepad2.a)// intake
            intakeGain = 0.7;
        else if (gamepad2.b)// backwards intake
            intakeGain = -0.7;
        else
            intakeGain = 0;

//        if(gamepad2.y)//glyphs scoring
//            glyphsServoOperation(0,1);
//        else if(gamepad2.x)
//            glyphsServoOperation(0,0);
//
//        if(gamepad2.dpad_left)//operates the side of the glyphs servo
//            glyphsServoOperation(1,1);
//        else if(gamepad2.dpad_right)
//            glyphsServoOperation(1,0);
//
//        if(gamepad2.dpad_up)//operates the "finger" at the back of the glyphs
//            glyphsServoOperation(2,1);
//        else if(gamepad2.dpad_down)
//            glyphsServoOperation(2,0);
//
//
//        if(gamepad2.left_trigger != 0)//opens the relic arm
//            relicDC[0].setPower(1);
//        else if(gamepad2.right_trigger != 0)//closes the relic arm
//            relicDC[0].setPower(-1);
//        else
//            relicDC[0].setPower(0);
    }

    void tankDriveTrainSetPower(double gain)
    {
        drivetrainDC[0][1].setPower(gain * (gamepad1.left_stick_y));
        drivetrainDC[1][1].setPower(gain * (gamepad1.left_stick_y));
        drivetrainDC[0][0].setPower(gain * (gamepad1.right_stick_y));
        drivetrainDC[1][0].setPower(gain * (gamepad1.right_stick_y));
    }

    void strafe(double gain)
    {
        double trigger = 0;
        if(gain>0)
             trigger = gamepad1.right_trigger;
        else if(gain<0)
            trigger = gamepad1.left_trigger;

        drivetrainDC[0][1].setPower(-gain * trigger);
        drivetrainDC[1][1].setPower(gain * trigger);
        drivetrainDC[0][0].setPower(gain *trigger);
        drivetrainDC[1][0].setPower(-gain * trigger);
    }

//    void glyphsServoOperation(int index, double position)
//    {
//        servosGlip[index].setPosition(position);
//    }

//    void intakeOperation(double power)
//    {
//        int i = 0;
//        for(i = 0;i<=1;i++)
//            intakeDC[i].setPower(power);
//    }

    void Sleep(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




//            dcMotor[0][1].setPower((-1) *gamepad1.right_trigger);
//            dcMotor[1][1].setPower( gamepad1.right_trigger);
//            dcMotor[0][0].setPower( gamepad1.right_trigger);
//            dcMotor[1][0].setPower((-1) *gamepad1.right_trigger);




//            dcMotor[0][1].setPower( gamepad1.left_trigger);
//            dcMotor[1][1].setPower((-1) *gamepad1.left_trigger);
//            dcMotor[0][0].setPower((-1) *gamepad1.left_trigger);
//            dcMotor[1][0].setPower( gamepad1.left_trigger);


    @Override

    public void stop() {
    }

}