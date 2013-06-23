/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * arkaneud Project (c) 2013 by Hans Ferchland
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * 
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * GNU Public License
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License 3 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Email me for any questions: hans.ferchland[at]gmx.de
 *
 * Project: arkaneud
 * File: GUIElement.java
 * Type: GUIElement
 *
 * Documentation created: 18.03.2013 - 14:10:46 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

/**
 * The Class GUIElement.
 */
public abstract class GUIElement implements Observer {

	/** The unique element id. */
	private static long uniqueElementID = 0;

	/**
	 * Gets the id internally.
	 * 
	 * @return the id
	 */
	private static long getID() {
		return uniqueElementID++;
	}

	/** The name of the element. */
	private String name = "element";

	/** The x and y coordinate on the java awt surface. */
	int x, y;

	/** The width. */
	int width;

	/** The height. */
	int height;
	
	/** The visiblity flag. */
	boolean isVisible;

	/** The id. */
	private long id = getID();

	/**
	 * Instantiates a new gUI element.
	 * 
	 * @param name
	 *            the name
	 */
	public GUIElement(String name) {
		this.name = name;
	}

	/**
	 * Instantiates a new gUI element.
	 */
	public GUIElement() {
		name = name + "_" + id;
	}

	/**
	 * Gets the elements id.
	 * 
	 * @return the element id
	 */
	public Long getElementID() {
		return id;
	}

	/**
	 * Gets the elements name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the x coordinate.
	 * 
	 * @return the elements x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate.
	 * 
	 * @return the elements y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Checks if is visible.
	 * 
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Sets the visiblity flag.
	 * 
	 * @param isVisible
	 *            the new visible
	 */
	void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Sets the position on screen.
	 * 
	 * @param x
	 *            the elements y coordinate
	 * @param y
	 *            the elements y coordinate
	 */
	void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public abstract void update(Observable o, Object arg);

	/**
	 * Draws the element on screen.
	 * 
	 * @param g
	 *            the Graphics reference to paint
	 */
	public abstract void draw(Graphics g);

}