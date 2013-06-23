package com.arkaneud.gui;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenuPanel extends Panel {

	private static final long serialVersionUID = -3666578097436022914L;
	private GameWindow window;

	public MenuPanel(final GameWindow window) {
		this.window = window;
		setLayout(new GridLayout(4, 1));

		JButton startGame = new JButton("Start Game");
		startGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeMenu();
				window.startGame();
				window.validate();
			}
		});

		JButton showHS = new JButton("Highscore");
		showHS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeMenu();
				window.showHighscore();
				window.validate();
			}
		});

		JButton settings = new JButton("Settings");
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeMenu();
				window.showSettings();
				window.validate();
			}
		});

		JButton exitGame = new JButton("Exit Game");
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

	public void removeMenu() {
		window.remove(this);
	}

}
