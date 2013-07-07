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
 * File: Start.java
 * Type: Start
 *
 * Documentation created: 07.07.2013 - 21:30:14 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

/**
 * The Class Start contains the main method.
 */
public class Start {

	/**
	 * Instantiates a new start.
	 */
	private Start() {

	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		// create a game window
		GameWindow game = new GameWindow();
		// and make it visible
		game.setVisible(true);
		
// Ray Caster Testing
//
//		RayCaster rc = RayCaster.getInstance();
//		Ray ray;
//		Float rect;
//		ray = new Ray(new Point2D.Float(0, 2), new Point2D.Float(4, 4));
//		ray.normalize();
//		rect = new Rectangle2D.Float(5, 4.5f, 4, 3);
//		ArrayList<CollisionResult> test = rc.intersectRay(ray, rect);
//		int i = 0;
//		for (CollisionResult c : test) {
//			if (c.isValid())
//				System.out.println(i++ + "] " + c.toString());
//
//		}
	}

}