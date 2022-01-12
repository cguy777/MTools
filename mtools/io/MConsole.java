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

package mtools.io;

import java.util.Scanner;

/**
 * This class is simply for abstracting {@link Scanner} and
 * @author Noah
 *
 */

public class MConsole {
	
	private Scanner consoleInput;
	
	/**
	 * Simply initializes the Scanner.
	 */
	public MConsole() {
		consoleInput = new Scanner(System.in);
	}
	
	/**
	 * Returns the next line of console input in the form of a string
	 * @return String
	 */
	public String getInputString() {
		return consoleInput.nextLine();
	}
	
	/**
	 * Returns the next line of console input in the form of an int.
	 * @return int
	 * @throws IndexOutOfBoundsException
	 */
	public int getInputInt() throws IndexOutOfBoundsException {
		return Integer.valueOf(consoleInput.nextLine());
	}
	
	/**
	 * Returns the next line of console input in the form of a float.
	 * @return float
	 * @throws IndexOutOfBoundsException
	 */
	public float getInputFloat() throws IndexOutOfBoundsException {
		return Float.valueOf(consoleInput.nextLine());
	}
	
	/**
	 * Calls reset on the Scanner object.
	 */
	public void reset() {
		consoleInput.reset();
	}
	
	/**
	 * Returns the Scanner object.
	 * @return {@link Scanner}
	 */
	public Scanner getScanner() {
		return consoleInput;
	}
	
	/**
	 * clears the console on Windows and Linux.  Should work on Mac, but untested.
	 */
	public void clear() {
		try {
			String os = System.getProperty("os.name");
			
			if(os.contains("Windows")) {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
				Process startProcess = pb.inheritIO().start();
				startProcess.waitFor();
			} else {
				ProcessBuilder pb = new ProcessBuilder("clear");
				Process startProcess = pb.inheritIO().start();
				startProcess.waitFor();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * Closes the Scanner object.
	 */
	public void close() {
		consoleInput.close();
	}
}
