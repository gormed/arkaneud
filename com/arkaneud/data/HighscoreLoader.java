package com.arkaneud.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.arkaneud.game.Highscore;
import com.arkaneud.game.Highscore.HighscoreItem;

public class HighscoreLoader {
	
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
	
	public void saveHighscore(String filepath) throws IOException {
		File file = new File(filepath);
		BufferedWriter writer = null;
		ArrayList<HighscoreItem> highscoreList = Highscore.getInstance().getList();
		
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (HighscoreItem it : highscoreList) {
				writer.append(it.getName()+","+it.getPoints());
			}
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
	
	public class HighscoreDataItem {
		public String name;
		public Integer points;
		
		public HighscoreDataItem(String name, int points) {
			this.name = name;
			this.points = points;
		}
		
	}
}
