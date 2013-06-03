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
 * Documentation created: 27.05.2013 - 01:24:29 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.arkaneud.data.LevelData.BrickData;

/**
 * The Class Brick represents a brick in simulation.
 */
public class Brick extends Collidable {

	/** The Constant BRICK_HEIGHT. */
	public static final int BRICK_HEIGHT = 20;

	/** The Constant BRICK_WIDTH. */
	public static final int BRICK_WIDTH = 50;

	/** The was hit. */
	boolean wasHit = false;

	/** The color. */
	private Color color;

	int points = 1;

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
	public Brick(float x, float y, Color color) {
		position = new Point2D.Float(x, y);
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
	public Brick(BrickData data) {
		position = new Point2D.Float(data.x, data.y);
		width = BRICK_WIDTH;
		height = BRICK_HEIGHT;
		this.color = data.color;
		createCollision();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Entity#update()
	 */
	@Override
	public void update(float gap) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Entity#createCollision()
	 */
	@Override
	public void createCollision() {
		collision = new Rectangle2D.Float(position.x, position.y, width, height);
	}

	/**
	 * Sets the hit.
	 */
	void setHit() {
		if (wasHit)
			return;
		Level.getInstance().removeBrick();
		Level.getInstance().getPlayerController().addPoints(points);
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

	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

}