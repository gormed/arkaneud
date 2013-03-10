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
 * File: Entity.java
 * Type: Entity
 *
 * Documentation created: 10.03.2013 - 14:02:44 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Rectangle2D;
import java.util.Observable;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 */
public abstract class Entity extends Observable implements Updateable {

	/** The x pos. */
	float xPos = 0;
	
	/** The y pos. */
	float yPos = 0;
	
	/** The width. */
	float width = 0;
	
	/** The height. */
	float height = 0;
	
	/** The collision. */
	Rectangle2D.Float collision;

	/**
	 * Instantiates a new entity.
	 */
	public Entity() {
		super();
	}

	/**
	 * Creates the collision.
	 */
	public abstract void createCollision();

	/**
	 * Gets the collision.
	 * 
	 * @return the collision
	 */
	public Rectangle2D getCollision() {
		return collision;
	}

	/**
	 * Sets the position.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setPosition(float x, float y) {
		xPos = x;
		yPos = y;
	}

	/**
	 * Update obsersers.
	 * 
	 * @param gap
	 *            the gap
	 */
	void updateObsersers(float gap) {
		update(gap);
		setChanged();
		notifyObservers(gap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#update(long)
	 */
	@Override
	public abstract void update(float gap);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#updateObservers(long)
	 */
	@Override
	public void updateObservers(float gap) {
		update(gap);
		setChanged();
		notifyObservers(gap);
	}

	/**
	 * Gets the x pos.
	 * 
	 * @return the x pos
	 */
	public float getXPos() {
		return xPos;
	}

	/**
	 * Gets the y pos.
	 * 
	 * @return the y pos
	 */
	public float getYPos() {
		return Level.LEVEL_HEIGHT - yPos;
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}
	
	
}