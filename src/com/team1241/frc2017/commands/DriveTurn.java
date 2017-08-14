package com.team1241.frc2017.commands;

import com.team1241.frc2017.*;

import edu.wpi.first.wpilibj.command.Command;


public class DriveTurn extends Command {
	private double speed;
	private double angle;
	private double timeOut;
	//private double tolerance;

	public DriveTurn(double angle, double speed, double timeOut) {
		this(angle, speed, timeOut, 1);
	}

    public DriveTurn(double angle, double speed, double timeOut, double tolerance) {
    	this.speed = speed;
    	this.angle = angle;
    	this.timeOut = timeOut;
    	//this.tolerance = tolerance;
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    	setTimeout(timeOut);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.driveTurn(angle, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.runLeftDrive(0);
    	Robot.drivetrain.runRightDrive(0);
    	Robot.drivetrain.resetPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.runLeftDrive(0);
		Robot.drivetrain.runRightDrive(0);
		Robot.drivetrain.resetPID();
    //	Robot.geartool.intake(1);
    	
    }
}