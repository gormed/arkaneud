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
 * File: DynamicCollidable.java
 * Type: DynamicCollidable
 *
 * Documentation created: 07.07.2013 - 21:30:15 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

/**
 * The Class DynamicCollidable.
 */
public abstract class DynamicCollidable extends Collidable {

	/** The velocity in x/y direction. */
	protected float velocityX, velocityY;

	/**
	 * Collides this collidable with a static one.
	 * 
	 * @param collidable
	 *            the collidable
	 * @return true, if successful
	 */
	public boolean collide(StaticCollidable collidable) {
		boolean lr = false, tb = false;
		// left
		if (this.collision.x + this.collision.width < collidable.collision.x)
			lr = true;
		// right
		if (this.collision.x > collidable.collision.x + collidable.width)
			lr = true;
		// top
		if (this.collision.y + this.collision.height < collidable.collision.y)
			tb = true;
		// bottom
		if (this.collision.y > collidable.collision.y + collidable.height)
			tb = true;

		if (tb && this.collision.x < collidable.collision.x + collidable.width
			   && this.collision.x + this.collision.width > collidable.collision.x) {
			velocityY *= -1.f;
			return true;
		}
		if (lr && this.collision.y < collidable.collision.y + collidable.height
			   && this.collision.y + this.collision.height > collidable.collision.y) {
			velocityX *= -1.f;
			return true;
		}
		return false;
	}
}
