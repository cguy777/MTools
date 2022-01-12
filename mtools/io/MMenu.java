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
 * A simple console window system that is designed to be
 * used as a menu system.  It displays a banner/message
 * and displays numbered options below that.  By default,
 * the banner is disabled and will not show.  If you need
 * to display something that isn't menu oriented, use {@link MDisplay}
 * @author Noah
 *
 */
public class MMenu {
	private ArrayList<String> menuItems;
	private String banner;
	private boolean bannerFlag;
	
	/**
	 * Default constructor.
	 */
	public MMenu() {
		menuItems = new ArrayList<String>();
		banner = null;
		bannerFlag = false;
	}
	
	/**
	 * Constructor that sets the banner.
	 * @param b
	 */
	public MMenu(String b) {
		menuItems = new ArrayList<String>();
		banner = b;
		bannerFlag = true;
	}
	
	/**
	 * Returns the amount of items in the menu
	 * @return
	 */
	public int getMenuSize() {
		return menuItems.size();
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
	 * Adds an item that is indexed numerically, and increases
	 * sequentially, starting from 0.
	 * @param s
	 */
	public void addMenuItem(String s) {
		menuItems.add(s);
	}
	
	/**
	 * Adds a menu item to any point in the menu.  Everything after is
	 * shifted numerically to the right.
	 * @param s
	 * @param index
	 */
	public void insertMenuItem(String s, int index) {
		menuItems.add(index, s);
	}
	
	/**
	 * Deletes the menu item referenced the index.
	 * @param index
	 */
	public void deleteMenuItem(int index) {
		menuItems.remove(index);
	}
	
	/**
	 * Clears all items from the menu.
	 */
	public void clearMenu() {
		menuItems.clear();
	}
	
	/**
	 * Returns the array list that contains all of the menu items.	
	 * @return
	 */
	public ArrayList<String> getArrayList() {
		return menuItems;
	}
	
	/**
	 * Clears the console and then displays the window at its current configuration.
	 */
	public void display() {
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
		
		if(banner != null && bannerFlag == true) {
			System.out.println(banner);
			System.out.println();
		}
		
		for(int i = 0; i<menuItems.size(); i++) {
			System.out.println(i + ". " + menuItems.get(i));
		}
	}
	
}
