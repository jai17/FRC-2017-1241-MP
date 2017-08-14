/*package com.team1241.frc2017.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.sf2.frc.navXSensor;
import com.kauailabs.sf2.orientation.OrientationHistory;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.sf2.orientation.Quaternion;
import com.kauailabs.sf2.time.TimestampedValue;

import edu.wpi.first.wpilibj.command.Subsystem;

*//**
 *
 *//*
public class LatencyCorrection extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    OrientationHistory orientation_history;
    AHRS ahrs;

    public LatencyCorrection(){
    	 Instantiate the sensor and the history; the history acquires data 
    	 * continuously from the sensor.  Set the depth of the Orientation Time
    	 * History to 10 seconds, based upon the sensors current update rate.
    	 
        ahrs = new AHRS(SPI.Port.kMXP);
        navXSensor navx_sensor = new navXSensor(ahrs, "Drivetrain Orientation");
        orientation_history = new OrientationHistory(navx_sensor,
    		ahrs.getRequestedUpdateRate() * 10);
        
        long navx_timestamp = ahrs.getLastSensorTimestamp();
        navx_timestamp -= 1000;  look 1 second backwards in time 
        TimestampedValue<Quaternion> historical_quat = orientation_history.getQuaternionAtTime(navx_timestamp);
        
        float historical_yaw = orientation_history.getYawDegreesAtTime(navx_timestamp);
        float historical_pitch = orientation_history.getPitchDegreesAtTime(navx_timestamp);
        float historical_roll = orientation_history.getRollDegreesAtTime(navx_timestamp);

         Acquire Current Orientation Data 
        float curr_yaw = ahrs.getYaw();
        float curr_pitch = ahrs.getPitch();
        float curr_roll = ahrs.getRoll();
        
         Calculate orientation change 
        float delta_yaw = curr_yaw - historical_yaw;
        float delta_pitch = curr_pitch - historical_pitch;
        float delta_roll = curr_roll - historical_roll;
        
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

*/