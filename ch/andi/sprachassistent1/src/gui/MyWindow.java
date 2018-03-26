package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import main.Main;

public class MyWindow extends JFrame {

	public MyWindow() {
		super("Sprachassistent");
		setVisible(true);
		setPreferredSize(new Dimension(1000, 800));
		setJMenuBar(myMenuBar());
		pack();
	}
	
	private JMenuBar myMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem exitItem =  new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.quit = true;
			}
		});
		menu.add(exitItem);
		menuBar.add(menu);
		return menuBar;
	}
	
}
