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
import java.util.ArrayList;
import com.arkaneud.game.Collider.RayCaster;
import com.arkaneud.game.Collider.RayCaster.CollisionResult;

/**
 * The Class Ball defines the simulated ball.
 */
public class Ball extends Collidable {

	/** The Constant BALL_RADIUS. */
	public static final int BALL_RADIUS = 6;

	/** The flag that defines if the ball is active. */
	private boolean isActive = false;

	/** The flag that defines if the ball is lost. */
	private boolean isLost = false;

	/** The velocity in x/y direction. */
	private float velocityX, velocityY;

	/** The radius. */
	float radius = BALL_RADIUS;

	/**
	 * Instantiates a new ball.
	 */
	public Ball() {
		position = new Point2D.Float(200, 200);
		// starting velocity by random x and defined y
		velocityX = (float) (200.0f * (Math.random() - 0.5));
		velocityY = (float) (320.0f);
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
		// get instaces of collider and raycaster
		Collider col = Collider.getInstance();
		RayCaster caster = RayCaster.getInstance();
		// create the balls movement ray from its velocity and current position
		Ray ballRay = new Ray(position, new Point2D.Float(
				position.x + velocityX * 0.1f * radius, 
				position.y + velocityY * 0.1f * radius));
		// collide with the level boundaries
		if (collideWithLevel() != 0) {
			// check if the ball itersects the paddle
		} else if (col.intersects(this, Level.getInstance().getPlayerController()
				.getPaddle())) {
			// calculate collision parameters with the paddle
			collideWithPaddle(caster, ballRay);
		} else if (true) {
			// checks for each brick if they collide with the ball
			collideWithBricks(col, caster, ballRay);
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
	 * @param col
	 *            the col
	 * @param caster
	 *            the caster
	 * @param ballRay
	 *            the ball ray
	 */
	private void collideWithBricks(Collider col, RayCaster caster, Ray ballRay) {
		ArrayList<Brick> pointer = Level.getInstance().getBricksList();
		ArrayList<Brick> temp = new ArrayList<Brick>(Level.getInstance()
				.getBricksList());
		// check collision with each brick
		for (Brick b : temp) {
			// preselect the bricks
			if (!b.wasHit && col.intersects(this, b)) {
				// intersect the balls movement ray with brick collision
				ArrayList<CollisionResult> results = caster.intersectRay(
						ballRay, b.collision);
				// if there are any colisions
				if (!results.isEmpty()) {
					// validate results
					caster.validateCollisions(results);
					// sort
					caster.sortByDistance(results);
					// get the nearest/first collision
					CollisionResult first = results.get(0);
					// change direction
					if (first.collisionNormal.x == 0) {
						velocityY *= -1.f;
					} else if (first.collisionNormal.y == 0) {
						velocityX *= -1.f;
					}
					// mark hit, so the player gets his/her points
					b.setHit();
					// remove finally from the levels list
					pointer.remove(b);
					break;
				}
			}
		}
	}

	/**
	 * Collide with paddle.
	 * 
	 * @param caster
	 *            the caster
	 * @param ballRay
	 *            the ball ray
	 */
	private void collideWithPaddle(RayCaster caster, Ray ballRay) {
		Paddle p = Level.getInstance().getPlayerController().getPaddle();
		ArrayList<CollisionResult> results = caster.intersectRay(ballRay,
				p.collision);
		caster.validateCollisions(results);
		caster.sortByDistance(results);
		if (!results.isEmpty()) {
			CollisionResult first = results.get(0);

			if (first.collisionNormal.x == 0) {
				velocityY *= -1.f;
			} else if (first.collisionNormal.y == 0) {
				velocityX += (p.velocity * p.direction) * 0.1f;
				velocityX *= -1.f;
			}
		}
	}

	/**
	 * Collide with level.
	 * 
	 * @return the int
	 */
	private int collideWithLevel() {
		int sth = 0;
		float x, y;
		// we need to check if the ball will collide in the future so we travel
		// some amount of time in the future to check taht
		x = position.x + velocityX * 0.01f;
		y = position.y + velocityY * 0.01f;
		// check for left and right
		if (x - radius < 0 || x + radius > Level.LEVEL_WIDTH) {
			velocityX *= -1f;
			sth = 1;
		}
		// check for top
		else if (y + radius > Level.LEVEL_HEIGHT) {
			velocityY *= -1f;
			sth = 1;
		}
		return sth;
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
		collision = new Rectangle2D.Float(position.x, position.y, width, height);
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