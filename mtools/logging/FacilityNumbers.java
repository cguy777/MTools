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

/**
 * The standard facility numbers as defined in RFC 5424.
 * @author Noah
 *
 */
public class FacilityNumbers {
	public static final int KERNEL = 0;
	public static final int USER_LEVEL = 1;
	public static final int MAIL_SYSTEM = 2;
	public static final int SYSTEM_DAEMONS = 3;
	public static final int SECURITY_AUTHORIZATION = 4;
	public static final int INTERNAL_SYSLOGD = 5;
	public static final int LINE_PRINTER = 6;
	public static final int NETWORK_NEWS = 7;
	public static final int UUCP = 8;
	public static final int CLOCK_DAEMON = 9;
	public static final int SECURITY_AUTHORIZATION_2 = 10;
	public static final int FTP_DAEMON = 11;
	public static final int NTP_SUBSYSTEM = 12;
	public static final int LOG_AUDIT = 13;
	public static final int LOG_ALERT = 14;
	public static final int CLOCK_DAEMON_2 = 15;
	public static final int LOCAL_USE_0 = 16;
	public static final int LOCAL_USE_1 = 17;
	public static final int LOCAL_USE_2 = 18;
	public static final int LOCAL_USE_3 = 19;
	public static final int LOCAL_USE_4 = 20;
	public static final int LOCAL_USE_5 = 21;
	public static final int LOCAL_USE_6 = 22;
	public static final int LOCAL_USE_7 = 23;
	
	/**
	 * Checks if the passed value is a valid facility number.
	 * Does nothing if the passed value is valid.
	 * Throws an {@link IllegalArgumentException} if the passed value is invalid.
	 * @param facNumber
	 */
	static void checkForValidFacility(int facNumber) {
		if(facNumber < 0 || facNumber > 23)
			throw new IllegalArgumentException("Facility numbers must be between 0 and 23");
	}
}
