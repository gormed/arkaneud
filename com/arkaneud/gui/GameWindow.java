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
 * Documentation created: 10.03.2013 - 14:02:44 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.arkaneud.data.LevelData;
import com.arkaneud.game.Ball;
import com.arkaneud.game.Brick;
import com.arkaneud.game.Collidable;
import com.arkaneud.game.Highscore;
import com.arkaneud.game.Level;

/**
 * The Class GameWindow is the main game JFrame window of the game.
 */
public class GameWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5097177280076223641L;

	/** The drawn elements list. */
	private Map<Long, GUIElement> drawnElementsList = Collections
			.synchronizedMap(new HashMap<Long, GUIElement>());

	/** The GUI Elements that need to be added */
	private ArrayList<GUIElement> addGUIElements = new ArrayList<GUIElement>();
	/** The GUI Elements that need to be removed */
	private ArrayList<Long> removeGUIElements = new ArrayList<Long>();

	/** The entity list. */
	private Map<Long, Collidable> entityList = Collections
			.synchronizedMap(new HashMap<Long, Collidable>());

	/** The level reference. */
	private Level level;

	/** The game key listener. */
	private GameKeyListener gameKeyListener;

	/** The update method task. */
	private Runnable updater;

	/** The update thread. */
	private Thread updateThread;

	/** The window listener. */
	@SuppressWarnings("unused")
	private GameWindowListener windowListener;

	/** The Menu-Panel displaying the components */
	private MenuPanel menu;
	/** The Highscore display panel */
	private HighscorePanel highscore;

	/** The game buffer. */
	private GameBuffer gameBuffer;

	/** The exit flag if user wants to leave. */
	boolean exit;

	/**
	 * The active flag that indicates if the window has focus. Pauses the game
	 * if false.
	 */
	private boolean active = false;

	/**
	 * Instantiates a new game window.
	 */
	public GameWindow() {
		// Frame setup
		super("Arkaneud");
		// contentPane = this.getContentPane();
		// init main menu
		init();

	}

	public void startGame() {
		// load level, create ball and paddle
		loadLevel();
		// start the game-thread
		startGameThread();
	}

	public void showHighscore() {
		highscore.refresh();
		add(highscore);
		validate();
	}

	public void exitGame() {
		Highscore.getInstance().save();
		if (updateThread != null && updateThread.isAlive())
			exit = true;
		else
			System.exit(0);
	}

	/**
	 * Sets up the game-thread.
	 */
	private void startGameThread() {
		// update loop
		updater = new Runnable() {

			@Override
			public void run() {
				// init time
				long time = System.currentTimeMillis();
				long updateTime = time;
				long drawTime = time;
				long gap = 0;
				// reset the exit flag
				exit = false;
				// loop endless until exit request
				while (!exit) {
					// if game loses the focus
					if (!active)
						updateTime = System.currentTimeMillis();
					// get current time
					time = System.currentTimeMillis();
					gap = time - updateTime;
					// inject inputs manually
					level.getPlayerController().getPaddle()
							.move(gameKeyListener.left, gameKeyListener.right);
					if (!level.isOver()) {
						// update level elements
						level.update((float) (((double) gap) * 0.001));
						if ((time - drawTime) > 16) {
							// draw synchronized all 16 ms
							drawTime = time;
							gameBuffer.repaint();
						}
						updateTime = time;
					} else {
						if (Highscore.getInstance().insertItem(
								level.getPlayerController().getName(),
								level.getPlayerController().getPoints())) {
							unloadLevel();
							showHighscore();
						} else {
							unloadLevel();
							showMenu();
						}
					}
				}
				// on leaving the loop, destroy everything
				level.destroy();
				remove(gameBuffer);
				gameBuffer.removeKeyListener(gameKeyListener);
				gameKeyListener = null;
				gameBuffer = null;
				entityList.clear();
				drawnElementsList.clear();
			}
		};
		// update thread
		updateThread = new Thread(updater);
	}

	/**
	 * Initialize the game.
	 */
	private void init() {
		setSize(Level.LEVEL_WIDTH, Level.LEVEL_HEIGHT);
		setResizable(false);
		setLocationByPlatform(true);
		addWindowListener(windowListener = new GameWindowListener());
		menu = new MenuPanel(this);
		highscore = new HighscorePanel(this);

		showMenu();
	}

	public void showMenu() {
		add(menu);
		validate();
	}

	/**
	 * Load level.
	 */
	private void loadLevel() {

		// show a input dlg for the players name
		String name = JOptionPane.showInputDialog("What is your name?");
		if (name == null) {
			name = "nameless";
		}
		// double buffer drawing logic
		gameBuffer = new GameBuffer();
		add(gameBuffer);

		// create level from data
		level = Level.getInstance();
		level.initialize(new LevelData(), name);

		// add the listeners for user input and exit handling
		gameBuffer.addKeyListener(gameKeyListener = new GameKeyListener());

		// add paddle and ball in updater and drawer
		addElement(new PaddleElement(), level.getPlayerController().getPaddle());
		for (Ball b : level.getPlayerController().getBallsList()) {
			addElement(new BallElement(), b);
		}

		// add text for lives
		addElement(new TextElement("Lives: ", 20, Level.LEVEL_HEIGHT - 50) {
			Font font = new Font("Arial Bold", Font.BOLD, 24);

			@Override
			public void draw(Graphics g) {
				if (isVisible) {
					g.setColor(Color.RED);
					g.setFont(font);
					g.drawString(this.getText() + " "
							+ level.getPlayerController().getLives(), x, y);
				}
			}
		}, null);
		// add text for points
		addElement(new TextElement("Points: ", Level.LEVEL_WIDTH - 160,
				Level.LEVEL_HEIGHT - 50) {
			Font font = new Font("Arial Bold", Font.BOLD, 18);

			@Override
			public void draw(Graphics g) {
				if (isVisible) {
					g.setColor(Color.BLUE);
					g.setFont(font);
					g.drawString(this.getText() + " "
							+ level.getPlayerController().getPoints(), x, y);
				}
			}
		}, null);

		// add text for points
		addElement(new TextElement("PRESS enter TO START!",
				(int) (Level.LEVEL_WIDTH * 0.5f - 170), 100) {
			Font font = new Font("Arial Bold", Font.BOLD, 28);

			@Override
			public void draw(Graphics g) {
				if (isVisible) {
					g.setColor(Color.RED);
					g.setFont(font);
					g.drawString(this.getText(), x, y);
				}
				if (updateThread.isAlive()) {
					removeGUIElement(this.getElementID());
				}
			}
		}, null);

		// add each brick from the level
		for (Brick b : level.getBricksList()) {
			addElement(new BrickElement(), b);
		}
	}

	private void unloadLevel() {
		exit = true;
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
	public GUIElement addElement(GUIElement element, Collidable entity) {
		// add the observer
		if (entity != null)
			entity.addObserver(element);
		// add entity to list
		entityList.put(element.getElementID(), entity);
		addGUIElements.add(element);
		// add the according gui element to draw list
		return element;
	}

	/**
	 * Removes the gui element.
	 * 
	 * @param id
	 *            the id
	 */
	public void removeGUIElement(long id) {
		Observable o = entityList.remove(id);
		if (o != null)
			o.deleteObserver(drawnElementsList.remove(id));
		else
			removeGUIElements.add(id);
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
	public Collidable getEntity(long id) {
		return entityList.get(id);
	}

	/**
	 * The listener class for receiving key events.
	 */
	private class GameKeyListener implements KeyListener {

		/** The left movement flag for the left arrow key. */
		boolean left;

		/** The right movement flag for the right arrow key. */
		boolean right;

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
	 * The listener interface for receiving window events.
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
			if (updateThread != null && updateThread.isAlive())
				exit = true;
			else
				System.exit(0);
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
			active = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.
		 * WindowEvent)
		 */
		@Override
		public void windowDeactivated(WindowEvent e) {
			active = false;
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
			for (GUIElement element : addGUIElements) {
				drawnElementsList.put(element.getElementID(), element);
			}
			for (Long id : removeGUIElements) {
				drawnElementsList.remove(id);
			}
			addGUIElements.clear();
			removeGUIElements.clear();
			for (Map.Entry<Long, GUIElement> entry : drawnElementsList
					.entrySet()) {
				entry.getValue().draw(g);
			}
		}
	}

}