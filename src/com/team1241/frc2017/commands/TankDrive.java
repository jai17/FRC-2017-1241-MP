package com.team1241.frc2017.commands;

import com.team1241.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

	public TankDrive() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.oi.getDriveStartButton()){
		///	
		} else if (Robot.oi.getDriveBButton()){
			Robot.drivetrain.reset();
		} else{
			Robot.drivetrain.runLeftDrive(0.25*Robot.oi.getDriveLeftY());
			Robot.drivetrain.runRightDrive(0.25*-Robot.oi.getDriveRightY());
		}
		if (Robot.oi.getDriveAButton()){
			Robot.drivetrain.runMotionProfile();
		}
	}
		
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
