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
 * File: Ray.java
 * Type: Ray
 *
 * Documentation created: 10.03.2013 - 14:02:43 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Point2D;

/**
 * The Class Ray represents a mathmatical 2D ray for the purpose of
 * ray-rectangle intersection checks.
 */
public class Ray {

	/** The direction vector of the ray. */
	Point2D.Float direction;

	/** The start point of the ray. */
	Point2D.Float startPoint;

	/** The end point of the ray. */
	Point2D.Float endPoint;

	/** The slope/gradient of the ray. */
	float m;

	/** The offset in y-direction of the ray. */
	float n;

	/** The vertical flag if the ray is parallel to the y-axis. */
	boolean vertical = false;

	/**
	 * Instantiates a new ray.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public Ray(Point2D.Float start, Point2D.Float end) {
		// calc the dir of the ray
		this.direction = new Point2D.Float(end.x - start.x, end.y - start.y);
		// set start and end point
		this.startPoint = start;
		this.endPoint = end;
		// if start and end isnt equal
		if (end.x != start.x) {
			// calculate the gradient- and offset-value
			m = (end.y - start.y) / (end.x - start.x);
			n = start.y - m * start.x;
		} else {
			// else the ray is vertical, so we flip x- with y-axis for this
			m = 0;
			n = endPoint.x;
			vertical = true;
		}
	}

	/**
	 * Gets the value, means we calculate f(x) = m*x + n if the ray is not
	 * vertical, otherwise we calculate g(y) = x.
	 * 
	 * @param x
	 *            the x
	 * @return the value
	 */
	public float getValue(float x) {
		if (!vertical) {
			return m * x + n;
		} else {
			return endPoint.x;
		}
	}

	/**
	 * Normalizes the direction vector.
	 */
	public void normalize() {
		float l = (float) direction.distance(0, 0);
		direction.x /= l;
		direction.y /= l;
	}

	/**
	 * Gets the point for a t-value [-oo; +oo]. 
	 * We calculate p(t) = start + t * direction.
	 * 
	 * @param t
	 *            the t
	 * @return the point
	 */
	public Point2D.Float getPoint(float t) {
		return new Point2D.Float(startPoint.x + t * direction.x, startPoint.y
				+ t * direction.x);
	}

	/**
	 * Gets the parameter 't' of a point on the ray.
	 * 
	 * @param point
	 *            the point
	 * @return the parameter
	 */
	public float getParameter(Point2D.Float point) {
		float t1, t2;
		float epsilon = 0.1f;
		t1 = (point.x - startPoint.x) / direction.x;
		t2 = (point.y - startPoint.y) / direction.y;
		if (direction.x == 0 && direction.y != 0)
			return t2;
		if (direction.y == 0 && direction.x != 0)
			return t1;
		if (t1 + epsilon > t2 && t1 - epsilon < t2)
			return t1;
		return Float.NaN;

	}
}