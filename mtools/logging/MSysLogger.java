/* Copyright 2022 Noah McLean
 *
 * Redistribution and use in source and binary forms, with
 * or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above
 *    copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or
 *    other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the
 *    names of its contributors may be used to endorse or
 *    promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package mtools.logging;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.temporal.ChronoUnit;

/**
 * This is a network logging system that is Syslog compliant (RFC 5424).
 * It is currently only UDP compatible.
 * This does not support setting the Structured Data syslog field.
 * @author Noah
 *
 */
public class MSysLogger {
	
	private final int DEFAULT_UDP_PORT = 514;
	
	private InetAddress logAddress;
	private DatagramSocket logSocket;
	private int logPort;
	private int facilityNumber;
	private String applicationName;
	
	/**
	 * The constructor for the syslog logger.
	 * Pass an application name (or pass null if it's unavailable or N/A), the facility number, and the syslog server address.
	 * Will throw an {@link IllegalArgumentException} if the facility number isn't valid (0 - 23).
	 * Sets the remote port for the syslog server to a default of 514.
	 * See the {@link LogSeverity} class for severity level definitions.
	 * See the {@link FacilityNumbers} class for facility number definitions.
	 * Uses a UTC timestamp by default.
	 * 
	 * @param appName Application Name.
	 * @param facNumber Facility Number.
	 * @param syslogAddress The address of the syslog server.
	 * @throws SocketException When the InetAddress is invalid for whatever reason.
	 */
	public MSysLogger(String appName, int facNumber, InetAddress syslogAddress) throws SocketException {
		if(appName == null)
			applicationName = "-";
		else
			applicationName = appName;
		
		FacilityNumbers.checkForValidFacility(facNumber);
		facilityNumber = facNumber;
		
		logAddress = syslogAddress;
		logSocket = new DatagramSocket();
		logPort = DEFAULT_UDP_PORT;
	}
	
	/**
	 * Allows for changing the remote port of the syslog server away from the default of 514.
	 * @param port
	 */
	public void setSyslogPort(int port) {
		logPort = port;
	}
	
	/**
	 * Not yet implemented.
	 */
	public void setTimeZone() {
		
	}
	
	/**
	 * Writes a log message.
	 * Assigns the message a severity level of "Informational" (6) by default.
	 * Assigns the NILVALUE for the process ID by default.
	 * Assigns the NILVALUE for the message ID by default.
	 * @param logMessage
	 */
	public void log(String logMessage) {
		log(logMessage, 6, "-", "-");
	}
	
	/**
	 * Writes a log message.
	 * Allows for the assigning of a severity level.
	 * Assigns the NILVALUE for the process ID by default.
	 * Assigns the NILVALUE for the message ID by default.
	 * @param logMessage
	 * @param severity
	 */
	public void log(String logMessage, int severity) {
		log(logMessage, severity, "-", "-");
	}
	
	/**
	 * Writes a log message.
	 * Allows for the assigning of a severity level.
	 * Allows for the assigning of a process ID.
	 * Assigns the NILVALUE for the message ID by default.
	 * 
	 * @param logMessage
	 * @param severity
	 * @param processID
	 */
	public void log(String logMessage, int severity, String processID) {
		log(logMessage, severity, processID, "-");
	}

	/**
	 * Writes a log message.
	 * Allows for the assigning of a severity level.
	 * Allows for the assigning of a process ID.
	 * Allows for the assigning of a message ID.
	 * 
	 * @param logMessage
	 * @param severity
	 * @param processID
	 * @param msgID
	 */
	public void log(String logMessage, int severity, String processID, String msgID) {
		
		//Does nothing if severity is legal.
		LogSeverity.checkForValidSeverity(severity);
		
		byte[] message = createLogStatement(logMessage, severity, processID, msgID).getBytes();
		DatagramPacket logPacket = new DatagramPacket(message, message.length, logAddress, logPort);
		
		try {
			logSocket.send(logPacket);
		} catch (IOException e) {
			System.err.println("Unable to send UDP log message to syslog server!!!");
			e.printStackTrace();
		}
	}

	/**
	 * Closes the UDP socket.
	 */
	public void close() {
		logSocket.close();
	}
	
	private String createLogStatement(String logMessage, int severity, String processID, String msgID) {
		
		String pri = "<" + ((facilityNumber * 8) + severity) + ">";
		String version = "1";
		String timeStamp = java.time.Clock.systemUTC().instant().truncatedTo(ChronoUnit.MICROS).toString();
		//Defaults to the NILVALUE if we can't grab the FQDN, host name, or IP address for some reason.
		String hostName = "-";
		//We don't support structured data at this time.
		String structuredData = "-";
		
		//Attempts to get the hostname.
		//If that fails, it should try to get the local IP address.
		//If that also fails, then we are stuck with the NILVALUE.
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			//we are stuck with the NILVALUE at this point.
		}
		
		String statement = pri;
		statement += version; 
		statement += " " + timeStamp;
		statement += " " + hostName;
		statement += " " + applicationName;
		statement += " " + processID;
		
		//Checking for the NILVALUE.  We have to remove or add the "ID" depending on if it's a NILVALUE or not.
		if(msgID.matches("-"))
			statement += " -";
		else
			statement += " ID" + msgID;
		
		statement += " " + structuredData;
		statement += " " + logMessage;
		
		return statement;
	}
}
