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
 * File: Line.java
 * Type: Line
 *
 * Documentation created: 10.03.2013 - 14:02:43 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Point2D;

/**
 * The Class Line defines a simple 2D line.
 */
public class Line {
	
	/** The start. */
	Point2D.Float start;
	
	/** The end. */
	Point2D.Float end;

	/**
	 * Instantiates a new line.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public Line(Point2D.Float start, Point2D.Float end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Gets the line as a vector.
	 * 
	 * @return the line
	 */
	public Point2D.Float getVector() {
		return new Point2D.Float(end.x - start.x, end.y - start.y);
	}

	/**
	 * Gets the line as a ray.
	 * 
	 * @return the ray
	 */
	public Ray getRay() {
		return new Ray(start, end);
	}
}