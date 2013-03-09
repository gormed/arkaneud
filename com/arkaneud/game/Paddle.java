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
 * Documentation created: 09.03.2013 - 17:26:30 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Rectangle2D;

// TODO: Auto-generated Javadoc
/**
 * The Class Paddle.
 */
public class Paddle extends Entity {
	
	/** The Constant PADDLE_HEIGHT. */
	public static final int PADDLE_HEIGHT = 7;
	
	/** The Constant PADDLE_WIDTH. */
	public static final int PADDLE_WIDTH = 50;

	/** The moved. */
	boolean moved = false;

	/** The direction. */
	int direction = 0;

	/** The velocity. */
	float velocity = 0.001f;
	
	/**
	 * Instantiates a new paddle.
	 */
	public Paddle() {
		setPosition(200, 100);
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
	public void update(long gap) {
		if (Level.getInstance().isOver())
			return;

		if (moved) {
			xPos += (velocity * direction);
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
	}

	/* (non-Javadoc)
	 * @see com.arkaneud.game.Entity#createCollision()
	 */
	@Override
	public void createCollision() {

		collision = new Rectangle2D.Float(xPos, yPos, PADDLE_WIDTH,
				PADDLE_HEIGHT);
	}


}