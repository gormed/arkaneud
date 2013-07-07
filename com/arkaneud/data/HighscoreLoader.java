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
 * File: HighscoreLoader.java
 * Type: HighscoreLoader
 *
 * Documentation created: 07.07.2013 - 21:30:12 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.arkaneud.game.Highscore;
import com.arkaneud.game.Highscore.HighscoreItem;

/**
 * The Class HighscoreLoader loads a highscore list from filesys.
 */
public class HighscoreLoader {
	
	/**
	 * Load highscore from a specific path.
	 * 
	 * @param filepath
	 *            the filepath
	 * @return the array list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ArrayList<HighscoreDataItem> loadHighscore(String filepath) throws IOException {
		File file = new File(filepath);
		StringBuffer content = new StringBuffer();
		BufferedReader reader = null;
		ArrayList<HighscoreDataItem> highscoreList = new ArrayList<HighscoreDataItem>();
		 
		try {
			reader = new BufferedReader(new FileReader(file));
			String s = null;
		 
			while ((s = reader.readLine()) != null) {
				content.append(s).append(System.getProperty("line.separator"));
				String[] parts = s.split("[,]");
				if (parts.length == 2) {
					String name = parts[0];
					int points = Integer.parseInt(parts[1]);
					
					highscoreList.add(new HighscoreDataItem(name, points));
				}
			}
			
	 	} catch (FileNotFoundException e) {
	 		throw e;
	 	} catch (IOException e) {
		  throw e;
	 	} finally {
	 		try {
	 			if (reader != null) {
	 				reader.close();
	 			}
	 		} catch (IOException e) {
	 			throw e;
	 		}
	 	}
		return highscoreList;
	}
	
	/**
	 * Save highscore to a specific path.
	 * 
	 * @param filepath
	 *            the filepath
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void saveHighscore(String filepath) throws IOException {
		File file = new File(filepath);
		BufferedWriter writer = null;
		ArrayList<HighscoreItem> highscoreList = Highscore.getInstance().getList();
		
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (HighscoreItem it : highscoreList) {
				writer.append(it.getName()+","+it.getPoints());
				writer.newLine();			}
	 	} catch (FileNotFoundException e) {
	 		throw e;
	 	} catch (IOException e) {
		  throw e;
	 	} finally {
	 		try {
	 			if (writer != null) {
	 				writer.close();
	 			}
	 		} catch (IOException e) {
	 			throw e;
	 		}
	 	}
		return;
	}
	
	// Subclass
	
	/**
	 * The Class HighscoreDataItem stores the data of one HS entry.
	 */
	public class HighscoreDataItem {
		
		/** The name. */
		public String name;
		
		/** The points. */
		public Integer points;
		
		/**
		 * Instantiates a new highscore data item.
		 * 
		 * @param name
		 *            the name
		 * @param points
		 *            the points
		 */
		public HighscoreDataItem(String name, int points) {
			this.name = name;
			this.points = points;
		}
		
	}
}
