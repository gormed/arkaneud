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

// TODO: Auto-generated Javadoc
/**
 * The Class Ball.
 */
public class Ball extends Entity {

	/** The Constant BALL_RADIUS. */
	public static final int BALL_RADIUS = 6;

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
		velocityX = (float) (200.0f * (Math.random() - 0.5));
		velocityY = (float) (500.0f * Math.random());
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
	public void update(float gap) {
		if (isLost || !isActive)
			return;
		if (yPos < BALL_RADIUS) {
			setLost();
			return;
		}

		Collider col = Collider.getInstance();
		RayCaster caster = RayCaster.getInstance();

		Ray ballRay = new Ray(new Point2D.Float(xPos, yPos), new Point2D.Float(
				xPos + velocityX * 0.1f, yPos + velocityY * 0.1f));

		if (collideWithLevel() != 0) {
		} else if (col.intersects(this, Level.getInstance().getLocalPlayer()
				.getPaddle())) {
			collideWithPaddle(caster, ballRay);
		} else if (true) {
			collideWithBricks(col, caster, ballRay);
		}
		xPos += velocityX * gap;
		yPos += velocityY * gap;
		createCollision();
	}

	/**
	 * Collide with bricks.
	 * 
	 * @param col
	 *            the col
	 * @param caster
	 *            the caster
	 * @param ballRay
	 *            the ball ray
	 */
	private void collideWithBricks(Collider col, RayCaster caster, Ray ballRay) {
		ArrayList<Brick> copy = Level.getInstance().getBricksList();
		ArrayList<Brick> temp = new ArrayList<Brick>(Level.getInstance()
				.getBricksList());
		for (Brick b : temp) {
			if (!b.wasHit && col.intersects(this, b)) {

				ArrayList<CollisionResult> results = caster.intersectRay(
						ballRay, b.collision);
				caster.validateCollisions(results);
				caster.sortByDistance(results);
				if (!results.isEmpty()) {
					CollisionResult first = results.get(0);
					if (first.collisionNormal.x == 0) {
						velocityY *= -1.f;
					} else if (first.collisionNormal.y == 0) {
						velocityX *= -1.f;
					}
					b.setHit();
					copy.remove(b);
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
		Paddle p = Level.getInstance().getLocalPlayer().getPaddle();
		ArrayList<CollisionResult> results = caster.intersectRay(ballRay,
				p.collision);
		caster.validateCollisions(results);
		caster.sortByDistance(results);
		if (!results.isEmpty()) {
			CollisionResult first = results.get(0);

			if (first.collisionNormal.x == 0) {
				velocityY *= -1.f;
			} else if (first.collisionNormal.y == 0) {
				velocityX += (p.velocity * p.direction) * 0.25f;
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
		float x,y;
		x = xPos + velocityX * 0.01f;
		y = yPos + velocityY * 0.01f;
		if (x - radius < 0
				|| x + radius > Level.LEVEL_WIDTH) {
			velocityX *= -1f;
			sth = 1;
		}
		if (y + radius > Level.LEVEL_HEIGHT) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Entity#createCollision()
	 */
	@Override
	public void createCollision() {
		collision = new Rectangle2D.Float(xPos, yPos, width, height);
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