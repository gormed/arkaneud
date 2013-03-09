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
 * Documentation created: 09.03.2013 - 17:26:29 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

/**
 * The Class Ray.
 */
public class Ray {

	/** The dir. */
	Point2D.Float dir = new Point2D.Float();

	/** The start point. */
	Point2D.Float startPoint = new Point2D.Float();
	
	/** The end point. */
	Point2D.Float endPoint = new Point2D.Float();

	/** The m. */
	float m;
	
	/** The n. */
	float n;

	/** The vertical. */
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
		this.dir = new Point2D.Float(end.x - start.x, end.y - start.y);
		this.startPoint = start;
		this.endPoint = end;
		if (end.x != start.x) {
			m = (end.y - start.y) / (end.x - start.x);
			if (m != 0)
				n = start.y - m * start.x;
			else
				n = endPoint.y;
		} else {
			m = 0;
			n = endPoint.x;
			vertical = true;
		}
	}

	/**
	 * Gets the value.
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
	 * Normalize.
	 */
	public void normalize() {
		float l = (float) dir.distance(0, 0);
		dir.x /= l;
		dir.y /= l;
	}

	/**
	 * Gets the point.
	 * 
	 * @param t
	 *            the t
	 * @return the point
	 */
	public Point2D.Float getPoint(float t) {
		return new Point2D.Float(startPoint.x + t * dir.x, startPoint.y + t
				* dir.x);
	}

	/**
	 * Gets the parameter.
	 * 
	 * @param point
	 *            the point
	 * @return the parameter
	 */
	public float getParameter(Point2D.Float point) {
		float t1, t2;
		float epsilon = 0.1f;
		t1 = (point.x - startPoint.x) / dir.x;
		t2 = (point.y - startPoint.y) / dir.y;
		if (dir.x == 0 && dir.y != 0)
			return t2;
		if (dir.y == 0 && dir.x != 0)
			return t1;
		if (t1 + epsilon > t2 && t1 - epsilon < t2)
			return t1;
		return 0;

	}
}