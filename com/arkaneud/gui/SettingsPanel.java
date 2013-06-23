package com.arkaneud.gui;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class SettingsPanel extends Panel {

	private static final long serialVersionUID = 9130026836586246960L;
	private GameWindow window;
	
	public SettingsPanel(final GameWindow window) {
		this.window = window;
		this.add(new JLabel("Coming soon..."));
		final JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeSettings();
				window.showMenu();
			}
		});
		add(back);
	}
	
	public void removeSettings() {
		window.remove(this);
	}
}
