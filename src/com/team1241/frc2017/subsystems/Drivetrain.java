package com.team1241.frc2017.subsystems;

import com.team1241.frc2017.RobotMap;
import com.team1241.frc2017.commands.MotionProfile;
import com.team1241.frc2017.commands.TankDrive;
import com.team1241.frc2017.utilities.NumberConstants;
import com.team1241.frc2017.utilities.PIDController;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {

    private CANTalon leftDriveBack;
    private CANTalon leftDriveFront;
    private CANTalon rightDriveBack;
    private CANTalon rightDriveFront;
    
    AHRS gyro;

    public PIDController drivePID;
    public PIDController gyroPID;
    
    MotionProfile profileLeft;
    MotionProfile profileRight;
    
    public Drivetrain(){
    	// Motion Profile 
    	profileLeft = new MotionProfile(leftDriveBack);
    	profileRight = new MotionProfile(rightDriveBack);
    	
    	// left front
 		leftDriveFront = new CANTalon(RobotMap.LEFT_DRIVE_FRONT);

 		// left back
 		leftDriveBack = new CANTalon(RobotMap.LEFT_DRIVE_BACK);
 		leftDriveBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
 		leftDriveBack.reverseSensor(true);
 		leftDriveBack.configEncoderCodesPerRev(1024);
 		
 		// right front
 		rightDriveFront = new CANTalon(RobotMap.RIGHT_DRIVE_FRONT);

 		// right back
 		rightDriveBack = new CANTalon(RobotMap.RIGHT_DRIVE_BACK);
 		rightDriveBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
 		rightDriveBack.reverseSensor(false);
 		rightDriveBack.configEncoderCodesPerRev(1024);
 		
 		leftDriveBack.setF(0.09106);		//FGain needs to 
 											//(Max RPM/Min)*(1Min/60 Secs)*(1/10 v)*(4096 native units/rotation) = Y
 											//F-Gain = (100% * 1023)/ Y
 		leftDriveFront.setF(0.09106);
 		rightDriveBack.setF(0.09106);
 		rightDriveFront.setF(0.09106);

 		
 		drivePID = new PIDController(NumberConstants.pDrive, NumberConstants.iDrive, NumberConstants.dDrive);
		gyroPID = new PIDController(NumberConstants.pGyro, NumberConstants.iGyro, NumberConstants.dGyro);
 		
		try {
			gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("ERROR navX: " + ex.getMessage(), true);
		}
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TankDrive());
    }
    public void runMotionProfile(){
    	profileLeft.control();
    	profileRight.control();
    	
    	leftDriveBack.changeControlMode(TalonControlMode.MotionProfile);
    	leftDriveFront.changeControlMode(TalonControlMode.MotionProfile);
		CANTalon.SetValueMotionProfile setOutputL = profileLeft.getSetValue();	
		leftDriveBack.set(setOutputL.value);
		leftDriveFront.set(setOutputL.value);
		
		rightDriveBack.changeControlMode(TalonControlMode.MotionProfile);
		rightDriveFront.changeControlMode(TalonControlMode.MotionProfile);
		CANTalon.SetValueMotionProfile setOutputR = profileRight.getSetValue();	
		rightDriveBack.set(setOutputR.value);
		rightDriveFront.set(setOutputR.value);
		
		profileRight.startMotionProfile();
		profileLeft.startMotionProfile();
    }
    
    public void runLeftDrive(double pwmVal) {
		leftDriveBack.set(pwmVal);
		leftDriveFront.set(pwmVal);
	}

	public void runRightDrive(double pwmVal) {
		rightDriveBack.set(pwmVal);
		rightDriveFront.set(pwmVal);
	}
	
	public void driveTurn(double setAngle, double speed) {
		double angle = gyroPID.calcPID(setAngle, getYaw(), 1);

		runLeftDrive ((-angle) * speed);
		runRightDrive ((-angle) * speed);
	}
	
	public void driveSetpoint(double setPoint, double speed, double setAngle) {
		double output = drivePID.calcPID(setPoint, getAverageDistance(), 1);
		double angle = gyroPID.calcPID(setAngle, getYaw(), 1);

		runLeftDrive ((-output - angle) * speed);
		runRightDrive ((output - angle) * speed);
	}
	
	// ************************Encoder Functions************************
		public double getLeftEncoderDist() {
			return leftDriveBack.getPosition()*12.44;// * RobotMap.ROTATIONS_TO_INCHES;
		}

		public double getRightEncoderDist() {
			return rightDriveBack.getPosition()*12.44;// * RobotMap.ROTATIONS_TO_INCHES;
		}

		public double getAverageDistance() {
			return (getLeftEncoderDist() + getRightEncoderDist()) / 2;
		}

		public void resetEncoders() {
			leftDriveBack.setPosition(0);
			rightDriveBack.setPosition(0);
		}

		/************************ GYRO FUNCTIONS ************************/

		/**
		 * This function is used to check if the gyro is connected
		 * 
		 * @return Returns true or false depending on the state of the gyro
		 */
		public boolean gyroConnected() {
			return gyro.isConnected();
		}

		/**
		 * This function is used to check if the gyro is calibrating
		 * 
		 * @return Returns true or false depending on the state of the gyro
		 */
		public boolean gyroCalibrating() {
			return gyro.isCalibrating();
		}

		/**
		 * This function returns the YAW reading from the gyro
		 * 
		 * @return Returns YAW
		 */
		public double getYaw() {
			return gyro.getAngle();
		}

		public void resetGyro() {
			gyro.zeroYaw();
		}
		
		public void resetPID(){
			drivePID.resetPID();
			gyroPID.resetPID();
		}
		
		public void reset(){
			resetGyro();
			resetEncoders();
		}
}

