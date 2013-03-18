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
 * Documentation created: 14.03.2013 - 14:27:14 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.game;

import java.util.ArrayList;
import java.util.Observable;

/**
 * The Class Player represents the player in simulation and holds the paddle as
 * well as the ball references.
 */
public class Player extends Observable implements Updateable {

	/** The has lost flag indicates if the player has lost the game. */
	private boolean hasLost = false;

	/** The has won flag indicates if the player has won the game. */
	private boolean hasWon = false;

	/** The lives of the player. */
	private int lives = 3;

	/** The name of the player. */
	private String name = "Player";

	/** The points of the player. */
	private int points = 0;

	/** The paddle reference. */
	private Paddle paddle;

	/** The balls list the player has remaining. */
	private ArrayList<Ball> ballsList = new ArrayList<Ball>();

	/**
	 * Instantiates a new player.
	 * 
	 * @param name
	 *            the name
	 */
	public Player(String name) {
		paddle = new Paddle();
		this.name = name;
		// add three balls :)
		ballsList.add(new Ball());
		ballsList.add(new Ball());
		ballsList.add(new Ball());
		// set the first one active
		ballsList.get(0).setActive(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#update(long)
	 */
	@Override
	public void update(float gap) {
		// dont do anything if the player has lost or won
		if (hasWon || hasLost)
			return;
		// update the paddle
		paddle.updateObservers(gap);
		// check if the player has won
		if (Level.getInstance().getRemainingBricks() < 1) {
			hasWon = true;
		} else {
			// check if the current ball is lost
			if (ballsList.get(0).isLost()) {
				// decrease lives and remove the ball
				lives--;
				ballsList.remove(0);
				ballsList.trimToSize();
				// check if there are balls remaining
				if (ballsList.isEmpty()) {
					// if not the player has lost
					hasLost = true;
					return;
				} else
					// otherwise we get a new ball for the player
					ballsList.get(0).setActive(true);
			}
			// update the current ball
			ballsList.get(0).updateObservers(gap);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.arkaneud.game.Updateable#updateObservers(long)
	 */
	@Override
	public void updateObservers(float gap) {
		// first update the object
		update(gap);
		// set changed flag
		setChanged();
		// notify the observing objects (gui) that there are changes to display
		notifyObservers(gap);
	}

	/**
	 * Checks if player has won.
	 * 
	 * @return true, if won
	 */
	public boolean hasWon() {
		return hasWon;
	}

	/**
	 * Checks if player has lost.
	 * 
	 * @return true, if lost
	 */
	public boolean hasLost() {
		return hasLost;
	}

	/**
	 * Gets the lives of the player.
	 * 
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Gets the name of the player.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the points of the player.
	 * 
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Gets the paddle of the player.
	 * 
	 * @return the paddle
	 */
	public Paddle getPaddle() {
		return paddle;
	}

	/**
	 * Gets the balls list, remaining for the player.
	 * 
	 * @return the balls list
	 */
	public ArrayList<Ball> getBallsList() {
		return new ArrayList<Ball>(ballsList);
	}

	/**
	 * Adds the points recieved for destroying a brick.
	 * 
	 * @param points
	 *            the points
	 */
	void addPoints(int points) {
		this.points += points;
	}

}