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
 * Documentation created: 09.03.2013 - 17:26:30 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import com.arkaneud.game.Collider.RayCaster;
import com.arkaneud.game.Collider.RayCaster.CollisionResult;

// TODO: Auto-generated Javadoc
/**
 * The Class Ball.
 */
public class Ball extends Entity {

	/** The Constant BALL_RADIUS. */
	public static final int BALL_RADIUS = 20;

	/** The is active. */
	private boolean isActive = false;

	/** The is lost. */
	private boolean isLost = false;

	/** The velocity y. */
	private float velocityX, velocityY;
	
	/** The accuracy. */
	private float accuracy = 0.9f;

	/** The radius. */
	float radius = BALL_RADIUS;

	/**
	 * Instantiates a new ball.
	 */
	public Ball() {
		xPos = 200;
		yPos = 200;
		velocityX = (float) (0.5f * (Math.random() - 0.5));
		velocityY = (float) (0.5f * Math.random());
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
	 * Sets the lost.
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
	public void update(long gap) {
		if (isLost || !isActive)
			return;
		if (yPos < BALL_RADIUS) {
			setLost();
			return;
		}

		createCollision();
		Collider col = Collider.getInstance();
		RayCaster caster = RayCaster.getInstance();

		Ray ballRay = new Ray(new Point2D.Float(xPos, yPos), new Point2D.Float(
				xPos + velocityX * gap, yPos + velocityY * gap));

		if (collideWithLevel() != 0) {
		} else if (this.collision.intersects(Level.getInstance().getLocalPlayer()
				.getPaddle().collision)) {
			ArrayList<CollisionResult> results = caster.intersectRay(ballRay,
					Level.getInstance().getLocalPlayer().getPaddle().collision);
			caster.sortByDistance(results);
			caster.validateCollisions(results);
			if (!results.isEmpty()) {
				CollisionResult first = results.get(0);
				if (first.isValid()) {
					if (first.collisionNormal.x == 0) {
						velocityY *= -1.f;
					} else if (first.collisionNormal.y == 0) {
						velocityX *= -1.f;
					}
				}
			}
		} else if (true) {
			ArrayList<Brick> copy = Level.getInstance().getBricksList();
			ArrayList<Brick> temp = new ArrayList<Brick>(Level.getInstance().getBricksList());
			for (Brick b : temp) {
				if (!b.wasHit && this.collision.intersects(b.collision)) {

					ArrayList<CollisionResult> results = caster.intersectRay(
							ballRay, b.collision);
					caster.sortByDistance(results);
					caster.validateCollisions(results);
					if (!results.isEmpty()) {
						CollisionResult first = results.get(0);
						if (first.isValid()) {
							if (first.collisionNormal.x == 0) {
								velocityY *= -1.f;
							} else if (first.collisionNormal.y == 0) {
								velocityX *= -1.f;
							}
						}
					}
					b.setHit();
					copy.remove(b);
					break;
				}
			}
		}
		xPos += velocityX * gap;
		yPos += velocityY * gap;

	}

	/**
	 * Collide with level.
	 * 
	 * @return the int
	 */
	private int collideWithLevel() {
		int sth = 0;
		if (xPos - width * 0.5f - accuracy < 0
				|| xPos + width * 0.5f + accuracy > Level.LEVEL_WIDTH
						- BALL_RADIUS) {
			velocityX *= -1f;
			sth = 1;
		}
		if (yPos - accuracy > Level.LEVEL_HEIGHT) {
			velocityY *= -1f;
			sth = 1;
		}
		return sth;
	}

	/**
	 * Sets the active.
	 * 
	 * @param active
	 *            the new active
	 */
	void setActive(boolean active) {
		isActive = active;
	}

	/* (non-Javadoc)
	 * @see com.arkaneud.game.Entity#createCollision()
	 */
	@Override
	public void createCollision() {
		collision = new Rectangle2D.Float(xPos, yPos, BALL_RADIUS, BALL_RADIUS);
	}

	// @Override
	// int collide(Entity entity) {
	// if (entity instanceof Paddle) {
	// Paddle paddle = (Paddle) entity;
	// Ball ball = this;
	//
	// if (this.collision.intersects(paddle.getCollision())) {
	//
	// }
	// } else if (entity instanceof Brick) {
	// Brick brick = (Brick) entity;
	// Ball ball = this;
	//
	// if (this.collision.intersects(brick.getCollision())) {
	//
	//
	// }
	// }
	// return 0;
	// }

	/**
	 * Gets the radius.
	 * 
	 * @return the radius
	 */
	public int getRadius() {
		return (int) radius;
	}

}