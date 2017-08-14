
package com.team1241.frc2017;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

import com.team1241.frc2017.commands.ExampleCommand;
import com.team1241.frc2017.subsystems.Drivetrain;
import com.team1241.frc2017.subsystems.ExampleSubsystem;
import com.team1241.frc2017.subsystems.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Vision vision = new Vision();
	public static OI oi;
	
	NetworkTable table;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	double prevTime = 0;
	double pingTime = 0;
	
	Timer timer;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		table = NetworkTable.getTable("VisionTable");
		timer = new Timer();
		timer.stop();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		SmartDashboard.putString("wassup", "nm just chillin");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		timer.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		String input;
		double time;
		double udpAngle = 0;
		double center;
		double Angle;
		double focalLength;
		double FOV;
		double width;
		
		String text;
		
		SmartDashboard.putString("wassup", "oh shit we live bby");
		
		input = table.getString("Phone Message", "test");
		time = table.getNumber("System Time", 0);
		center = table.getNumber("Center", 0);
		Angle = table.getNumber("Angle", 0);
		focalLength = table.getNumber("Focal Length", 0);
		FOV = table.getNumber("Field of View",0);
		width = table.getNumber("Width", 0);
		
		SmartDashboard.putString("Input", input);
		
		SmartDashboard.putNumber("wuz da time fam", time);
		
		try {
			udpAngle = vision.UDP();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Vision (NetworkTable)
		SmartDashboard.putBoolean("Phone Connected", table.getBoolean("Phone Connected", false));
		SmartDashboard.putNumber("Center", center);
		SmartDashboard.putNumber("Vision Angle", Angle);
		SmartDashboard.putNumber("Focal Length", focalLength);
		SmartDashboard.putNumber("FOV", FOV);
		SmartDashboard.putNumber("Width", width);
		
		//Robot
		SmartDashboard.putNumber("Robot Angle", drivetrain.getYaw());
		
		//UDP
		SmartDashboard.putNumber("Ping Time", 1/pingTime);
		SmartDashboard.putNumber("UDP Angle", udpAngle);
		
		if(Robot.oi.getDriveAButton()){
    		drivetrain.reset();
    	}
		
		if(Robot.oi.getDriveStartButton()){
			//new DriveTurn(drivetrain.getYaw() + udpAngle, 0.25, 3).start();
			drivetrain.driveTurn(drivetrain.getYaw() + udpAngle, 0.25);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
}
