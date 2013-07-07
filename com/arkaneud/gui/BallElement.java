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
 * File: BallElement.java
 * Type: BallElement
 *
 * Documentation created: 07.07.2013 - 21:30:13 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

import com.arkaneud.game.*;

/**
 * The Class BallElement represents a ball in the level for displaying.
 */
public class BallElement extends GUIElement {

	/** The radius of the drawn ball. */
	int radius = 0;

	/**
	 * Instantiates a new ball element.
	 */
	public BallElement() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.gui.GUIElement#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		if (isVisible()) {
			g.setColor(Color.ORANGE);
			g.fillOval(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawOval(x, y, width, height);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.gui.GUIElement#update(java.util.Observable,
	 * java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// float gap = (Float) arg;
		if (o instanceof Ball) {
			Ball b = (Ball) o;
			x = (int) b.getXPos();
			y = (int) b.getYPos();
			isVisible = b.isActive();
			radius = b.getRadius();
			width = radius * 2;
			height = radius * 2;
		}
	}

}