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
 * Documentation created: 10.03.2013 - 14:02:44 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

// TODO: Auto-generated Javadoc
/**
 * The Class GUIElement.
 */
public abstract class GUIElement implements Observer {

	/** The unique element id. */
	private static long uniqueElementID = 0;
	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	private static long getID() { return uniqueElementID++; }
	
	/** The name. */
	private String name = "element";
	
	/** The y. */
	int x,y;
	
	/** The is visible. */
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
	 * Gets the element id.
	 * 
	 * @return the element id
	 */
	public Long getElementID() {
		return id;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
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
	 * Sets the visible.
	 * 
	 * @param isVisible
	 *            the new visible
	 */
	void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	/**
	 * Sets the position.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public abstract void update(Observable o, Object arg);
	
	/**
	 * Draw.
	 * 
	 * @param g
	 *            the g
	 */
	public abstract void draw(Graphics g);

}