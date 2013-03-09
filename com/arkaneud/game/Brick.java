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
 * File: Brick.java
 * Type: Brick
 *
 * Documentation created: 09.03.2013 - 17:26:30 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.arkaneud.data.LevelData.BrickData;

/**
 * The Class Brick.
 */
public class Brick extends Entity {

	/** The Constant BRICK_HEIGHT. */
	public static final int BRICK_HEIGHT = 20;
	
	/** The Constant BRICK_WIDTH. */
	public static final int BRICK_WIDTH = 50;
	
	/** The was hit. */
	boolean wasHit = false;
	
	/** The color. */
	Color color;

	
	/**
	 * Instantiates a new brick.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param color
	 *            the color
	 */
	public Brick(float x, float y, Color color){
		xPos = x;
		yPos = y;
		width = BRICK_WIDTH;
		height = BRICK_HEIGHT;
		this.color = color;
		createCollision();
	}
	
	/**
	 * Instantiates a new brick.
	 * 
	 * @param data
	 *            the data
	 */
	public Brick(BrickData data){
		xPos = data.x;
		yPos = data.y;
		this.color = data.color;
		createCollision();
	}
	
	/* (non-Javadoc)
	 * @see com.arkaneud.game.Entity#update()
	 */
	@Override
	public void update(long gap) {
		if (Level.getInstance().isOver() || wasHit)
			return;
//		Ball b = Level.getInstance().getLocalPlayer().getBallsList().get(0);
//		collide(b);
	}

	/* (non-Javadoc)
	 * @see com.arkaneud.game.Entity#createCollision()
	 */
	@Override
	public void createCollision() {
		collision = new Rectangle2D.Float(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
	}

	/**
	 * Sets the hit.
	 */
	void setHit() {
		wasHit = true;
	}
	
	/**
	 * Was hit.
	 * 
	 * @return true, if successful
	 */
	public boolean wasHit() {
		return wasHit;
	}
}