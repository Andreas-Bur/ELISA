package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MyWindow extends JFrame{

	public MyWindow() {
		super("Sprachassistent");
		setVisible(true);
		setPreferredSize(new Dimension(1000, 800));
		pack();
		
	}

}
