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

import java.util.ArrayList;

/**
 * This class is to be used to display sequential text/message 
 * oriented data through the console.  Line 0 is towards the
 * top of the window, and line 1 is below that, and so on...
 * This can be reversed. There is an option for a banner/message
 * that displays at the top if you need one.  By default, the banner
 * is disabled and will not show.
 * If you need a menu system, use {@link MMenu}
 * @author Noah
 *
 */
public class MDisplay {
	private ArrayList<String> lines;
	private String banner;
	private boolean bannerFlag;
	private int maxLines;
	private boolean isDisplayTopToBottom;
	
	/**
	 * Default constructor.  Sets the maximum lines of console display
	 * to 15 and inserts items to the top of the display.  You can
	 * reverse the direction by calling {@link setDisplayReverse()}
	 */
	public MDisplay() {
		lines = new ArrayList<String>();
		banner = null;
		bannerFlag = false;
		maxLines = 15;
		isDisplayTopToBottom = true;
	}
	
	/**
	 * Constructor that allows you to set the banner and the maximum
	 * amount of lines.  The maximum amount of lines does not include
	 * the banner.  If you don't want a banner and just want to 
	 * set the maximum amount of lines, pass null for String s.  If
	 * you just want to set the banner, and not the max lines, pass
	 * an integer that is less than 1 for int max.
	 * @param s
	 */
	public MDisplay(String s, int max) {
		lines = new ArrayList<String>();
		banner = s;
		isDisplayTopToBottom = true;
		
		if(banner != null) {
			bannerFlag = true;
		} else {
			bannerFlag = false;
		}
		
		if(max > 0) {
			maxLines = max;
		} else {
			maxLines = 15;
		}
	}
	
	/**
	 * Sets the message to be displayed above the menu items.
	 * @param s
	 */
	public void setBanner(String s) {
		banner = s;
		bannerFlag = true;
	}
	
	/**
	 * Removes any banner currently set.
	 */
	public void clearBanner() {
		banner = null;
		bannerFlag = false;
	}
	
	/**
	 * Determines if the banner should be displayed, regardless if it is set or not.
	 * Can be useful if you want to save the banner for later, but don't currently
	 * want to display it.  This flag is automatically set to true when the banner
	 * is set.  It is automatically set to false when it is cleared.
	 * @param flag
	 */
	public void setBannerDisplayFlag(boolean flag) {
		bannerFlag = flag;
	}
	
	/**
	 * Sets the maximum amount of lines.  Default is 15 lines.
	 * Anything less than 1 results in the default of 15.  This
	 * does not include the banner.
	 * @param s
	 */
	public void setMaxLines(int max) {
		if(max > 0) {
			maxLines = max;
		} else {
			maxLines = 15;
		}
		
		//cutting off the lines that extend past the maximum amount of lines value
		if(lines.size() > maxLines) {
			while(lines.size() > maxLines) {
				lines.remove(maxLines);
			}
		}
	}
	
	/**
	 * Returns the number of maximum lines to be displayed.
	 * This does not include the banner.
	 * @return
	 */
	public int getMaxLines() {
		return maxLines;
	}
	
	/**
	 * returns the total number of lines as it is currently configured.
	 * This number is going to be less than or equal to the maximum
	 * lines that have been configured.
	 * @return
	 */
	public int getSize() {
		return lines.size();
	}
	
	/**
	 * Adds a line of text that is indexed numerically, and increases
	 * sequentially, starting from 0.  If the amount of configured lines
	 * is greater than or equal to the maximum amount of lines, then this
	 * command will be ignored.
	 * @param line
	 */
	public void addLine(String line) {
		if(lines.size() < maxLines)
			lines.add(line);
	}
	
	/**
	 * Inserts a new line into any line placement.  Everything after is
	 * shifted numerically to the right.  If the number of lines added
	 * is higher than the maximum amount of lines, then the lines above
	 * the limit will get deleted.
	 * @param line
	 * @param index
	 */
	public void insertLine(String line, int index) {
		lines.add(index, line);
		
		//cutting off the lines that extend past the maximum amount of lines value
		if(lines.size() > maxLines) {
			while(lines.size() > maxLines) {
				lines.remove(maxLines);
			}
		}
	}
	
	/**
	 * Removes the referenced line.  All sequentially higher lines
	 * are shifted to the left to fill the cleared line.
	 * @param index
	 */
	public void removeLine(int index) {
		lines.remove(index);
	}
	
	/**
	 * Clears all lines.
	 */
	public void clear() {
		lines.clear();
	}
	
	/**
	 * This is the default display order.  Lower numbered lines
	 * are displayed towards the top.
	 *  
	 */
	public void setDisplayForward() {
		isDisplayTopToBottom = true;
	}
	
	/**
	 * Returns whether the display orientation is set to forward or reverse.
	 * @return
	 */
	public boolean isDisplayForward() {
		return isDisplayTopToBottom;
	}
	
	/**
	 * Reverses the display order.  Lower numbered lines are
	 * displayed towards the bottom.
	 */
	public void setDisplayReverse() {
		isDisplayTopToBottom = false;
	}
	
	/**
	 * Returns the array list that contains all of the lines.	
	 * @return
	 */
	public ArrayList<String> getArrayList() {
		return lines;
	}
	
	/**
	 * Updates various variables and the displays what is currently configured
	 * on the console.
	 */
	public void display() {
		
		//Clears the console
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
		//end of console clearing
		
		if(banner != null && bannerFlag == true) {
			System.out.println(banner);
			System.out.println();
		}
		
		//Sets the print orientation on the console
		if(isDisplayTopToBottom) {
			for(int i = 0; i<lines.size(); i++) {
				System.out.println(lines.get(i));
			}
		} else {
			for(int i = lines.size() - 1; i >= 0; i--) {
				System.out.println(lines.get(i));
			}
		}
	}
}
