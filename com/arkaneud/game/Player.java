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
 * File: Player.java
 * Type: Player
 *
 * Documentation created: 09.03.2013 - 17:26:29 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.util.ArrayList;
import java.util.Observable;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player extends Observable implements Updateable {

	/** The has lost. */
	private boolean hasLost = false;

	/** The has won. */
	private boolean hasWon = false;

	/** The lives. */
	private int lives = 3;

	/** The name. */
	private String name = "Player";

	/** The points. */
	private int points = 0;

	/** The paddle. */
	private Paddle paddle;

	/** The balls list. */
	private ArrayList<Ball> ballsList = new ArrayList<Ball>();

	/** The level. */
	private Level level;

	/**
	 * Instantiates a new player.
	 * 
	 * @param name
	 *            the name
	 * @param level
	 *            the level
	 */
	public Player(String name, Level level) {
		paddle = new Paddle();
		this.name = name;
		this.level = level;
		// add three balls :)
		ballsList.add(new Ball());
		ballsList.add(new Ball());
		ballsList.add(new Ball());
		ballsList.get(0).setActive(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#update(long)
	 */
	@Override
	public void update(long gap) {
		if (hasWon || hasLost)
			return;

		paddle.updateObsersers(gap);
		if (level.getRemainingBricks() < 1) {
			hasWon = true;
		} else {
			if (ballsList.get(0).isLost()) {
				ballsList.remove(0);
				ballsList.trimToSize();
				if (ballsList.isEmpty()) {
					hasLost = true;
					return;
				}
				ballsList.get(0).setActive(true);
			}
			ballsList.get(0).updateObsersers(gap);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#updateObservers(long)
	 */
	@Override
	public void updateObservers(long gap) {
		update(gap);
		setChanged();
		notifyObservers(gap);
	}

	/**
	 * Checks for won.
	 * 
	 * @return true, if successful
	 */
	public boolean hasWon() {
		return hasWon;
	}

	/**
	 * Checks for lost.
	 * 
	 * @return true, if successful
	 */
	public boolean hasLost() {
		return hasLost;
	}

	/**
	 * Gets the lives.
	 * 
	 * @return the lives
	 */
	public int getLives() {
		return lives;
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
	public int getPoints() {
		return points;
	}

	/**
	 * Gets the paddle.
	 * 
	 * @return the paddle
	 */
	public Paddle getPaddle() {
		return paddle;
	}

	/**
	 * Gets the balls list.
	 * 
	 * @return the balls list
	 */
	public ArrayList<Ball> getBallsList() {
		return new ArrayList<Ball>(ballsList);
	}

	/**
	 * Gets the level.
	 * 
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

}