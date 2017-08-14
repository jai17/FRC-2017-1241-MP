package com.team1241.frc2017.subsystems;

import java.nio.channels.DatagramChannel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double UDP() throws IOException{
    	String text;
    	int server_port = 5808;
    	byte[] message = new byte[1500];
    	DatagramPacket p = new DatagramPacket(message, message.length);
    	DatagramSocket s = new DatagramSocket(server_port);
    	s.receive(p);
    	text = new String(message, 0, p.getLength());
    	s.close();
    	
    	SmartDashboard.putString("UDP Received", text);
    	
    	return Double.parseDouble(text);
    }
    
}

