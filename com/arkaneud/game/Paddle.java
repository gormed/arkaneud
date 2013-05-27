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
 * File: Paddle.java
 * Type: Paddle
 *
 * Documentation created: 10.03.2013 - 14:02:44 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

// TODO: Auto-generated Javadoc
/**
 * The Class Paddle.
 */
public class Paddle extends Collidable {
	
	/** The Constant PADDLE_HEIGHT. */
	public static final int PADDLE_HEIGHT = 10;
	
	/** The Constant PADDLE_WIDTH. */
	public static final int PADDLE_WIDTH = 65;

	/** The moved. */
	boolean moved = false;

	/** The direction. */
	int direction = 0;

	/** The velocity. */
	float velocity = 500f;
	
	/**
	 * Instantiates a new paddle.
	 */
	public Paddle() {
		position = new Point2D.Float(200, 100);
		width = PADDLE_WIDTH;
		height = PADDLE_HEIGHT;
		createCollision();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Entity#update()
	 */
	@Override
	public void update(float gap) {
		if (Level.getInstance().isOver())
			return;
		// chck if the player moved the paddle
		if (moved) {
			// add delta x to position if moved
			position.x += (velocity * direction) * gap;
			// check if paddle was moved out of level bounds
			if (position.x < 0)
				position.x = 0;
			else if (position.x > Level.LEVEL_WIDTH-width)
				position.x = Level.LEVEL_WIDTH-width;
			// refresh the collision rect
			createCollision();
		} else
			return;
		
//		Ball b = Level.getInstance().getLocalPlayer().getBallsList().get(0);
//		b.createCollision();
	}

	/**
	 * Move.
	 * 
	 * @param left
	 *            the left
	 * @param right
	 *            the right
	 */
	public void move(boolean left, boolean right) {
		if (!(left && right) && (left || right)) {
			if (left)
				direction = -1;
			if (right)
				direction = 1;
			moved = true;
			return;
		}
		moved = false;
		direction = 0;
	}

	/* (non-Javadoc)
	 * @see com.arkaneud.game.Entity#createCollision()
	 */
	@Override
	public void createCollision() {

		collision = new Rectangle2D.Float(position.x, position.y, width,
				height);
	}


}