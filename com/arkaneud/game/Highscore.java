package com.arkaneud.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import com.arkaneud.data.HighscoreLoader;
import com.arkaneud.data.HighscoreLoader.HighscoreDataItem;

public class Highscore {
	
	// Singleton
	
	private static final String HIGHSCORE_PATH = "highscore.hsl";
	private static Highscore instance;
	
	public static Highscore getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new Highscore();
	}
	
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
	
	private ArrayList<HighscoreItem> highscoreList;
	private static HighscoreLoader data = new HighscoreLoader();
	
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
	

	public ArrayList<HighscoreItem> getList() {
		Collections.sort(highscoreList);
		return new ArrayList<Highscore.HighscoreItem>(highscoreList);
	}
	
	public void save() {
		try {
			data.saveHighscore(HIGHSCORE_PATH);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, null, "Highscore couldn't be saved!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	// Subclass
	
	public class HighscoreItem implements Comparable<HighscoreItem> {
		String name;
		Integer points;
		
		public HighscoreItem(String name, int points) {
			this.name = name;
			this.points = points;
		}

		public String getName() {
			return name;
		}
		
		public Integer getPoints() {
			return points;
		}
		
		@Override
		public int compareTo(HighscoreItem o) {
			return this.points.compareTo(o.points);
		}
		
	}

	
}
