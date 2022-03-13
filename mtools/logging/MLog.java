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

import java.net.InetAddress;
import java.net.SocketException;

/**
 * Wrapper for a static {@link MFileLogger} and a static {@link MSysLogger} so they can be called
 * directly from anywhere in the application without having to pass the object down to other objects.
 * @author Noah
 *
 */
public class MLog {
	public static MFileLogger fileLog;
	public static MSysLogger sysLog;
	
	/**
	 * Initializes the {@link MFileLogger}.
	 * One of the init commands for that object must be called before using the MFileLogger object contained in this class.
	 * Logs to a local file named "Log.txt".
	 */
	public static void initFileLogger() {
		fileLog = new MFileLogger();
	}
	
	/**
	 * Initializes the {@link MFileLogger}.
	 * One of the init commands for that object must be called before using the MFileLogger object contained in this class.
	 * Logs to a local file named whatever the logFileName is assigned.
	 * 
	 * @param logFileName The filename you wish to assign to the log file.
	 */
	public static void initFileLogger(String logFileName) {
		fileLog = new MFileLogger(logFileName);
	}
	
	/**
	 * Initializes the {@link MSysLogger}.
	 * Must be called before using the MSysLogger object contained in this class.
	 * 
	 * @param applicationName The application name for the syslog messages.  See RFC 5424, section 6.2.5 for more info.
	 * @param facilityNumber The facilityNumber for the syslog messages.  See RFC 5424, section 6.2.1 for more info.
	 * Also see the {@link FacilityNumbers} class for defined facility numbers.
	 * @param serverAddress The IP address of the syslog server you wish to write to.
	 */
	public static void initSysLogger(String applicationName, int facilityNumber, InetAddress serverAddress) {
		try {
			sysLog = new MSysLogger(applicationName, facilityNumber, serverAddress);
		} catch (SocketException e) {
			System.err.println("Cannot initialize syslogger!!!");
			e.printStackTrace();
		}
	}
}
