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
 * File: HighscorePanel.java
 * Type: HighscorePanel
 *
 * Documentation created: 07.07.2013 - 21:30:12 by Hans Ferchland
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.arkaneud.gui;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.arkaneud.game.Highscore;
import com.arkaneud.game.Highscore.HighscoreItem;

/**
 * The Class HighscorePanel.
 */
public class HighscorePanel extends Panel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9130026836586246960L;
	
	/** The highscore. */
	private Highscore highscore = Highscore.getInstance();
	
	/** The window. */
	private GameWindow window;
	
	/**
	 * Instantiates a new highscore panel.
	 * 
	 * @param window
	 *            the window
	 */
	public HighscorePanel(final GameWindow window) {
		this.window = window;
		refresh();
	}
	
	/**
	 * Removes the highscore.
	 */
	public void removeHighscore() {
		window.remove(this);
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		removeAll();
		validate();
		ArrayList<HighscoreItem> list = highscore.getList();
		Collections.reverse(list);
		int items = list.size();
		setLayout(new GridLayout(items+1, 1));
		final JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeHighscore();
				window.showMenu();
			}
		});
		add(back, items, 0);
		for (int i = items-1; i >= 0; i--) {
			HighscoreItem item = list.get(i);
			JLabel l = new JLabel( (i+1) + ")   " + item.getName() + " - " + item.getPoints());
			add(l, i, 0);
		}
		validate();
	}
}
