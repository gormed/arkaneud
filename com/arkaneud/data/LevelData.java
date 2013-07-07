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
 * File: LevelData.java
 * Type: LevelData
 *
 * Documentation created: 07.07.2013 - 21:30:12 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Class LevelData discribes the level with its bricks.
 */
public class LevelData {

	/**
	 * The Class BrickData defines the general data needed to create a brick.
	 */
	public class BrickData {

		/** The x,y position of the brick. */
		public int x, y;

		/** The color. */
		public Color color;

		/**
		 * Instantiates a new brick data.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param color
		 *            the color
		 */
		public BrickData(float x, float y, Color color) {
			this.x = (int) x;
			this.y = (int) y;
			this.color = color;
		}

	}

	/** The brick data. */
	private ArrayList<BrickData> brickData;

	/**
	 * Instantiates a new level data.
	 */
	public LevelData() {
		createDataset();
	}

	/**
	 * Creates the dataset for the level hardcoded (could be loaded via XML).
	 */
	private void createDataset() {
		Random rand = new Random();

		brickData = new ArrayList<LevelData.BrickData>();
		for (float x = 1.0f; x < 6; x = x + 0.9f) {
			for (float y = 19; y > 13; y = y - 0.9f) {
				brickData.add(new BrickData(x * 60, y * 30, new Color(rand
						.nextInt(255), rand.nextInt(255), rand.nextInt(255))));
			}
		}
	}

	/**
	 * Gets the brick data.
	 * 
	 * @return the brick data
	 */
	public ArrayList<BrickData> getBrickData() {
		return new ArrayList<LevelData.BrickData>(brickData);
	}
}