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
 * File: BrickElement.java
 * Type: BrickElement
 *
 * Documentation created: 10.03.2013 - 14:02:44 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.Graphics;
import java.util.Observable;

import com.arkaneud.game.*;

/**
 * The Class BrickElement.
 */
public class BrickElement extends LevelElement {

	/**
	 * Instantiates a new brick element.
	 */
	public BrickElement() {
		setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.gui.GUIElement#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		if (isVisible)
			g.drawRect(x - width/2, y - height/2, width,
					height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.gui.GUIElement#update(java.util.Observable,
	 * java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Brick) {
			Brick b = (Brick) o;
			x = (int) b.getXPos();
			y = (int) b.getYPos();
			width = (int) b.getWidth();
			height = (int) b.getHeight();
			setVisible(!b.wasHit());
		}
	}

}