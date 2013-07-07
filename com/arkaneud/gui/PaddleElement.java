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
 * File: PaddleElement.java
 * Type: PaddleElement
 *
 * Documentation created: 07.07.2013 - 21:30:14 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

import com.arkaneud.game.Paddle;

/**
 * The Class PaddleElement represents the players paddle.
 */
public class PaddleElement extends GUIElement {

	/**
	 * Instantiates a new paddle element.
	 */
	public PaddleElement() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.gui.GUIElement#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fill3DRect(x, y, width, height, true);

		// g.drawRect(x - width / 2, y - height / 2, width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.gui.GUIElement#update(java.util.Observable,
	 * java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Paddle) {
			Paddle p = (Paddle) o;
			x = (int) p.getXPos();
			y = (int) p.getYPos();
			width = (int) p.getWidth();
			height = (int) p.getHeight();
		}
	}
}