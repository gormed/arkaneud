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
 * File: Ball.java
 * Type: Ball
 *
 * Documentation created: 10.03.2013 - 14:02:44 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;

/**
 * The Class Ball defines the simulated ball.
 */
public class Ball extends DynamicCollidable {

	/** The Constant BALL_RADIUS. */
	public static final int BALL_RADIUS = 6;

	/** The flag that defines if the ball is active. */
	private boolean isActive = false;

	/** The flag that defines if the ball is lost. */
	private boolean isLost = false;

	/** The radius. */
	float radius = BALL_RADIUS;

	/**
	 * Instantiates a new ball.
	 */
	public Ball() {
		position = new Point2D.Float(200, 200);
		// starting velocity by random x and defined y
		velocityX = (float) (300.0f * (Math.random() - 0.5));
		velocityY = (float) (220.0f);
		width = 2 * radius;
		height = 2 * radius;
		createCollision();
	}

	/**
	 * Checks if is active.
	 * 
	 * @return true, if is active
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Checks if is lost.
	 * 
	 * @return true, if is lost
	 */
	public boolean isLost() {
		return isLost;
	}

	/**
	 * Sets the lost flag internally.
	 */
	private void setLost() {
		isActive = false;
		isLost = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Entity#update()
	 */
	@Override
	public void update(float gap) {
		// check if the ball itself is lost or not active and cancel updating
		if (isLost || !isActive)
			return;
		// if the ball is below zero (out of game) it is lost
		if (position.y < BALL_RADIUS) {
			setLost();
			return;
		}
		// create collision rect that is 10 frames in the future to get
		// collisions before they really happen
		Rectangle2D.Float pre = new Rectangle2D.Float(collision.x + velocityX
				* gap * 10, collision.y + velocityY * gap * 10,
				collision.width, collision.height);

		if (collideWithLevel(gap)) {
			// collide with the level boundaries
		} else if (collideWithPaddle(pre)) {
			// check if the ball itersects the paddle
		} else if (collideWithBricks(pre)) {
			// checks for each brick if they collide with the ball
		}
		// apply the new velocity (set in one of the methods before)
		position.x += velocityX * gap;
		position.y += velocityY * gap;
		// the position changed, so we need to recalculate the collision rect
		createCollision();
	}

	/**
	 * Collide with all bricks.
	 * 
	 * @param pre
	 * 
	 * @param col
	 *            the col
	 * @param caster
	 *            the caster
	 * @param ballRay
	 *            the ball ray
	 */
	private boolean collideWithBricks(Rectangle2D.Float pre) {
		ArrayList<Brick> temp = new ArrayList<Brick>(Level.getInstance()
				.getBricksList());
		// check collision with each brick
		for (Brick b : temp) {
			// preselect the bricks
			if (!b.wasHit && pre.intersects(b.getCollision())) {
				if (collide(b)) {
					b.setHit();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Collide with paddle.
	 * 
	 * @param pre
	 * 
	 * @param caster
	 *            the caster
	 * @param ballRay
	 *            the ball ray
	 */
	private boolean collideWithPaddle(Float pre) {
		Paddle p = Level.getInstance().getPlayerController().getPaddle();
		if (pre.intersects(p.getCollision())) {
			if (collide(p))
				return true;
		}
		return false;
	}

	/**
	 * Collide with level.
	 * 
	 * @return the int
	 */
	private boolean collideWithLevel(float gap) {
		float x, y;
		// we need to check if the ball will collide in the future so we travel
		// some amount of time in the future to check taht
		x = collision.x + velocityX * gap * 10;
		y = collision.y + velocityY * gap * 10;
		// check for left and right
		if (x <= 0 || x + width >= Level.LEVEL_WIDTH) {
			velocityX *= -1f;
			return true;
		}
		// check for top
		else if (y - radius >= Level.LEVEL_HEIGHT) {
			velocityY *= -1f;
			return true;
		}
		return false;
	}

	/**
	 * Sets the active flag for the ball (each time the last ball was lost).
	 * 
	 * @param active
	 *            the new active
	 */
	void setActive(boolean active) {
		isActive = active;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Entity#createCollision()
	 */
	@Override
	public void createCollision() {
		collision = new Rectangle2D.Float(position.x - radius, position.y
				- radius, width, height);
	}

	/**
	 * Gets the radius.
	 * 
	 * @return the radius
	 */
	public int getRadius() {
		return (int) radius;
	}
}