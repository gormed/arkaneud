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
 * File: GameWindow.java
 * Type: GameWindow
 *
 * Documentation created: 09.03.2013 - 17:26:29 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.JFrame;

import com.arkaneud.data.LevelData;
import com.arkaneud.game.Ball;
import com.arkaneud.game.Brick;
import com.arkaneud.game.Entity;
import com.arkaneud.game.Level;

// TODO: Auto-generated Javadoc
/**
 * The Class GameWindow.
 */
public class GameWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5097177280076223641L;

	/** The drawn elements list. */
	private HashMap<Long, GUIElement> drawnElementsList = new HashMap<Long, GUIElement>();

	/** The entity list. */
	private HashMap<Long, Entity> entityList = new HashMap<Long, Entity>();

	/** The level. */
	private Level level;

	/** The game key listener. */
	private GameKeyListener gameKeyListener;

	/** The updater. */
	private Runnable updater;

	/** The update thread. */
	private Thread updateThread;

	/** The window listener. */
	private GameWindowListener windowListener;

	/** The game buffer. */
	private GameBuffer gameBuffer;

	/**
	 * Instantiates a new game window.
	 */
	public GameWindow() {
		// Frame setup
		super("Arkaneud");
		setSize(Level.LEVEL_WIDTH, Level.LEVEL_HEIGHT);
		setResizable(false);
		setLocationByPlatform(true);
		// double buffer drawing logic
		gameBuffer = new GameBuffer();
		add(gameBuffer);

		// create level from data
		level = Level.getInstance();
		level.initialize(new LevelData());

		// add the listeners for user input and exit handling
		gameBuffer.addKeyListener(gameKeyListener = new GameKeyListener());
		addWindowListener(windowListener = new GameWindowListener());
		// load level, create ball and paddle
		loadLevel();
		// update loop
		updater = new Runnable() {

			@Override
			public void run() {
				// init time
				long time = System.currentTimeMillis();
				long updateTime = time;
				long drawTime = time;
				long gap = 0;
				// sync the key input with thread
				synchronized (gameKeyListener) {
					// loop endless until exit request
					while (!gameKeyListener.exit) {
						// get current time
						time = System.currentTimeMillis();
						gap = time - updateTime;
						// inject inputs manually
						level.getLocalPlayer()
								.getPaddle()
								.move(gameKeyListener.left,
										gameKeyListener.right);
						// update level elements
						level.update(gap);
						if ((time - drawTime) > 16) {
							// draw synchronized all 16 ms
							drawTime = time;
							gameBuffer.repaint();
						}
						updateTime = time;
					}
				}
				level.destory();
				System.exit(0);
			}
		};
		// update thread
		updateThread = new Thread(updater);
	}

	/**
	 * Load level.
	 */
	private void loadLevel() {
		// add paddle and ball in updater and drawer
		addElement(new PaddleElement(), level.getLocalPlayer().getPaddle());
		for (Ball b : level.getLocalPlayer().getBallsList()) {
			addElement(new BallElement(), b);
		}

		addElement(new TextElement("Lives: ") {

			@Override
			public void update(Observable o, Object arg) {
				// TODO Auto-generated method stub

			}

			@Override
			public void draw(Graphics g) {
				if (isVisible) {
					g.drawString(this.getText() + " "
							+ level.getLocalPlayer().getLives(), x, y);
				}
			}
		}, null);

		// add each brick
		for (Brick b : level.getBricksList()) {
			addElement(new BrickElement(), b);
		}
	}

	/**
	 * Adds the element.
	 * 
	 * @param element
	 *            the element
	 * @param entity
	 *            the entity
	 * @return the gUI element
	 */
	public GUIElement addElement(GUIElement element, Entity entity) {
		// add the observer
		if (entity != null)
			entity.addObserver(element);
		// add entity to list
		entityList.put(element.getElementID(), entity);
		// add the according gui element to draw list
		return drawnElementsList.put(element.getElementID(), element);
	}

	/**
	 * Gets the draw element.
	 * 
	 * @param id
	 *            the id
	 * @return the draw element
	 */
	public GUIElement getDrawElement(long id) {
		return drawnElementsList.get(id);
	}

	/**
	 * Gets the entity.
	 * 
	 * @param id
	 *            the id
	 * @return the entity
	 */
	public Entity getEntity(long id) {
		return entityList.get(id);
	}

	/**
	 * Removes the gui element.
	 * 
	 * @param id
	 *            the id
	 */
	public void removeGUIElement(long id) {
		entityList.remove(id).deleteObserver(drawnElementsList.remove(id));
	}

	/**
	 * The listener interface for receiving gameKey events. The class that is
	 * interested in processing a gameKey event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addGameKeyListener<code> method. When
	 * the gameKey event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see GameKeyEvent
	 */
	private class GameKeyListener implements KeyListener {

		/** The left. */
		boolean left;

		/** The right. */
		boolean right;

		/** The exit. */
		boolean exit;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				right = true;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!updateThread.isAlive())
					updateThread.start();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				left = false;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				right = false;
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				exit = true;
			}
		}
	}

	/**
	 * The listener interface for receiving gameWindow events. The class that is
	 * interested in processing a gameWindow event implements this interface,
	 * and the object created with that class is registered with a component
	 * using the component's <code>addGameWindowListener<code> method. When
	 * the gameWindow event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see GameWindowEvent
	 */
	private class GameWindowListener implements WindowListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent
		 * )
		 */
		@Override
		public void windowOpened(WindowEvent e) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent
		 * )
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			gameKeyListener.exit = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent
		 * )
		 */
		@Override
		public void windowClosed(WindowEvent e) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent
		 * )
		 */
		@Override
		public void windowIconified(WindowEvent e) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.
		 * WindowEvent)
		 */
		@Override
		public void windowDeiconified(WindowEvent e) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent
		 * )
		 */
		@Override
		public void windowActivated(WindowEvent e) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.
		 * WindowEvent)
		 */
		@Override
		public void windowDeactivated(WindowEvent e) {

		}

	}

	/**
	 * The Class GameBuffer.
	 */
	private class GameBuffer extends DoubleBuffer {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 6402460431074494210L;

		/**
		 * Instantiates a new game buffer.
		 */
		public GameBuffer() {
			setBackground(Color.WHITE);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.arkaneud.gui.DoubleBuffer#paintBuffer(java.awt.Graphics)
		 */
		@Override
		public void paintBuffer(Graphics g) {
			for (Map.Entry<Long, GUIElement> entry : drawnElementsList
					.entrySet()) {
				entry.getValue().draw(g);
			}
		}
	}
}