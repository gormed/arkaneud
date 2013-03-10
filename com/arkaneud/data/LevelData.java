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
 * Documentation created: 10.03.2013 - 14:02:43 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.data;

import java.awt.Color;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelData.
 */
public class LevelData {

	/**
	 * The Class BrickData.
	 */
	public class BrickData {
		
		/** The y. */
		public int x,y;
		
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
	public LevelData(){
		createDataset();
	}
	
	/**
	 * Creates the dataset.
	 */
	private void createDataset() {
		brickData = new ArrayList<LevelData.BrickData>();
		for (float x = 0.8f; x < 6; x++) {
			for (float y = 19; y > 13; y--) {
				brickData.add(new BrickData(x * 60, y * 30, Color.GREEN));				
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