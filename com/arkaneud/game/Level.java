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
 * File: Level.java
 * Type: Level
 *
 * Documentation created: 10.03.2013 - 14:02:43 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.util.ArrayList;
import java.util.Observable;

import com.arkaneud.data.*;

/**
 * The Class Level discribes the levels content and holds the player object.
 * This class is a singleton!
 */
public class Level extends Observable implements Updateable {

	/** The Constant LEVEL_HEIGHT. */
	public static final int LEVEL_HEIGHT = 600;

	/** The Constant LEVEL_WIDTH. */
	public static final int LEVEL_WIDTH = 400;

	/** The instance. */
	private static Level instance;

	/** The is inistialized. */
	private static boolean isInistialized = false;

	/** The is over flag, true if the player has won or lost. */
	private boolean isOver = false;

	/** The levels data. */
	private LevelData levelData;

	/** The bricks list. */
	private ArrayList<Brick> bricksList = null;

	/** The local player. */
	private Controller playerController;

	/**
	 * Instantiates a new level.
	 */
	private Level() {

	}

	/**
	 * Gets the single instance of Level.
	 * 
	 * @return single instance of Level
	 */
	public static Level getInstance() {
		if (instance != null)
			return instance;
		return instance = new Level();
	}

	/**
	 * Initializes the level with the given data.
	 * 
	 * @param data
	 *            the data
	 */
	public void initialize(LevelData data, String playerName) {
		if (isInistialized)
			return;

		isOver = false;
		levelData = data;
		bricksList = getBricksFromData();
		/*
		 * This code works only on windows/not with the openJDK for linux
		 * com.sun.security.auth.module.NTSystem NTSystem = new
		 * com.sun.security.auth.module.NTSystem(); localPlayer = new
		 * Player(System.getProperty(NTSystem.getName()), this);
		 */
		playerController = new Controller(playerName);

		isInistialized = true;
	}

	/**
	 * Destorys the level instance so it can be reinitialized.
	 */
	public void destroy() {
		if (!isInistialized)
			return;

		levelData = null;
		bricksList.clear();
		playerController = null;

		isInistialized = false;
	}

	/**
	 * Gets the bricks from data.
	 * 
	 * @return the bricks from data
	 */
	private ArrayList<Brick> getBricksFromData() {
		ArrayList<Brick> input = new ArrayList<Brick>();

		for (LevelData.BrickData e : levelData.getBrickData()) {
			input.add(new Brick(e));
		}

		return input;
	}

	/**
	 * Gets the bricks list.
	 * 
	 * @return the bricks list
	 */
	public ArrayList<Brick> getBricksList() {
		return new ArrayList<Brick>(bricksList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#update(long)
	 */
	@Override
	public void update(float gap) {
		// update the players controller
		playerController.updateObservers(gap);
		// check for game-end
		if (playerController.hasLost() || playerController.hasWon()) {
			// signal the game is over
			isOver = true;
		}
		// update all bricks. this is actually unused, but can be used for
		// animation purposes
		for (Brick b : bricksList) {
			b.updateObservers(gap);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#updateObservers(long)
	 */
	@Override
	public void updateObservers(float gap) {
		if (!isInistialized)
			return;
		update(gap);
		setChanged();
		notifyObservers(gap);
	}

	/**
	 * Gets the remaining bricks.
	 * 
	 * @return the remaining bricks
	 */
	public int getRemainingBricks() {
		return bricksList.size();
	}

	/**
	 * Checks if level is over.
	 * 
	 * @return true, if is over
	 */
	public boolean isOver() {
		return isOver;
	}

	/**
	 * Gets the local player.
	 * 
	 * @return the local player
	 */
	public Controller getPlayerController() {
		return playerController;
	}

}