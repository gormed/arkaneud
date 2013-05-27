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

public class HighscorePanel extends Panel {

	private static final long serialVersionUID = 9130026836586246960L;
	private Highscore highscore = Highscore.getInstance();
	private GameWindow window;
	
	public HighscorePanel(final GameWindow window) {
		this.window = window;
		refresh();
	}
	
	public void removeHighscore() {
		window.remove(this);
	}

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
