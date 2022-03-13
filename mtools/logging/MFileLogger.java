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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

/**
 * Simple class that allows logging to a file.
 * By default, it will log to a file named "log.txt".
 * This class follows the severity levels defined by RFC 5424.
 * However, the log statement that is written is not overall compliant with RFC 5424.
 * See the {@link LogSeverity} class for severity level definitions.
 * Uses a UTC timestamp by default.
 * @author Noah
 *
 */
public class MFileLogger {
	
	FileWriter fWriter;
	BufferedWriter bWriter;
	
	/**
	 * Configures the logging system to write to a local file named "Log.txt".
	 */
	public MFileLogger() {
		try {
			fWriter = new FileWriter("Log.txt");
			bWriter = new BufferedWriter(fWriter);
		} catch(IOException e) {
			System.err.println("Cannot write to log file!!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Allows configuration of the log file location and name.
	 * @param fileName
	 */
	public MFileLogger(String fileName) {
		try {
			fWriter = new FileWriter(fileName);
			bWriter = new BufferedWriter(fWriter);
		} catch(IOException e) {
			System.err.println("Cannot write to log file!!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Not implemented.
	 */
	public void setTimeZone() {
		
	}
	
	/**
	 * Writes a log statement.
	 * Assigns it a severity of "Informational" (6) by default.
	 * Assigns it a process ID of 0 by default.
	 * @param logMessage
	 */
	public void log(String logMessage) {
		log(logMessage, LogSeverity.INFORMATION, "0");
	}
	
	/**
	 * Writes a log statement.
	 * Allows for setting the severity level.
	 * Assigns it a process ID of 0 by default.
	 * @param logMessage
	 * @param severity
	 */
	public void log(String logMessage, int severity) {
		log(logMessage, severity, "0");
	}

	/**
	 * Writes a log statement.
	 * Allows for setting the severity level.
	 * Allows for setting a process ID.
	 * @param logMessage
	 * @param severity
	 */
	public void log(String logMessage, int severity, String processID) {
		try {
			//Throws and IllegalArgumentException if the severity is illegal.
			LogSeverity.checkForValidSeverity(severity);
			bWriter.write(createLogStatement(logMessage, severity, processID));
			bWriter.newLine();
			bWriter.flush();
		} catch(IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (IOException e) {
			System.err.println("Cannot write to log file!!!");
			e.printStackTrace();
		}
	}

	/**
	 * Closes the BufferedWriter and FileWriter associated with this object.
	 * This should be called when the program closes, but it is not mandatory.
	 */
	public void close() {
		try {
			bWriter.flush();
			bWriter.close();
			fWriter.close();
		} catch(IOException e) {
			System.err.println("Cannot close logging system!!!");
			e.printStackTrace();
		}
	}
	
	private String createLogStatement(String logMessage, int severity, String processID) {
		String statement = java.time.Clock.systemUTC().instant().truncatedTo(ChronoUnit.MICROS).toString();
		statement += ", Severity ";
		statement += severity;
		statement += ", PID ";
		statement += processID;
		statement += ", ";
		statement += logMessage;
		
		return statement;
	}
}
