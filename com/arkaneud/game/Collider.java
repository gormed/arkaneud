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
 * File: Collider.java
 * Type: Collider
 *
 * Documentation created: 10.03.2013 - 14:02:44 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The Class Collider.
 */
public class Collider {

	/** The instance. */
	public static Collider instance;

	/**
	 * Instantiates a new collider.
	 */
	private Collider() {

	}

	/**
	 * Gets the instance.
	 * 
	 * @return the instance
	 */
	public static Collider getInstance() {
		if (instance != null)
			return instance;
		return instance = new Collider();
	}

	/**
	 * Intersects.
	 * 
	 * @param circle
	 *            the circle
	 * @param rect
	 *            the rect
	 * @return true, if successful
	 */
	public boolean intersects(Ball circle, Paddle rect) {
		Point2D.Float circleDistance = new Point2D.Float();
		circleDistance.x = Math.abs(circle.xPos - rect.xPos);
		circleDistance.y = Math.abs(circle.yPos - rect.yPos);

		if (circleDistance.x > (rect.width / 2 + circle.radius)) {
			return false;
		}
		if (circleDistance.y > (rect.height / 2 + circle.radius)) {
			return false;
		}

		if (circleDistance.x <= (rect.width / 2)) {
			return true;
		}
		if (circleDistance.y <= (rect.height / 2)) {
			return true;
		}

		double cornerDistance_sq = Math.pow(
				(circleDistance.x - rect.width / 2), 2)
				+ Math.pow((circleDistance.y - rect.height / 2), 2);

		return (cornerDistance_sq <= Math.pow(circle.radius, 2));
	}

	/**
	 * Intersects.
	 * 
	 * @param circle
	 *            the circle
	 * @param rect
	 *            the rect
	 * @return true, if successful
	 */
	public boolean intersects(Ball circle, Brick rect) {
		Point2D.Float circleDistance = new Point2D.Float();
		circleDistance.x = Math.abs(circle.xPos - rect.xPos);
		circleDistance.y = Math.abs(circle.yPos - rect.yPos);

		if (circleDistance.x > (rect.width / 2 + circle.radius)) {
			return false;
		}
		if (circleDistance.y > (rect.height / 2 + circle.radius)) {
			return false;
		}

		if (circleDistance.x <= (rect.width / 2)) {
			return true;
		}
		if (circleDistance.y <= (rect.height / 2)) {
			return true;
		}

		double cornerDistance_sq = Math.pow(
				(circleDistance.x - rect.width / 2), 2)
				+ Math.pow((circleDistance.y - rect.height / 2), 2);

		return (cornerDistance_sq <= Math.pow(circle.radius, 2));
	}

	/**
	 * The Class RayCaster.
	 */
	public static class RayCaster {

		/** The instance. */
		private static RayCaster instance;

		/** The collision results. */
		private static ArrayList<CollisionResult> collisionResults = new ArrayList<Collider.RayCaster.CollisionResult>();

		/** The comparator. */
		private static Comparator<CollisionResult> comparator = new Comparator<CollisionResult>() {

			@Override
			public int compare(CollisionResult o1, CollisionResult o2) {
				if (o1.interpolator < o2.interpolator)
					return -1;
				if (o1.interpolator >= o2.interpolator)
					return 1;
				else
					return 0;
			}
		};

		/**
		 * Instantiates a new ray caster.
		 */
		private RayCaster() {

		}

		/**
		 * Gets the instance.
		 * 
		 * @return the instance
		 */
		public static RayCaster getInstance() {
			if (instance != null) {
				return instance;
			}
			return instance = new RayCaster();
		}

		/**
		 * Gets the collision results.
		 * 
		 * @return the collision results
		 */
		public ArrayList<CollisionResult> getCollisionResults() {
			return collisionResults;
		}

		/**
		 * Intersect ray.
		 * 
		 * @param ray
		 *            the ray
		 * @param rect
		 *            the rect
		 * @return the array list
		 */
		public ArrayList<CollisionResult> intersectRay(Ray ray,
				Rectangle2D.Float rect) {
			collisionResults.clear();
			Line left, right, top, bottom;
			Point2D.Float tl, tr, bl, br;

			//
			// top
			//
			// tl-------->tr
			// /\ |
			// l| |
			// e| X | right
			// f| mid |
			// t| \/
			// bl<--------br
			//
			// bottom
			//

			tl = new Point2D.Float(rect.x - rect.width * 0.5f, rect.y
					+ rect.height * 0.5f);
			tr = new Point2D.Float(rect.x + rect.width * 0.5f, rect.y
					+ rect.height * 0.5f);
			bl = new Point2D.Float(rect.x - rect.width * 0.5f, rect.y
					- rect.height * 0.5f);
			br = new Point2D.Float(rect.x + rect.width * 0.5f, rect.y
					- rect.height * 0.5f);

			left = new Line(tl, bl);
			bottom = new Line(bl, br);
			right = new Line(br, tr);
			top = new Line(tr, tl);

			collisionResults.add(subintersectRay(ray, left));
			collisionResults.add(subintersectRay(ray, bottom));
			collisionResults.add(subintersectRay(ray, right));
			collisionResults.add(subintersectRay(ray, top));

			return collisionResults;
		}

		/**
		 * Validate collisions.
		 * 
		 * @param collisions
		 *            the collisions
		 */
		public void validateCollisions(ArrayList<CollisionResult> collisions) {
			ArrayList<CollisionResult> temp = new ArrayList<CollisionResult>();
			for (CollisionResult c : collisions) {
				if (c != null) {
					if (!c.isValid())
						temp.add(c);
				}
			}
			for (CollisionResult c : temp) {
				collisions.remove(c);
			}
			collisions.trimToSize();
		}

		/**
		 * Sort by distance.
		 * 
		 * @param collisions
		 *            the collisions
		 */
		public void sortByDistance(ArrayList<CollisionResult> collisions) {
			Collections.sort(collisions, comparator);
		}

		/**
		 * Subintersect ray.
		 * 
		 * @param ray
		 *            the ray
		 * @param side
		 *            the side
		 * @return the collision result
		 */
		private CollisionResult subintersectRay(Ray ray, Line side) {
			// side as a ray
			Ray sideRay = side.getRay();
			// accuracy param
			float epsilon = 0.1f;
			// create empty collision result
			CollisionResult c = new CollisionResult(null, null, false, -1);
			// parallel?
			if ((ray.m + epsilon < sideRay.m && ray.m - epsilon > sideRay.m)
					|| (sideRay.vertical && ray.vertical)) {
				return c;
			}
			float x = 0;
			float y1 = 0, y2 = 0;
			// check if one of the rays is vertical
			if (sideRay.vertical || ray.vertical) {
				// if that is so, the x value is the n of the ray
				if (sideRay.vertical) {
					x = sideRay.n;
					y1 = y2 = ray.getValue(x);
				} else if (ray.vertical) {
					x = ray.n;
					y1 = y2 = sideRay.getValue(x);
				}

			} else {
				// else we calculate x and y normally
				x = (sideRay.n - ray.n) / (ray.m - sideRay.m);
				y1 = ray.getValue(x);
				y2 = sideRay.getValue(x);
			}
			// check if the result for the points match an epsilon area
			if (y1 + epsilon > y2 && y1 - epsilon < y2) {
				Point2D.Float normal = new Point2D.Float();
				// create collision point
				Point2D.Float point = new Point2D.Float(x, y1);
				// get the interpolation param on side ray (0-1 is between start
				// and end point)
				float t = sideRay.getParameter(point);
				// get interpolation param on ray (smaller is closer to origin
				// of the ray)
				float s = ray.getParameter(point);
				// Calculate the normal of the side which was hit
				normal.x = -sideRay.dir.y;
				normal.y = sideRay.dir.x;
				// only valid between start and end point
				boolean valid = (t >= 0 && t <= 1);
				// create the result accordingly
				CollisionResult res = new CollisionResult(point, normal, valid,
						s);
				return res;
			}
			return c;
		}

		/**
		 * The Class CollisionResult.
		 */
		public class CollisionResult {

			/** The valid. */
			boolean valid = false;

			/** The interpolator. */
			float interpolator = 0;

			/** The collision point. */
			Point2D.Float collisionPoint;

			/** The collision normal. */
			Point2D.Float collisionNormal;

			/**
			 * Instantiates a new collision result.
			 * 
			 * @param collisionPoint
			 *            the collision point
			 * @param collisionNormal
			 *            the collision normal
			 * @param valid
			 *            the valid
			 * @param interpolator
			 *            the interpolator
			 */
			public CollisionResult(Point2D.Float collisionPoint,
					Point2D.Float collisionNormal, boolean valid,
					float interpolator) {
				this.collisionPoint = collisionPoint;
				this.collisionNormal = collisionNormal;
				this.valid = valid;
				this.interpolator = interpolator;
			}

			/**
			 * Checks if is the valid.
			 * 
			 * @return the valid
			 */
			public boolean isValid() {
				return valid;
			}

			/**
			 * Gets the interpolator.
			 * 
			 * @return the interpolator
			 */
			public float getInterpolator() {
				return interpolator;
			}

			/**
			 * Gets the collision point.
			 * 
			 * @return the collision point
			 */
			public Point2D.Float getCollisionPoint() {
				return collisionPoint;
			}

			/**
			 * Gets the collision normal.
			 * 
			 * @return the collision normal
			 */
			public Point2D.Float getCollisionNormal() {
				return collisionNormal;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Object#toString()
			 */
			public String toString() {
				return "valid: " + valid + " - t: " + interpolator
						+ " - point: " + collisionPoint.toString()
						+ " - normal: " + collisionNormal.toString();
			}
		}
	}
}
