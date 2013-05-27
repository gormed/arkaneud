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
 * File: Highscore.java
 * Type: Highscore
 *
 * Documentation created: 26.05.2013 - 14:48:31 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import com.arkaneud.data.HighscoreLoader;
import com.arkaneud.data.HighscoreLoader.HighscoreDataItem;

/**
 * The Class Highscore.
 */
public class Highscore {

	/** The Constant HIGHSCORE_PATH. */
	private static final String HIGHSCORE_PATH = "highscore.hsl";
	
	// Singleton
	
	/** The instance. */
	private static Highscore instance;
	
	/**
	 * Gets the single instance of Highscore.
	 * 
	 * @return single instance of Highscore
	 */
	public static Highscore getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new Highscore();
	}
	
	/**
	 * Instantiates a new highscore.
	 */
	private Highscore() {
		try {
			ArrayList<HighscoreDataItem> dataList = data.loadHighscore(HIGHSCORE_PATH);
			highscoreList = new ArrayList<Highscore.HighscoreItem>();
			for (HighscoreDataItem dataIt : dataList) {
				highscoreList.add(new HighscoreItem(dataIt.name, dataIt.points));
			}
			Collections.sort(highscoreList);
		} catch (IOException e) {
			highscoreList = new ArrayList<HighscoreItem>();
			e.printStackTrace();
		}
	}
	
	// Class
	
	/** The highscore list. */
	private ArrayList<HighscoreItem> highscoreList;
	
	/** The data of the highscore. */
	private static HighscoreLoader data = new HighscoreLoader();
	
	/**
	 * Insert item into the HS-List.
	 * 
	 * @param name
	 *            the name of the player
	 * @param points
	 *            the points of the player
	 * @return true, if successful inserted, false if the points were lower than the lowest entry
	 */
	public boolean insertItem(String name, int points) {
		
		HighscoreItem it = new HighscoreItem(name, points);
		if (highscoreList.size() > 0) {
			if (highscoreList.get(highscoreList.size()-1).compareTo(it) < 0) {
				highscoreList.add(it);
				Collections.sort(highscoreList);
				return true;
			}
		} else {
			highscoreList.add(it);
			return true;
		}
		
		return false;
	}
	

	/**
	 * Gets the list.
	 * 
	 * @return the list
	 */
	public ArrayList<HighscoreItem> getList() {
		Collections.sort(highscoreList);
		return new ArrayList<Highscore.HighscoreItem>(highscoreList);
	}
	
	/**
	 * Save.
	 */
	public void save() {
		try {
			data.saveHighscore(HIGHSCORE_PATH);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, null, "Highscore couldn't be saved!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	// Subclass
	
	/**
	 * The Class HighscoreItem.
	 */
	public class HighscoreItem implements Comparable<HighscoreItem> {
		
		/** The name. */
		String name;
		
		/** The points. */
		Integer points;
		
		/**
		 * Instantiates a new highscore item.
		 * 
		 * @param name
		 *            the name
		 * @param points
		 *            the points
		 */
		public HighscoreItem(String name, int points) {
			this.name = name;
			this.points = points;
		}

		/**
		 * Gets the name.
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Gets the points.
		 * 
		 * @return the points
		 */
		public Integer getPoints() {
			return points;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(HighscoreItem o) {
			return this.points.compareTo(o.points);
		}
		
	}

	
}
