package com.arkaneud.game;

public abstract class DynamicCollidable extends Collidable {

	/** The velocity in x/y direction. */
	protected float velocityX, velocityY;

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
