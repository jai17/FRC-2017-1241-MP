package com.team1241.frc2017;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//**************************************************************************
	//***************************** DRIVETRAIN *********************************
	//**************************************************************************        
		
		public static final int RIGHT_DRIVE_FRONT                               = 2; 
		public static final int RIGHT_DRIVE_BACK                                = 3;
		
		public static final int LEFT_DRIVE_FRONT                                = 1;
		public static final int LEFT_DRIVE_BACK                                 = 4;
		
	//**************************************************************************
	//********************* DRIVE ENCODER CONSTANTS ****************************
	//**************************************************************************
		
		public static final int driveWheelRadius = 2;//wheel radius in inches
		public static final double driveGearRatio = 1/1; //ratio between wheel and encoder
		public static final double ROTATIONS_TO_INCHES 	= (2*Math.PI*driveWheelRadius*driveGearRatio);
		
		
}
