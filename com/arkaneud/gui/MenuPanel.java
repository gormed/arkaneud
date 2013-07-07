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
 * File: MenuPanel.java
 * Type: MenuPanel
 *
 * Documentation created: 07.07.2013 - 21:30:14 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * The Class MenuPanel.
 */
public class MenuPanel extends Panel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3666578097436022914L;
	
	/** The window. */
	private GameWindow window;

	/**
	 * Instantiates a new menu panel.
	 * 
	 * @param window
	 *            the window
	 */
	public MenuPanel(final GameWindow window) {
		this.window = window;
		setLayout(new GridLayout(4, 1));

		JButton startGame = new JButton();
		startGame.setIcon(new ImageIcon("img/play.png"));
		startGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeMenu();
				window.startGame();
				window.validate();
			}
		});

		JButton showHS = new JButton();
		showHS.setIcon(new ImageIcon("img/hs.png"));
		showHS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeMenu();
				window.showHighscore();
				window.validate();
			}
		});

		JButton settings = new JButton();
		settings.setIcon(new ImageIcon("img/settings.png"));
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeMenu();
				window.showSettings();
				window.validate();
			}
		});

		JButton exitGame = new JButton();
		exitGame.setIcon(new ImageIcon("img/exit.png"));
		exitGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeMenu();
				window.exitGame();
			}
		});
		add(exitGame, 3, 0);
		add(settings, 2, 0);
		add(showHS, 1, 0);
		add(startGame, 0, 0);
	}

	/**
	 * Removes the menu.
	 */
	public void removeMenu() {
		window.remove(this);
	}

}
